package com.zj.auction.general.sms;


import com.zj.auction.common.oss.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * 短信
 *
 * @author Mao Qi
 * @date 2019年6月5日
 * @describe
 */
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class SmsUtils {
    private final RedisTemplate redisTemplate;


    /**
     * @param smsId  多企源平台ID
     * @param mobile 电话
     * @param ip     IP地址
     */
    public static Map<String, Object> sendRandomCode(String smsId, String mobile, String ip, String code) {
        return SendMessage.sendMessage(smsId, mobile, code, ip);
    }


}
