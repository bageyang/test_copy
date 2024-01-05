package com.zj.auction.seckill.service;

import com.zj.auction.common.model.Auction;

import java.util.List;

public interface AuctionService {
    /**
     * 拍品扣减库存
     * @return null or 锁定的库存号
     */
    String decreAuctionStock(Long auctionId);

    /**
     * 定时任务预热拍品
     */
    void warmUpAuction(Integer areaId);

    /**
     * 用户商品上架拍卖
     * @param stockId 用户拥有的库存id
     * @return 拍品信息 TODO 不在秒杀服务处理此业务,挪到通用服务中去
     */
    @Deprecated
    Auction transfer2Auction(Long stockId);

    /**
     * 获取拍品信息
     * @param auctionId 拍品id
     * @return 拍品信息
     */
    Auction getAuctionInfo(Long auctionId);

    /**
     * 获取当前场次拍品列表
     * @param areaId 场次id
     * @return 拍品列表
     */
    List<Auction> listAuctionByAreaId(Integer areaId);

}
