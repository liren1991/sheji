package info.biyesheji.sheji.controller;

import info.biyesheji.sheji.entity.ReptileLog;
import info.biyesheji.sheji.entity.ReptileParam;
import info.biyesheji.sheji.mapper.ReptileLogMapper;
import info.biyesheji.sheji.util.RequestUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class ReptileTaskController {
    private static final Logger logger = LoggerFactory.getLogger(ReptileTaskController.class);
    private static final HashSet<String> projectUrlSet = new HashSet<>();
    private static volatile Boolean isStop;
    private static final int java = 1;
    @Autowired
    private ReptileLogMapper reptileLogMapper;


    @RequestMapping("/reptileGitHubUrl.html")
    public Object reptileGitUrl(@RequestBody ReptileParam param) {
        if (isStop != null && !isStop)
            return RequestUtil.error("gitHub 爬虫已启动!");
        isStop = false;
        ReptileThread reptileThread = new ReptileThread(param, reptileLogMapper);
        new Thread(reptileThread).start();
        return RequestUtil.succ();
    }

    @RequestMapping("/stopReptileGitHub.html")
    public void stopReptileGitHub(@RequestBody ReptileParam param) {
        isStop = true;
    }

    private static final class ReptileThread implements Runnable {
        private ReptileParam param;
        private ReptileLogMapper mapper;
        private String initUrl;

        public ReptileThread(ReptileParam param, ReptileLogMapper reptileLogMapper) {
            this.param = param;
            mapper = reptileLogMapper;
            initUrl = param.getUrl();
        }

        @Override
        public void run() {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("本次爬取url:  " + param.getUrl());
            HttpGet httpGet = new HttpGet(param.getUrl());
            String nextPage = "";
            String body = "";
            try {
                httpGet.setHeader("Cookie", param.getCookie());
                HttpResponse response;
                HttpEntity entity;
                response = httpClient.execute(httpGet);
                entity = response.getEntity();
                body = EntityUtils.toString(entity, "utf-8");

                Document document = Jsoup.parse(body);
                Elements elements = document.select("li.repo-list-item.d-flex.flex-column.flex-md-row.flex-justify-start.py-4.public.source");
                for (Element element : elements) {
                    Elements elements1 = element.select("[itemprop='programmingLanguage']");
                    String value = elements1.text();
                    if (value.equalsIgnoreCase("java")) {
                        String urlStr = element.select("a.v-align-middle").attr("href");
                        if (!StringUtils.isEmpty(urlStr)) {
                            String url = httpGet.getURI().getAuthority() + urlStr;
                            projectUrlSet.add(url);
                            ReptileLog reptileLog = new ReptileLog();
                            reptileLog.setLanguageType(java);
                            reptileLog.setRemark(element.select("p.col-12.col-md-9.d-inline-block.text-gray.mb-2.pr-4").text());
                            reptileLog.setStartNum(element.select("a.muted-link").text());
                            reptileLog.setUrl("https://" + url + ".git");
                            reptileLog.setType(1);
                            try {
                                mapper.addReptileLog(reptileLog);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }
                nextPage = document.select("a.next_page").attr("href");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!isStop && !StringUtils.isEmpty(nextPage)) {
                URI url = httpGet.getURI();
                String nextPageUrl = url.getAuthority();
                param.setUrl("https://" + nextPageUrl + nextPage);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();
            } else {
                isStop = true;
                httpGet.releaseConnection();
                logger.info("本次爬取结束:  " + initUrl);
                logger.info("最后一次页面html:   " + body);
                return;
            }
        }
    }


}
