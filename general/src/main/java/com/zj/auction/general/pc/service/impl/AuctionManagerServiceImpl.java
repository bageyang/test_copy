package com.zj.auction.general.pc.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.mapper.AuctionMapper;
import com.zj.auction.common.mapper.AuctionStockRelationMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.query.AuctionQuery;
import com.zj.auction.general.pc.service.AuctionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AuctionManagerServiceImpl implements AuctionManagerService {
    private final AuctionMapper auctionMapper;
    private final AuctionStockRelationMapper relationMapper;

    @Autowired
    public AuctionManagerServiceImpl(AuctionMapper auctionMapper, AuctionStockRelationMapper relationMapper) {
        this.auctionMapper = auctionMapper;
        this.relationMapper = relationMapper;
    }


    @Override
    public List<Auction> listAuction(AuctionQuery queryCondition) {
        if(Objects.isNull(queryCondition)){
            return Collections.emptyList();
        }
        if(Objects.nonNull(queryCondition.getStockNumber())){
           Long auctionId = relationMapper.getAuctionIdByStockNumber(queryCondition.getStockNumber());
           queryCondition.setAuctionId(auctionId);
        }
        PageHelper.startPage(queryCondition.getPageNum(),queryCondition.getPageSize());
        return auctionMapper.listAuction(queryCondition);
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
