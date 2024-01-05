package com.zj.auction.general.listener.strategy;

import com.zj.auction.common.dto.PayDto;
import com.zj.auction.general.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class BailPayHandler implements PayCallBackHandler{
    private static final String BAIL_PAY_TYPE = "预约保证金支付";
    private static final String SUCCESS_STAT = "成功支付";
    private final OrderService orderService;

    public BailPayHandler(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public boolean shouldHand(PayDto payDto) {
        String billType = payDto.getBillType();
        String billStatus = payDto.getBillStatus();
        return Objects.equals(BAIL_PAY_TYPE,billType)&&Objects.equals(SUCCESS_STAT,billStatus);
    }


    @Override
    public void hand(PayDto payDto) {
        log.info("处理转拍支付回调:{}",payDto);
        Long originSn = payDto.getOriginSn();
        orderService.transferPaymentCallBack(originSn);
        log.info("处理转拍支付回调处理完毕");
    }
}
