package com.zj.auction.general.pc.service.impl;

import com.zj.auction.common.model.Auction;
import com.zj.auction.general.pc.service.AuctionManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuctionManagerServiceImpl implements AuctionManagerService {
    @Override
    public List<Auction> listAuction(Auction queryCondition) {
        return null;
    }

    @Override
    public Auction getAuctionInfo(Long auctionId) {
        return null;
    }

    @Override
    public Auction disAbleAuction(Long auction) {
        return null;
    }
}
