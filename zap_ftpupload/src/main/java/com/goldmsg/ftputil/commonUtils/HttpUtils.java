package com.goldmsg.ftputil.commonUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/30
 * Time: 12:00
 */
public class HttpUtils {
    public static Logger logger = LogManager.getLogger(HttpUtils.class);

    public static CloseableHttpClient getHttpclient() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient;
    }

    public static CloseableHttpResponse getHttpResponse(CloseableHttpClient httpClient, String url) throws IOException, ClientException {
        HttpGet httpGet = getHttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (!getStatusCode(response)){
            throw  new ClientException("服务器返回失败："+response.getStatusLine().getStatusCode());
        }
        return response;
    }

    public static CloseableHttpResponse postHttpResponse(CloseableHttpClient httpClient, String url, JSONObject params) throws IOException, ClientException {
        HttpPost httpPost = getHttpPost(url);
        StringEntity entity = new StringEntity(params.toString(), "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (!getStatusCode(response)){
            throw  new ClientException("服务器返回失败："+response.getStatusLine().getStatusCode());
        }

        return response;
    }

    public static HttpGet getHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        setHttpRequest(httpGet);
        return httpGet;
    }

    public static HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        setHttpRequest(httpPost);
        return httpPost;
    }

    private static void setHttpRequest(HttpRequestBase requestBase) {
        requestBase.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
    }

    public static boolean getStatusCode(CloseableHttpResponse response) {
        boolean result = false;
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            return result = true;
        } else {
            return result;
        }
    }

    public static String getHttpEntityString(CloseableHttpResponse response) throws IOException {
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        EntityUtils.consume(httpEntity);
        response.close();
        return result;
    }

    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

}
