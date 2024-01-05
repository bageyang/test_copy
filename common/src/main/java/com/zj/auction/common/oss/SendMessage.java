package com.zj.auction.common.oss;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zj.auction.common.oss.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;


/**
 * 发送消息
 *
 * @author 胖胖不胖
 * @date 2022/06/08
 */
public class SendMessage {
    private static String BaseUrl = "http://api.cq5869.com/json/sendSmsMessage.action?";

    public SendMessage() {
    }

    public static Map<String, Object> sendMessage(String smsDemoId, String tel, String code, String ip) {
        Map<String, Object> codeMap = Maps.newHashMap();
        codeMap.put("error_code", "-1");

        try {
            String num = String.valueOf((new Date()).getTime());
            if (code == null || code.trim().equals("")) {
                code = num.subSequence(7, 13).toString();
            }
            String newUrl = BaseUrl + "ip=" + ip + "&smsDemoId=" + smsDemoId + "&tel=" + tel + "&code=" + code;
            HttpUtil.get(newUrl, 3);
            codeMap.put("error_code", "1");
            codeMap.put("tel", tel);
            codeMap.put("code", code);
            codeMap.put("ip", ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return codeMap;
    }

    public static Map<String, Object> sendMessage(String smsDemoId, String tel, String code, String ip, String areaCode) {
        Map<String, Object> codeMap = Maps.newHashMap();
        codeMap.put("error_code", "-1");

        try {
            String num = String.valueOf((new Date()).getTime());
            if (code == null || code.trim().equals("")) {
                code = num.subSequence(7, 13).toString();
            }

            areaCode = StringUtils.trimToEmpty(areaCode);
            String newUrl = BaseUrl + "ip=" + ip + "&smsDemoId=" + smsDemoId + "&tel=" + tel + "&code=" + code + "&areaCode=" + areaCode;
            HttpUtil.get(newUrl, 3);
            codeMap.put("error_code", "1");
            codeMap.put("tel", tel);
            codeMap.put("code", code);
            codeMap.put("ip", ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return codeMap;
    }

    public static Map<String, Object> sendMessage(String smsDemoId, String tel, String[] params, String ip, String areaCode) {
        Map<String, Object> codeMap = Maps.newHashMap();
        codeMap.put("error_code", "-1");

        try {
            StringBuffer paramsStr = new StringBuffer();

            for (int i = 0; i < params.length; ++i) {
                switch (i) {
                    case 0:
                        paramsStr.append("&code=").append(params[i]);
                        break;
                    case 1:
                        paramsStr.append("&code2=").append(params[i]);
                        break;
                    case 2:
                        paramsStr.append("&code3=").append(params[i]);
                }
            }

            areaCode = StringUtils.trimToEmpty(areaCode);
            String arrJsonStr = JSON.toJSONString(params);
            String newUrl = BaseUrl + "ip=" + ip + "&smsDemoId=" + smsDemoId + "&tel=" + tel + paramsStr.toString() + "&areaCode=" + areaCode + "&params=";
            newUrl = newUrl + URLEncoder.encode(arrJsonStr, "UTF-8");
            System.out.println("newUrl:" + newUrl);
            HttpUtil.get(newUrl, 3);
            codeMap.put("error_code", "1");
            codeMap.put("tel", tel);
            codeMap.put("params", arrJsonStr);
            codeMap.put("ip", ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return codeMap;
    }
}
