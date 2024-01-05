package com.zj.auction.seckill.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.netty.buffer.*;
import io.netty.util.CharsetUtil;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.codec.Codec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

/**
 * 只在特定的redisson中使用
 */
public class FastJsonCodec extends BaseCodec {
    @Override
    public Decoder<Object> getMapValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getMapValueEncoder() {
        return encoder;
    }

    @Override
    public Decoder<Object> getMapKeyDecoder() {
        return decoder;
    }

    @Override
    public Encoder getMapKeyEncoder() {
        return encoder;
    }

    @Override
    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    private final Encoder encoder = in -> {
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        if(in instanceof String){
           return out.writeBytes(((String)in).getBytes());
        }else {
            ByteBufOutputStream os = new ByteBufOutputStream(out);
            JSON.writeTo(os, in, JSONWriter.Feature.PrettyFormat);
            return os.buffer();
        }
    };

    private final Decoder<Object> decoder = (buf, state) -> buf.toString(CharsetUtil.UTF_8);

    @Override
    public Decoder<Object> getValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }

}
