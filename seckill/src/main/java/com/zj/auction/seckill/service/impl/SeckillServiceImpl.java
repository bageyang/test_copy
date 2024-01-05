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
import com.zj.auction.common.vo.AuctionVo;
import com.zj.auction.seckill.service.AuctionService;
import com.zj.auction.seckill.service.RedisService;
import com.zj.auction.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    private final RedisService redisService;
    private final AuctionService auctionService;
    private final RabbitTemplate rabbitTemplate;
    private static ConcurrentHashMap<String,Object> SELL_OUT_AUCTION_MAP =new ConcurrentHashMap<>();

    @Autowired
    public SeckillServiceImpl(RedisService redisService, AuctionService auctionService, RabbitTemplate rabbitTemplate) {
        this.redisService = redisService;
        this.auctionService = auctionService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Ret<BaseOrderDto> seckill(Long auctionId) {
        // 1.状态检查
        Optional<Ret<BaseOrderDto>> checkRet = doCheck(auctionId);
        if(checkRet.isPresent()){
            return checkRet.get();
        }
        // 2.扣减库存 decrement
        Optional<String> ret = decreStock(auctionId);
        if(!ret.isPresent()){
            return Ret.error(StatusEnum.SECKILL_FAIL_ERROR);
        }
        // todo 后续所有异常需要捕获并补偿消息
        // 3.发送mq 生成订单
        long orderSn = SnowFlake.nextId();
        BaseOrderDto baseOrderDto = buildOrderMqMSg(orderSn,ret.get(),auctionId);
        rabbitTemplate.convertAndSend(Constant.ORDER_EXCHANGE_KEY, null, baseOrderDto);
        return Ret.ok(baseOrderDto);
    }

    private BaseOrderDto buildOrderMqMSg(Long orderSn, String sn, Long auctionId) {
        BaseOrderDto baseOrderDto = new BaseOrderDto();
        baseOrderDto.setOrderSn(orderSn);
        baseOrderDto.setSn(Long.parseLong(sn));
        baseOrderDto.setCreateTime(LocalDateTime.now());
        baseOrderDto.setAuctionId(auctionId);
        return baseOrderDto;
    }

    private Optional<Ret<BaseOrderDto>> doCheck(Long auctionId) {
        // userId
        if(Objects.isNull(auctionId)){
            return Optional.of(Ret.error(StatusEnum.PARAM_ERROR));
        }
        String auctionIdStr = String.valueOf(auctionId);
        if(SELL_OUT_AUCTION_MAP.containsKey(auctionIdStr)){
            return Optional.of(Ret.error(StatusEnum.AUCTION_FINISH_ERROR));
        }
        Integer num = (Integer) redisService.hGet(RedisConstant.AUCTION_REMAINDER_KEY, auctionIdStr);
        if(num<=0){
            SELL_OUT_AUCTION_MAP.put(auctionIdStr,num);
            return Optional.of(Ret.error(StatusEnum.AUCTION_FINISH_ERROR));
        }
        // 获取拍品信息
        AuctionVo auction =  (AuctionVo) redisService.hGet(RedisConstant.AUCTION_INFO_KEY,auctionIdStr);
        if(Objects.isNull(auction)){
            return Optional.of(Ret.error(StatusEnum.AUCTION_MISS_ERROR)) ;
        }
        Integer auctionAreaId = auction.getAuctionAreaId();
        // 状态判断
        Byte auctionStatus = auction.getAuctionStatus();
        if(AuctionStatEnum.isFinish(auctionStatus)){
            return Optional.of(Ret.error(StatusEnum.AUCTION_FINISH_ERROR));
        }
        // todo 区域时间判断

        // todo 用户判断
        return Optional.empty();
    }

    private Optional<String> decreStock(Long auctionId) {
        return Optional.ofNullable(auctionService.deductAuctionStock(auctionId));
    }
}
