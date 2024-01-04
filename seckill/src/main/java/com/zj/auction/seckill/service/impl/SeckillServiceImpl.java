package com.zj.auction.seckill.service.impl;

import com.alibaba.nacos.common.utils.Objects;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.enums.AuctionStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.model.Auction;
import com.zj.auction.seckill.service.RedisService;
import com.zj.auction.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeckillServiceImpl implements SeckillService {
    private final RedisService redisService;

    @Autowired
    public SeckillServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Ret<Object> seckill(Long auctionId) {
        // 1.状态检查
        doCheck(auctionId);
        // 2.扣减库存 decrement
        Optional<String> ret = decreStock(auctionId);
        return null;
    }

    private void doCheck(Long auctionId) {
        // userId
        if(Objects.isNull(auctionId)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        // 获取拍品信息
        Auction auction =  (Auction) redisService.hGet(RedisConstant.AUCTION_INFO_KEY,String.valueOf(auctionId));
        Integer auctionAreaId = auction.getAuctionAreaId();
        // 状态判断
        Byte auctionStatus = auction.getAuctionStatus();
        if(AuctionStatEnum.isFinish(auctionStatus)){
            throw new CustomException(StatusEnum.AUCTION_FINISH_ERROR);
        }

        // todo 区域时间判断

        // todo 用户判断
    }

    private Optional<String> decreStock(Long auctionId) {
return null;
    }
}
