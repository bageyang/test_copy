package com.zj.auction.seckill.service;

import com.zj.auction.common.model.Auction;
import com.zj.auction.common.vo.AuctionListVo;
import com.zj.auction.common.vo.AuctionVo;

import java.util.List;

public interface AuctionService {
    /**
     * 拍品扣减库存
     * @return null or 锁定的库存号
     */
    String deductAuctionStock(Long auctionId);

    /**
     * 定时任务预热拍品
     */
    void warmUpAuction(Integer areaId);


    /**
     * 获取拍品信息
     * @param auctionId 拍品id
     * @return 拍品信息
     */
    AuctionVo getAuctionInfo(Long auctionId);

    /**
     * 获取当前场次拍品列表
     * @param areaId 场次id
     * @return 拍品列表
     */
    List<AuctionListVo> listAuctionByAreaId(Integer areaId);

}
