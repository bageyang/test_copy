package com.zj.auction.seckill.service.impl;

import com.zj.auction.seckill.service.AuctionService;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final RedissonClient redissonClient;

    @Autowired
    public AuctionServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public String decreAuctionStock(Long auctionId) {
        // redis检查
        RScript script = redissonClient.getScript();
//        script.eval()
        return null;
    }
}
