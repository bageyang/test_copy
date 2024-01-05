package com.zj.auction.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class CardCheckUtils {



//    public static void main(String[] args) {
//        JSONObject jsonObject = checkUserCard("张用", "51298654556253698");
//        System.out.println("jsonObject = " + jsonObject);
//    }

    /**
     *
     * @param realName  真实姓名
     * @param cardNo    身份证号码
     * @return 返回 null接口异常;  正常返回如下:
     *  "realname": "张*", 用户传递上来真实姓名脱敏返回
     *  "idcard":"3303***********", 用户传递上来IdcardNo的脱敏返回
     *  "isok":false   true：匹配 false：不匹配,
     *  "IdCardInfor":
     *  {
     *  "province":"浙江省",
     *  "city":"杭州市",
     *  "district":"xx县",
     *  "area":"浙江省杭州市区xx县",
     *  "sex":"男",
     *  "birthday":"1965-3-10"
     *  }
     *  }}
     */
    public static JSONObject checkUserCard(String realName, String cardNo){
        String host = "https://zid.market.alicloudapi.com";
        String path = "/idcheck/Post";
        String method = "POST";
        String appcode = "85021f2e3c634055b06447af45788366";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        bodys.put("cardNo", cardNo);
        bodys.put("realName", realName);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpCardUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            int httpCode = response.getStatusLine().getStatusCode();
            if (httpCode == 200) {

                System.out.println("正常请求计费(其他均不计费)");
                System.out.println("获取返回的json：");
//                System.out.println(EntityUtils.toString(response.getEntity()));
                String string = EntityUtils.toString(response.getEntity());
                System.out.println(string);
                JSONObject jsonObject = JSON.parseObject(string);
                return jsonObject.getJSONObject("result");
            } else {
                System.out.println("httpCode = " + httpCode);
                System.out.println(response.getFirstHeader("X-Ca-Error-Message").toString());
//                Map<String, List<String>> map = response.headers().map();
//                String error = map.get("X-Ca-Error-Message").get(0);
//                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
//                    System.out.println("AppCode错误 ");
//                } else if (httpCode == 400 && error.equals("Invalid Url")) {
//                    System.out.println("请求的 Method、Path 或者环境错误");
//                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
//                    System.out.println("参数错误");
//                } else if (httpCode == 403 && error.equals("Unauthorized")) {
//                    System.out.println("服务未被授权（或URL和Path不正确）");
//                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
//                    System.out.println("套餐包次数用完 ");
//                } else {
//                    System.out.println("参数名错误 或 其他错误");
//                    System.out.println(error);
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


}
