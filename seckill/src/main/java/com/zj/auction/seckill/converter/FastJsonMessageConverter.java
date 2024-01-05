package com.zj.auction.seckill.converter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

public class FastJsonMessageConverter extends AbstractMessageConverter {

    @Override
    protected Message createMessage(Object o, MessageProperties messageProperties) {
        return new Message(JSON.toJSONBytes(o,JSONWriter.Feature.WriteClassName),messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        byte[] body = message.getBody();
        return JSON.parseObject(body, Object.class,(String)null, JSONReader.Feature.SupportAutoType);
    }
}
