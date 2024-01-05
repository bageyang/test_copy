package com.zj.auction.seckill;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.enums.AuctionStatEnum;
import com.zj.auction.common.mapper.GoodsMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.util.SnowFlake;
import com.zj.auction.seckill.service.RedisService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import netscape.javascript.JSObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void inserRedisList() throws InterruptedException {
//        int num = 1000000;
//        for (int i = 0; i < num; i++) {
//            redisService.lPush("auction:stock","socket_"+i);
//        }
//        redissonClient.getLock("auction:test_lock").tryLock(60, TimeUnit.SECONDS);
        RScript script = redissonClient.getScript();
        RLock lock = redissonClient.getLock();
        lock.tryLock();
        lock.lock(1);
        List<Object> keys = new ArrayList<>();
        keys.add("auction:volume");
        keys.add("auction:stock");
        String auctionId = "auction01";
        String value = script.evalSha(RScript.Mode.READ_WRITE, "a1b4d39b78f9ee7c73e6f6d9a681b0f8be081d74", RScript.ReturnType.VALUE,keys, auctionId,0,1);
        System.out.println(value);
    }

    @Test
    public void testGoods(){
        List<Goods> goods = goodsMapper.selectAll();
        goods.stream().forEach(e-> System.out.println(e.getGoodsName()));
    }

    @Test
    public void testRedisSerialize(){

        Auction o = (Auction)redisService.hGet(RedisConstant.AUCTION_INFO_KEY, String.valueOf(1002L));
        System.out.println(JSON.toJSONString(o));
    }

    public static void main(String[] args) {
        String auctionId = "auction01";
        ByteBuf buf = Unpooled.copiedBuffer(auctionId.getBytes());
        String s = JSON.parseObject(buf.array(), String.class);
        System.out.println(s);
//        String o = JSON.parseObject(new ByteBufInputStream(buf), String.class);
    }

    @Test
    public void testIdGenerator(){
        for (int i = 0; i < 10; i++) {
            System.out.println(SnowFlake.nextId());
        }
    }
}
