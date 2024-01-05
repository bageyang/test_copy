package com.zj.auction.common.sms;


import com.zj.auction.common.oss.SendMessage;
import com.zj.auction.common.util.AppTokenUtils;
import com.zj.auction.common.util.IPUtils;
import com.zj.auction.common.util.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 短信
 *
 * @author Mao Qi
 * @date 2019年6月5日
 * @describe
 */
public class SmsUtils {

    /**
     * @param smsId  多企源平台ID
     * @param mobile 电话
     * @param ip     IP地址
     */
    public static Map<String, Object> sendRandomCode(String smsId, String mobile, String ip, String code) {
        return SendMessage.sendMessage(smsId, mobile, code, ip);
    }

    public static Map<String, Object> sendMessages(String template, String phone, String code, HttpServletRequest request) {
        Map<String, Object> sendMessage = SendMessage.sendMessage(template, phone, code, IPUtils.getRemoteAddr(request));
        RedisUtil.set(AppTokenUtils.CODE_FILE + phone, sendMessage.get("code").toString().trim(), 5 * 60);
        return sendMessage;
    }
}
