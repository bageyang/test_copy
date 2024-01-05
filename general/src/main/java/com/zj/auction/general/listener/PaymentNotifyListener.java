package com.zj.auction.general.listener;

import com.alibaba.fastjson2.JSONObject;
import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.dto.PayDto;
import com.zj.auction.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class PaymentNotifyListener {
    private static final String CASH_PAY_TYPE = "充值余额支付";
    private static final String BAIL_PAY_TYPE = "预约保证金支付";
    private static final String TRANSFER_FEE_PAY_TYPE = "转拍手续费支付";
    private static final String SUCCESS_STAT = "成功支付";
    private static final String WAIT_STAT = "待支付";
    private static final String CLOSE_STAT = "已关闭";


    @RabbitListener(queues = Constant.PAY_QUEUE_KEY)
    public void paymentListener(byte[] msgBody){
        String msg = new String(msgBody);
        log.info("处理支付消息通知:{}",msg);
        if(StringUtils.isBlank(msg)){
            log.error("支付回调消息为空");
        }// 策略
        PayDto payDto = JSONObject.parseObject(msg, PayDto.class);
        Optional<String> s = checkPayDto(payDto);
        if(s.isPresent()){
            log.error("支付回调错误,case:{},msg:{}",s.get(),msg);
            return;
        }
        String payType = payDto.getPayType();
        String billStatus = payDto.getBillStatus();
        if(SUCCESS_STAT.equals(billStatus)){
            if (CASH_PAY_TYPE.equals(payType)) {
                // 余额充值
            }
        }

    }

    private Optional<String> checkPayDto(PayDto payDto) {
        if(Objects.isNull(payDto)){
            return Optional.of("消息为空");
        }
        if(Objects.isNull(payDto.getAmount())){
            return Optional.of("金额为空");
        }
        if(Objects.isNull(payDto.getBillType())){
            return Optional.of("支付类型为空");
        }
        if(Objects.isNull(payDto.getOriginSn())){
            return Optional.of("关联id为空");
        }
        return Optional.empty();
    }

}
