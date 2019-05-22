package info.biyesheji.sheji.util;

public interface HTMLConstant {
    // s 顺序  详情链接   首图链接  详情链接  商品名称  商品实际价格  商品原价
    String gridView = "<div class=\"col-lg-4 col-md-4 col-sm-6 col-6\">" +
            "<div class=\"single-product\">" +
            "<div class=\"pro-img\">" +
            "<a href=\"/user/getProduct.html?product_id=%s\">" +
            "<img class=\"my-primary-img\" src=\"%s\" alt=\"single-product\">" +
            "</a>" +
            "</div>" +
            "<div class=\"pro-content\">" +
            "<div>" +
            "<h4><a th:href=\"/user/getProduct.html?product_id=%s\">%s</a></h4>" +
            "<p><span class=\"price\">¥%s</span>" +
            "<del class=\"prev-price\">¥%s</del>" +
            "</p>" +
            "</div>" +
            "<div class=\"pro-actions}\">" +
            "<div class=\"actions-primary\">" +
            "<a> + 购物车</a>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>";

    
    // s 顺序  详情链接   首图链接  详情链接  商品名称  商品实际价格  商品备注
    String listView = "<div class=\"single-product\">" +
            "<div class=\"row\">" +
            "<div class=\"col-lg-4 col-md-5 col-sm-12\">" +
            "<div class=\"pro-img\">" +
            "<a href=\"/user/getProduct.html?product_id=%s\">" +
            "<img class=\"my-primary-img\" src=\"%s\"" +
            " alt=\"single-product\">" +
            "</a>" +
            "<span class=\"sticker-new\">新上架</span>" +
            "</div>" +
            "</div>" +
            "<div class=\"col-lg-8 col-md-7 col-sm-12\">" +
            "<div class=\"pro-content hot-product2\">" +
            "<h4><a href=\"/user/getProduct.html?product_id=%s\" >%s</a></h4>" +
            "<p><span class=\"price\">¥%s</span></p>" +
            "<p >%s</p>" +
            "<div class=\"pro-actions\">" +
            "<div class=\"actions-primary\">" +
            "<a>+ 购物车</a>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>";

}
