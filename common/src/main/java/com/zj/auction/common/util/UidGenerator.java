package com.zj.auction.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.security.SecureRandom;


public class UidGenerator {

    private static AlternativeJdkIdGenerator generator = new AlternativeJdkIdGenerator();

    /**
     * @return java.lang.String
     * @describe uuid32
     * @title uuid32
     * @author Mao Qi
     * @date 2019/8/9 10:38
     */
    public static String uuid32() {
        return generator.generateId().toString().replace("-", "");
    }


    /** MF 开头
     * 获取新唯一编号（18为数值）
     * 来自于twitter项目snowflake的id产生方案，全局唯一，时间有序。
     * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
     */
    public static String createOrderXid() {
        return "FY"+IdWorker.getId();
    }

    /** MF 开头
     * 获取新唯一编号（18为数值）
     * 来自于twitter项目snowflake的id产生方案，全局唯一，时间有序。
     * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
     */
    public static String createHouseXid() {
        return "MF"+IdWorker.getId();
    }
}
