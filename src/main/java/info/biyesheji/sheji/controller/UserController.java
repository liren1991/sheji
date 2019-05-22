package info.biyesheji.sheji.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.biyesheji.sheji.entity.Product;
import info.biyesheji.sheji.entity.ProductParam;
import info.biyesheji.sheji.mapper.OrderItemMapper;
import info.biyesheji.sheji.mapper.OrderMapper;
import info.biyesheji.sheji.mapper.ProductMapper;
import info.biyesheji.sheji.util.MD5Util;
import info.biyesheji.sheji.util.PageResult;
import info.biyesheji.sheji.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static info.biyesheji.sheji.entity.ProductParam.下载量倒序;
import static info.biyesheji.sheji.util.HTMLConstant.gridView;
import static info.biyesheji.sheji.util.HTMLConstant.listView;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final int 默认分页条数 = 30;
    private static final String 浏览器_Cookie = "Cookie";
    private static final String 购物车_key = "cart_%s";
    private static final Gson gson = new Gson();
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;

    public static final Cache<String, Object> cache = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(50)
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(1000)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(1, TimeUnit.HOURS).build(); //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存


    @RequestMapping("/shop.html")
    public Object shopIndex(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        String cookie = getCookie(request);
        String ip = ResponseUtil.getIpAddress(request);
        if (StringUtils.isEmpty(cookie) && !StringUtils.isEmpty(ip))
            response.addCookie(new Cookie(浏览器_Cookie, MD5Util.encrypt(ip)));

        Map<String, Object> resultMap = new HashMap<>();
        ProductParam param = new ProductParam();
        param.setSortType(下载量倒序);
        param.setPageSize(默认分页条数);
        PageResult<Product> pageResult = new PageResult<>();
        int count = productMapper.countListProduct(param, 0);
        List<Product> productList = productMapper.listProduct(param, 0);
        pageResult.setBeanList(productList);
        pageResult.setTotalSize(180);
        pageResult.setPageNum(1);
        pageResult.setTotalPageCount(PageResult.staticTotalPageCount(180,默认分页条数));
        resultMap.put("pageResult", pageResult);
        resultMap.put("totalPageCount",pageResult.getTotalPageCount());
        List<Product> sortList = new ArrayList<>();
        for (int i = 0; i < productList.size() && i < 5; i++)
            sortList.add(productList.get(i));

        resultMap.put("sortList", sortList);
        modelAndView.addAllObjects(resultMap);
        modelAndView.setViewName("shop.html");
        return modelAndView;
    }

    @RequestMapping("/listProduct.html")
    @ResponseBody
    public Object listProduct(@RequestParam(value = "startPrice", required = false) Integer startPrice,
                              @RequestParam(value = "endPrice", required = false) Integer endPrice,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "type", required = false) Integer type,
                              @RequestParam(value = "sortType", required = false) Integer sortType,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize) {
        ProductParam param = new ProductParam();
        List<Product> productList = new ArrayList<>();
        param.setStartPrice(startPrice);
        param.setEndPrice(endPrice);
        param.setPageSize(pageSize);
        param.setName(name);
        param.setType(type);
        param.setSortType(sortType);
        param.setPageNum(pageNum);
        Integer count = productMapper.countListProduct(param, (pageNum - 1) * pageSize);
        if (count != null)
            productList = productMapper.listProduct(param, (pageNum - 1) * pageSize);
        else
            count = 0;
        StringBuilder gridViewBuilder = new StringBuilder();
        StringBuilder listViewBuilder = new StringBuilder();

        for (Product product : productList) {
            // s 顺序  详情链接   首图链接  详情链接  商品名称  商品实际价格  商品原价
            gridViewBuilder.append(String.format(gridView, product.getId(), product.getUrl(), product.getId(), product.getName(), product.getPrice(), product.getPrice()));
            // s 顺序  详情链接   首图链接  详情链接  商品名称  商品实际价格  商品备注
            listViewBuilder.append(String.format(listView, product.getId(), product.getUrl(), product.getId(), product.getName(), product.getPrice(), product.getRemark()));
        }

        // 计算总页数
        Integer totalPageCount = PageResult.staticTotalPageCount(180,pageSize);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("gridView", gridViewBuilder.toString());
        resultMap.put("listView", listViewBuilder.toString());
        resultMap.put("totalPageCount",totalPageCount);
        return ResponseUtil.succ(resultMap);
    }

    @RequestMapping(value = "addToCart.html")
    @ResponseBody
    public Object addToCart(@RequestParam(value = "productId") Integer productId,
                            HttpServletRequest request) throws ExecutionException {
        String cookieValue = getCookie(request);
        String key = String.format(购物车_key, cookieValue);
        Type type = new TypeToken<HashMap<Integer, Product>>() {
        }.getType();
        Map<Integer, Product> map = gson.fromJson(gson.toJson(cache.getIfPresent(key)), type);
        if (map == null)
            map = new HashMap<>();
        Product product = (Product) cache.get(productId.toString(), () -> productMapper.getProductByPrimaryId(productId));
        cache.put(product.getId().toString(), product);
        map.put(productId, product);
        cache.put(cookieValue, map);
        List<Product> productList = map.keySet().stream().map(map::get).collect(Collectors.toList());
        return ResponseUtil.succ(productList);
    }

    public static final String getCookie(HttpServletRequest request) {
        // 设置cookie  未经过 此页面的的所有请求都视为 非法请求
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;

        Optional<Cookie> cookieOptional = Arrays.stream(cookies).filter(item -> 浏览器_Cookie.equalsIgnoreCase(item.getName())).findFirst();
        final String[] cookies1 = new String[1];
        cookieOptional.ifPresentOrElse(cookie1 -> cookies1[0] = cookie1.getValue(), () -> cookies1[0] = "");
        return cookies1[0];
    }
}
