package com.zj.auction.seckill.service;

import com.zj.auction.common.dto.Ret;

/**
 * 秒杀服务 下单
 * @author yangbing
 */
public interface SeckillService {
    Ret<Object> seckill(Long auctionId);
}
