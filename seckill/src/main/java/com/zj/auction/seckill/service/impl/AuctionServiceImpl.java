package com.zj.auction.seckill.service.impl;

import com.zj.auction.common.mapper.AuctionMapper;
import com.zj.auction.common.mapper.AuctionStockRelationMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.seckill.service.AuctionService;
import com.zj.auction.seckill.service.RedisService;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final RedissonClient redissonClient;
    private final RedisService redisService;
    private final AuctionMapper auctionMapper;
    private final AuctionStockRelationMapper auctionStockRelationMapper;

    @Autowired
    public AuctionServiceImpl(RedissonClient redissonClient, RedisService redisService, AuctionMapper auctionMapper, AuctionStockRelationMapper auctionStockRelationMapper) {
        this.redissonClient = redissonClient;
        this.redisService = redisService;
        this.auctionMapper = auctionMapper;
        this.auctionStockRelationMapper = auctionStockRelationMapper;
    }

    @Override
    public String decreAuctionStock(Long auctionId) {
        // redis检查
        RScript script = redissonClient.getScript();
//        script.eval()
        return null;
    }

    @Override
    public void warmUpAuction(Integer areaId) {

    }

    @Override
    public Auction transfer2Auction(Long stockId) {
        return null;
    }

    @Override
    public Auction getAuctionInfo(Long auctionId) {
        return null;
    }

    @Override
    public List<Auction> listAuctionByAreaId(Integer areaId) {
        return null;
    }
}
