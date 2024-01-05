package com.zj.auction.seckill.service;

import com.zj.auction.common.dto.BaseOrderDto;
import com.zj.auction.common.dto.Ret;

/**
 * 秒杀服务 下单
 * @author yangbing
 */
public interface SeckillService {
    /**
     * 秒杀接口
     * @param auctionId 拍品id
     * @return fail or 锁定库存id
     */
    Ret<BaseOrderDto> seckill(Long auctionId);
}
