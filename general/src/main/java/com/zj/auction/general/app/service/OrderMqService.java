package com.zj.auction.general.app.service;

import com.zj.auction.common.dto.OrderNotifyDto;

import java.util.concurrent.TimeUnit;

public interface OrderMqService {
    void sendOrderDelayMsg(OrderNotifyDto notifyDto, long delayTime, TimeUnit timeUnit);
    void sendOrderDelayMsg(OrderNotifyDto notifyDto,long delayTime);
    void sendOrderDelayMsg(OrderNotifyDto notifyDto);
}
