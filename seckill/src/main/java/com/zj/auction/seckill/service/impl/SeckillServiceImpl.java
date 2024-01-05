package com.zj.auction.seckill.service.impl;

import com.alibaba.nacos.common.utils.Objects;
import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.dto.BaseOrderDto;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.enums.AuctionStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.util.SnowFlake;
import com.zj.auction.seckill.service.AuctionService;
import com.zj.auction.seckill.service.RedisService;
import com.zj.auction.seckill.service.SeckillService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SeckillServiceImpl implements SeckillService {
    private final RedisService redisService;
    private final AuctionService auctionService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SeckillServiceImpl(RedisService redisService, AuctionService auctionService, RabbitTemplate rabbitTemplate) {
        this.redisService = redisService;
        this.auctionService = auctionService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Ret<Object> seckill(Long auctionId) {
        // 1.状态检查
        doCheck(auctionId);
        // 2.扣减库存 decrement
        Optional<String> ret = decreStock(auctionId);
        if(!ret.isPresent()){
            return Ret.error(StatusEnum.SECKILL_FAIL_ERROR);
        }
        // 3.发送mq 生成订单
        long orderId = SnowFlake.nextId();
        BaseOrderDto baseOrderDto = buildOrderMqMSg(orderId,ret.get(),auctionId);
        rabbitTemplate.convertAndSend(Constant.ORDER_EXCHANGE_KEY, null, baseOrderDto);
        return Ret.ok(baseOrderDto);
    }

    private BaseOrderDto buildOrderMqMSg(Long orderId, String sn, Long auctionId) {
        BaseOrderDto baseOrderDto = new BaseOrderDto();
        baseOrderDto.setOrderId(orderId);
        baseOrderDto.setSn(Long.parseLong(sn));
        baseOrderDto.setCreateTime(LocalDateTime.now());
        baseOrderDto.setAuctionId(auctionId);
        return baseOrderDto;
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
        return Optional.ofNullable(auctionService.decreAuctionStock(auctionId));
    }
}
