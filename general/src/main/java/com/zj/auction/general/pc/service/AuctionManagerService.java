package com.zj.auction.general.pc.service;

import com.zj.auction.common.model.Auction;

import java.util.List;

/**
 * 主要为拍品后台管理service类
 */
public interface AuctionManagerService {
    // todo 拍品列表
    List<Auction> listAuction(Auction queryCondition);
    // todo 拍品详情
    Auction getAuctionInfo(Long auctionId);
    // todo 下架拍品
    Auction disAbleAuction(Long auction);
    // todo
}
