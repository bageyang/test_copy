package com.zj.auction.general.listener;

import com.alibaba.fastjson2.JSONObject;
import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.dto.PayDto;
import com.zj.auction.common.util.StringUtils;
import com.zj.auction.general.listener.strategy.PayCallBackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class PaymentNotifyListener {
    @Autowired
    private List<PayCallBackHandler> payCallBackHandlerList;


    @RabbitListener(queues = Constant.PAY_QUEUE_KEY)
    public void paymentListener(byte[] msgBody){
        String msg = new String(msgBody);
        log.info("处理支付消息通知:{}",msg);
        if(StringUtils.isBlank(msg)){
            log.error("支付回调消息为空");
        }
        PayDto payDto = JSONObject.parseObject(msg, PayDto.class);
        Optional<String> s = checkPayDto(payDto);
        if(s.isPresent()){
            log.error("支付回调错误,case:{},msg:{}",s.get(),msg);
            return;
        }
        handMsg(payDto);
    }

    private void handMsg(PayDto payDto) {
        for (PayCallBackHandler handler : payCallBackHandlerList) {
            try {
                if(handler.shouldHand(payDto)){
                    handler.hand(payDto);
                }
            }catch (Exception e){
                log.error("支付回调处理错误,处理类:{},消息:{}",handler.getClass(),payDto);
                log.error("支付回调处理错误",e);
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
