//package com.zj.auction.general.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zj.auction.common.constant.Constant;
//import com.zj.auction.common.dto.BaseOrderDto;
//import com.zj.auction.general.app.service.OrderService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class OrderNotifyListener {
//    private final OrderService orderService;
//
//    @Autowired
//    public OrderNotifyListener(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//
//    @RabbitListener(queues = Constant.ORDER_DELAY_QUEUE_KEY)
//    public void OrderDelayListener(byte[] msgBody){
//        String msg = new String(msgBody);
//        log.info("订单延时消费消息:{}",msg);
//    }
//
//    @RabbitListener(queues = Constant.ORDER_QUEUE_KEY)
//    public void OrderGeneratorListener(byte[] msgBody){
//        try {
//            String msg = new String(msgBody);
//            log.info("开始生成订单:{}",msg);
//            BaseOrderDto baseOrderDto = JSONObject.parseObject(msg, BaseOrderDto.class);
//            orderService.generatorOrder(baseOrderDto);
//        }catch (Exception e){
//            log.error("订单生成失败",e);
//        }
//    }
//}
