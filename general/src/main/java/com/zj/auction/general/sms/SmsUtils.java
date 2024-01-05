package com.zj.auction.general.sms;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.google.common.collect.Maps;
import com.zj.auction.common.oss.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * 短信跑龙套
 * 短信
 *
 * @author 胖胖不胖
 * @describe
 * @date 2022/06/17
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


    public static Map<String, Object> sendMessagesByAliyun( String tel,String ip ) {
        Map<String, Object> codeMap = Maps.newHashMap();
        codeMap.put("error_code", "-1");
        String num = String.valueOf((new Date()).getTime());
        String code = num.subSequence(7, 13).toString();
        try {
            Client client = createClient("LTAI5tMK7gcdnZiNxsJhHdTy", "qRdawhezZeKzRBu44kcDvlXlo8tAp3");
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName("中酒商城")
                    .setTemplateCode("SMS_242055036")
                    .setPhoneNumbers(tel)
                    .setTemplateParam("{\"code\":\""+code+"\"}");
            SendSmsResponse sendRsp = client.sendSms(sendSmsRequest);
            boolean isOk = Optional.ofNullable(sendRsp)
                    .map(SendSmsResponse::getBody)
                    .map(SendSmsResponseBody::getCode)
                    .map(SmsUtils::isOK)
                    .orElse(false);
            if(isOk){
                codeMap.put("error_code", "1");
                codeMap.put("tel", tel);
                codeMap.put("code", code);
                codeMap.put("ip", ip);
                return codeMap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return codeMap;
    }

    private static boolean isOK(String s){
        return "OK".equals(s);
    }

    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

}
