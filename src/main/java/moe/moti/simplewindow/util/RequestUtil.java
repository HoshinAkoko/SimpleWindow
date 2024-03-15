package moe.moti.simplewindow.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class RequestUtil {
    protected static Logger log = LoggerFactory.getLogger(RequestUtil.class);

    /***
     * post 请求
     * @param url
     * @param str
     * @return
     * @throws Exception
     */
    public static String post(String url, String str) throws Exception {
        // 处理请求地址
        URI uri = new URI(url);
        HttpPost post = new HttpPost(uri);
        StringEntity entity = new StringEntity(str, "UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
//        post.setHeader("Content-Type", "application/json");
        // 执行请求
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(15000)
                .setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000)
                .build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        CloseableHttpResponse response = client.execute(post);
        int httpStatus = response.getStatusLine().getStatusCode();
        if (httpStatus == 200) {
            // 处理请求结果
            StringBuffer buffer = new StringBuffer();
            InputStream in = null;
            BufferedReader reader = null;
            try {
                in = response.getEntity().getContent();
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
            } catch (Exception e) {
                log.error("http request util post exc:" + e.getMessage());
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != client) {
                    client.close();
                }
                if (null != response) {
                    response.close();
                }
            }
            return buffer.toString();
        } else {
            log.error("http request Status:{}", httpStatus);
            if (null != client) {
                client.close();
            }
            if (null != response) {
                response.close();
            }
            return null;
        }
    }
}
