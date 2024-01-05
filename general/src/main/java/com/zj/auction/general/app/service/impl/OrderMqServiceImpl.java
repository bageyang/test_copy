package com.zj.auction.general.app.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.dto.OrderNotifyDto;
import com.zj.auction.general.app.service.OrderMqService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class OrderMqServiceImpl implements OrderMqService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderMqServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrderDelayMsg(OrderNotifyDto notifyDto, long delayTime, TimeUnit timeUnit) {
        long milliseconds = TimeUnit.MILLISECONDS.convert(delayTime, timeUnit);
        notifyDto.setCreateTime(LocalDateTime.now());
        notifyDto.setDelayTime(milliseconds);
        String message = JSON.toJSONString(notifyDto);
        // 消息延迟设置
        Message msg = MessageBuilder
                .withBody(message.getBytes(StandardCharsets.UTF_8)) //消息体
                .setHeader("x-delay", milliseconds)
                .build();
        // 发送消息
        rabbitTemplate.convertAndSend(Constant.ORDER_DELAY_EXCHANGE_KEY, Constant.ORDER_DELAY_KEY, msg);
    }

    @Override
    public void sendOrderDelayMsg(OrderNotifyDto notifyDto, long delayTime) {
        sendOrderDelayMsg(notifyDto,delayTime,TimeUnit.MILLISECONDS);
    }

    @Override
    public void sendOrderDelayMsg(OrderNotifyDto notifyDto) {

        sendOrderDelayMsg(notifyDto,notifyDto.getDelayTime(),TimeUnit.MILLISECONDS);
    }
}
