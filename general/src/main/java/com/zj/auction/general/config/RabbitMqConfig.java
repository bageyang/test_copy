package com.zj.auction.general.config;

import com.zj.auction.common.constant.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public DirectExchange orderDelayExchange() {
        return ExchangeBuilder
                .directExchange(Constant.ORDER_DELAY_EXCHANGE_KEY)
                .delayed()
                .durable(true)
                .build();
    }

    /**
     * 订单延迟队列
     */
    @Bean
    public Queue orderDelayQueue() {
        return new Queue(Constant.ORDER_DELAY_QUEUE_KEY,true);
    }

    @Bean
    public Binding delayedBinding(){
        return BindingBuilder.bind(orderDelayQueue()).to(orderDelayExchange()).with(Constant.ORDER_DELAY_KEY);
    }


    @Bean
    public FanoutExchange payExchange() {
        return new FanoutExchange(Constant.PAY_EXCHANGE_KEY);
    }


    @Bean
    public Queue payQueue() {
        return new Queue(Constant.PAY_QUEUE_KEY, true);
    }

    @Bean
    public Binding payBinding() {
        return BindingBuilder.bind(payQueue()).to(payExchange());
    }

}
