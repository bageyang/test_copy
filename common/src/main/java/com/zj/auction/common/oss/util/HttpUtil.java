package com.zj.auction.common.oss.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpUtil {
    public static RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
    public static final String key = "申请的接口Appkey";
    public static final String openId = "在个人中心查询";
    public static final String telCheckUrl = "http://op.juhe.cn/ofpay/mobile/telcheck?cardnum=*&phoneno=!&key=申请的接口Appkey";
    public static final String telQueryUrl = "http://op.juhe.cn/ofpay/mobile/telquery?cardnum=*&phoneno=!&key=申请的接口Appkey";
    public static final String onlineUrl = "http://op.juhe.cn/ofpay/mobile/onlineorder?key=申请的接口Appkey&phoneno=!&cardnum=*&orderid=@&sign=$";
    public static final String yueUrl = "http://op.juhe.cn/ofpay/mobile/yue?key=申请的接口Appkey&timestamp=%&sign=$";
    public static final String orderstaUrl = "http://op.juhe.cn/ofpay/mobile/ordersta?key=申请的接口Appkey&orderid=!";
    public static final String orderListUrl = "http://op.juhe.cn/ofpay/mobile/orderlist?key=申请的接口Appkey";

    public HttpUtil() {
    }

    public static int telCheck(String phone, int cardnum) throws Exception {
        String result = get("http://op.juhe.cn/ofpay/mobile/telcheck?cardnum=*&phoneno=!&key=申请的接口Appkey".replace("*", String.valueOf(cardnum)).replace("!", phone), 0);
        return JSON.parseObject(result).getInteger("error_code");
    }

    public static String telQuery(String phone, int cardnum) throws Exception {
        String result = get("http://op.juhe.cn/ofpay/mobile/telquery?cardnum=*&phoneno=!&key=申请的接口Appkey".replace("*", String.valueOf(cardnum)).replace("!", phone), 0);
        return result;
    }

    public static String onlineOrder(String phone, int cardnum, String orderid) throws Exception {
        String result = null;
        String sign = MD5Util.md5Hex("在个人中心查询申请的接口Appkey" + phone + cardnum + orderid);
        result = get("http://op.juhe.cn/ofpay/mobile/onlineorder?key=申请的接口Appkey&phoneno=!&cardnum=*&orderid=@&sign=$".replace("*", String.valueOf(cardnum)).replace("!", phone).replace("@", orderid).replace("$", sign), 0);
        return result;
    }

    public static String yuE() throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String sign = MD5Util.md5Hex("在个人中心查询申请的接口Appkey" + timestamp);
        String result = get("http://op.juhe.cn/ofpay/mobile/yue?key=申请的接口Appkey&timestamp=%&sign=$".replace("%", timestamp).replace("$", sign), 0);
        return result;
    }

    public static String orderSta(String orderid) throws Exception {
        return get("http://op.juhe.cn/ofpay/mobile/ordersta?key=申请的接口Appkey&orderid=!".replace("!", orderid), 0);
    }

    public static String orderList() throws Exception {
        return get("http://op.juhe.cn/ofpay/mobile/orderlist?key=申请的接口Appkey", 0);
    }

    public static String get(String url, int tts) throws Exception {
        if (tts > 3) {
            return null;
        }
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = ConvertStreamToString(resEntity.getContent(), "UTF-8");
            }
            EntityUtils.consume(resEntity);
            return result;
        } catch (IOException e) {
            return get(url, ++tts);
        }
    }

    public static String ConvertStreamToString(InputStream is, String charset) throws Exception {
        StringBuilder sb = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(is, charset);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return sb.toString();
    }
}
