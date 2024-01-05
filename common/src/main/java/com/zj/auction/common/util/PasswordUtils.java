package com.zj.auction.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;


public class PasswordUtils {
    /**
     * 加密方式
     */
    private final static String algorithmName = "md5";

    /**
     * 默认加密次数值
     */
    private final static int DEFAULT_HASH_TIMES = 2;


    /**
     * 平台系统密码加密工具方法
     *
     * @param password 密码
     * @param salt     随机盐
     * @return
     */
    public static String encryptPassword(String salt, String password) {
        return new SimpleHash(algorithmName, password, salt, DEFAULT_HASH_TIMES).toHex();
    }

    /**
     * @Method salt
     * @Author yangf
     * @Version 1.0
     * @Description 生成随机盐
     * @Return java.lang.String
     * @Exception
     * @Date 2019/4/3 14:57
     */
    public static String salt() {
        return RandomStringUtils.randomAscii(6, 10);
    }
}
