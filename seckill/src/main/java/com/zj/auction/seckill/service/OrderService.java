package com.zj.auction.seckill.service;

import com.zj.auction.common.dto.BaseOrderDto;

public interface OrderService {
    /**
     * 根据库存号生产订单
     * @param orderInfo
     */
    void generatorOrder(BaseOrderDto orderInfo);
}
