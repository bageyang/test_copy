package com.zj.auction.payment.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtils {
    public static String md5Sign(Map<String, String> params, String secretKey) {
        List<String> keys = Lists.newArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                continue;
            }
            if (StringUtils.isNotBlank(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        Collections.sort(keys);

        List<String> temp = Lists.newArrayList();
        for (String key : keys) {
            String value = params.get(key);
            temp.add(key + "=" + value);
        }

        temp.add("secret_key=" + secretKey);
        String signStr = StringUtils.join(temp, "&");
        return genMd5(signStr);
    }

    public static String genMd5(String info) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] infoBytes = info.getBytes();
        md5.update(infoBytes);
        byte[] sign = md5.digest();
        return byteArrayToHex(sign);
    }

    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}

