package com.zj.auction.seckill.service;

public interface OrderService {
    /**
     * 根据库存号生产订单
     * @param sn
     */
    void generatorOrder(String sn,Long userId);
}
