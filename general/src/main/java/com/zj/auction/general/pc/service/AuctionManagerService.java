package com.zj.auction.general.pc.service;

import com.zj.auction.common.model.Auction;
import com.zj.auction.common.query.AuctionQuery;

import java.util.List;

/**
 * 主要为拍品后台管理service类
 */
public interface AuctionManagerService {

    List<Auction> listAuction(AuctionQuery queryCondition);

    Auction getAuctionInfo(Long auctionId);

    Auction disAbleAuction(Long auction);
}
