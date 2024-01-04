package com.zj.auction.seckill.service.impl;

import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.mapper.AuctionMapper;
import com.zj.auction.common.mapper.AuctionStockRelationMapper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.AuctionStockRelation;
import com.zj.auction.seckill.service.AuctionService;
import com.zj.auction.seckill.service.RedisService;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final RedissonClient redissonClient;
    private final RedisService redisService;
    private final AuctionMapper auctionMapper;
    private final OrderMapper orderMapper;
    private final AuctionStockRelationMapper auctionStockRelationMapper;

    @Autowired
    public AuctionServiceImpl(RedissonClient redissonClient, RedisService redisService, AuctionMapper auctionMapper, OrderMapper orderMapper, AuctionStockRelationMapper auctionStockRelationMapper) {
        this.redissonClient = redissonClient;
        this.redisService = redisService;
        this.auctionMapper = auctionMapper;
        this.orderMapper = orderMapper;
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
        List<Auction> auctions = auctionMapper.listAuctionByAreaId(areaId);
        List<Long> ids = auctions.stream().map(Auction::getId).collect(Collectors.toList());
        List<AuctionStockRelation> auctionStockRelations = auctionStockRelationMapper.listStockByAuctionIds(ids);
        Map<Long, List<AuctionStockRelation>> auctionGroup = auctionStockRelations.stream().collect(Collectors.groupingBy(AuctionStockRelation::getAuctionId));
        auctionGroup.forEach((k,v)->{
            String key = String.valueOf(k);
            // hash 缓存库存数量
            redisService.hSet(RedisConstant.AUCTION_VOLUME_KEY,key,v.size());
            // list 缓存库存编号
            redisService.lPushAll(RedisConstant.AUCTION_STOCK_KEY+key,v);
        });
    }

    @Override
    public Auction transfer2Auction(Long stockId) {
        return null;
    }

    @Override
    public Auction getAuctionInfo(Long auctionId) {
        Auction auction = (Auction) redisService.hGet(RedisConstant.AUCTION_INFO_KEY, String.valueOf(auctionId));
        if(Objects.isNull(auction)){
            auction = loadAuction(auctionId);
        }
        return auction;
    }

    private Auction loadAuction(Long auctionId) {
        Auction auction = auctionMapper.selectByPrimaryKey(auctionId);
        // todo 填充商品信息
        redisService.hSet(RedisConstant.AUCTION_INFO_KEY,String.valueOf(auctionId),auction);
        return auction;
    }

    @Override
    public List<Auction> listAuctionByAreaId(Integer areaId) {
        // todo 填充商品信息
        return auctionMapper.listAuctionByAreaId(areaId);
    }
}
