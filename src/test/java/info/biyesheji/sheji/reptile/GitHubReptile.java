package info.biyesheji.sheji.reptile;

import com.google.gson.Gson;
import info.biyesheji.sheji.entity.ReptileLog;
import info.biyesheji.sheji.mapper.ReptileLogMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static info.biyesheji.sheji.entity.ReptileLog.未处理;

//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "application.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubReptile {
//    @Autowired
    private ReptileLogMapper reptileLogMapper;

    @Test
    public void test() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://github.com/search?q=ssh");
        httpGet.setHeader("Cookie","_octo=GH1.1.1495118585.1556611901; _ga=GA1.2.388250338.1556611944; _device_id=837e62df150b48ce7f44f9897bf17bc4; user_session=noiZwSPahmi410wuXNjNQoCisLnoEy2A35jG6XMG5eKpla7C; __Host-user_session_same_site=noiZwSPahmi410wuXNjNQoCisLnoEy2A35jG6XMG5eKpla7C; logged_in=yes; dotcom_user=liren1991; tz=Asia%2FShanghai; has_recent_activity=1; _gat=1; _gh_sess=VTVSU21rK0REcVNGQnh5cURhZnB3cTJORU9xQ1RnRWI5SDJoaUNaN2hSV09vV2VXd2tPQUIzOVhQaHBIbnRVRGZnV2srUWdSMmtsMW90VE9xRm4zV00vbTZ3cTI0cmlDWjlKVmRxZnNSc1UrRTZQM2ZVN2h1UG9iclBvdVFHYmZrdU9QcDIwSVpYTFgrejUxNGZIdDhGV2paQ3V0bWVOUHZlVTBvOW90Y3BoL2loV0tpLzJ2ekZFNkRCK0hmcERQVFVyb2xMRk5SMzd4eEJEdGpza3djblJPK3drUFRybUlFeWlvbWRQd1YwcEtXaklNQ092VDhXQktha1p3T1lUMno0NDdlV0RoWjJzUW4rN1Brbm1PZHJHNGZsb1RyTjdnTTQ0cmdNa1JlYmpWL00wL3dqMTZTMjVPWXpLSnpkYUNFSWUzMERJeFNucm9tTU1UM3Fhb2Z3PT0tLUVHWHcvUlFBZFZXZDV3TGN3QTU3cXc9PQ%3D%3D--127ccf0a61c307354cdb94784b3afd91e7970ab8");
        String body = "";
        HttpResponse response;
        HttpEntity entity;
        response = httpClient.execute(httpGet);
        entity = response.getEntity();
        body = EntityUtils.toString(entity,"utf-8");
        System.err.println(body);
        File file = new File("C:\\Users\\liren\\Desktop\\test.html");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(body);
        }
        httpGet.releaseConnection();
    }

    @Test
    public void test2(){
        File file = new File("C:\\Users\\liren\\Desktop\\test.html");
        Gson gson = new Gson();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char[] chars = new char[(int)file.length()];
            reader.read(chars);
            String string = new String(chars);
            Document document = Jsoup.parse(string);
            Elements elements = document.select("li.repo-list-item.d-flex.flex-column.flex-md-row.flex-justify-start.py-4.public.source");
            List<String> urlList = new ArrayList<>();
            for (Element element : elements){
                Elements elements1 = element.select("[itemprop='programmingLanguage']");
                String value = elements1.html();
                if (value.equalsIgnoreCase("java")){
                    urlList.add(element.select("a.v-align-middle").attr("href"));
                }
            }
            String nextPage = document.select("a.next_page").attr("href");
            System.out.printf(gson.toJson(urlList));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test3() throws IOException, GitAPIException, InterruptedException {
        List<ReptileLog> logList = reptileLogMapper.listAllReptileLog(未处理);
        for (ReptileLog reptileLog : logList){
            // prepare a new folder for the cloned repository
            File localPath = new File("D:\\gitProject");
//            if(!localPath.delete()) {
//                throw new IOException("Could not delete temporary file " + localPath);
//            }
            String cloneUrl = "https://" + reptileLog.getUrl() + ".git";
            // then clone
            System.out.println("Cloning from " + cloneUrl + " to " + localPath);
            try (Git result = Git.cloneRepository()
                    .setURI(cloneUrl)
                    .setDirectory(localPath)
                    .call()) {
                System.out.println("Having repository: " + result.getRepository().getDirectory());
            }
            TimeUnit.SECONDS.sleep(3);
        }
    }


    private static final String REMOTE_URL = "https://github.com/github/testrepo.git";

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath =  new File("D:\\gitProject\\test");
//        if(!localPath.delete()) {
//            throw new IOException("Could not delete temporary file " + localPath);
//        }

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setGitDir(localPath)
                .call()) {
            System.out.println("Having repository: " + result.getRepository().getDirectory());
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(localPath);
    }

}
