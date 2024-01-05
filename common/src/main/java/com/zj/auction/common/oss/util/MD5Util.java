package com.zj.auction.common.oss.util;

import org.apache.tomcat.util.buf.HexUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
    private MD5Util() {
    }

    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            throw new RuntimeException(var1);
        }
    }

    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    public static String md5Hex(byte[] data) {
        return HexUtils.toHexString(md5(data));
    }

    public static String md5Hex(String data) {
        return HexUtils.toHexString(md5(data));
    }
}
