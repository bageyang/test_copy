package com.zj.auction.seckill.service.impl;

import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.mapper.AuctionMapper;
import com.zj.auction.common.mapper.AuctionStockRelationMapper;
import com.zj.auction.common.mapper.GoodsMapper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.AuctionStockRelation;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.vo.AuctionListVo;
import com.zj.auction.common.vo.AuctionVo;
import com.zj.auction.seckill.service.AuctionService;
import com.zj.auction.seckill.service.RedisService;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final RedissonClient redissonClient;
    private final RedisService redisService;
    private final AuctionMapper auctionMapper;
    private final GoodsMapper goodsMapper;
    private final OrderMapper orderMapper;
    private final AuctionStockRelationMapper auctionStockRelationMapper;

    @Autowired
    public AuctionServiceImpl(RedissonClient redissonClient, RedisService redisService, AuctionMapper auctionMapper, GoodsMapper goodsMapper, OrderMapper orderMapper, AuctionStockRelationMapper auctionStockRelationMapper) {
        this.redissonClient = redissonClient;
        this.redisService = redisService;
        this.auctionMapper = auctionMapper;
        this.goodsMapper = goodsMapper;
        this.orderMapper = orderMapper;
        this.auctionStockRelationMapper = auctionStockRelationMapper;
    }

    @Override
    public String deductAuctionStock(Long auctionId) {
        // redis检查
        try {
            RScript script = redissonClient.getScript();
            List<Object> keys = new ArrayList<>();
            keys.add(RedisConstant.AUCTION_REMAINDER_KEY);
            keys.add(RedisConstant.AUCTION_STOCK_KEY+ auctionId);
            return script.evalSha(RScript.Mode.READ_WRITE, RedisConstant.AUCTION_LUA_SCRIPT_SHA, RScript.ReturnType.VALUE,keys, auctionId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void warmUpAuction(Integer areaId) {
        List<Auction> auctions = auctionMapper.listAuctionByAreaId(areaId);
        List<Long> ids = auctions.stream().map(Auction::getId).collect(Collectors.toList());
        List<AuctionStockRelation> auctionStockRelations = auctionStockRelationMapper.listStockByAuctionIds(ids);
        Map<Long, List<AuctionStockRelation>> auctionGroup = auctionStockRelations.stream()
                .collect(Collectors.groupingBy(AuctionStockRelation::getAuctionId));
        auctionGroup.forEach((k,v)->{
            String key = String.valueOf(k);
            // hash 缓存库存数量
            redisService.hSet(RedisConstant.AUCTION_REMAINDER_KEY,key,v.size());
            Object[] snList = v.stream()
                    .map(AuctionStockRelation::getStockNumber)
                    .toArray();
            // list 缓存库存编号
            redisService.lPushAll(RedisConstant.AUCTION_STOCK_KEY+key,snList);
        });
    }


    @Override
    public AuctionVo getAuctionInfo(Long auctionId) {
        AuctionVo auction = (AuctionVo) redisService.hGet(RedisConstant.AUCTION_INFO_KEY, String.valueOf(auctionId));
        if(Objects.isNull(auction)){
            auction = loadAuction(auctionId);
        }
        Integer num = (Integer) redisService.hGet(RedisConstant.AUCTION_REMAINDER_KEY, String.valueOf(auctionId));
        auction.setStockQuantity(num);
        return auction;
    }

    private AuctionVo loadAuction(Long auctionId) {
        Auction auction = auctionMapper.selectByPrimaryKey(auctionId);
        Long goodsId = auction.getGoodsId();
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        AuctionVo vo = buildAuctionVo(auction,goods);
        redisService.hSet(RedisConstant.AUCTION_INFO_KEY,String.valueOf(auctionId),vo);
        return vo;
    }

    private AuctionVo buildAuctionVo(Auction auction, Goods goods) {
        AuctionVo vo = new AuctionVo();
        vo.setAuctionName(auction.getAuctionName());
        vo.setAuctionAreaId(auction.getAuctionAreaId());
        vo.setGoodsId(auction.getGoodsId());
        vo.setPrices(auction.getPrice());
        vo.setStockQuantity(auction.getStockQuantity());
        vo.setGoodsInfo(goods);
        return vo;
    }

    @Override
    public List<AuctionListVo> listAuctionByAreaId(Integer areaId) {
        List<Auction> auctions = auctionMapper.listAuctionByAreaId(areaId);
        List<Long> goodIds = auctions.stream()
                .map(Auction::getGoodsId)
                .collect(Collectors.toList());
        List<Goods> goods = goodsMapper.listGoodsInfoByIds(goodIds);
        Map<Long, Goods> goodsIdMap = goods.stream().collect(Collectors.toMap(Goods::getId, Function.identity()));
        return auctions.stream().map(e->buildAuctionListVo(e,goodsIdMap)).collect(Collectors.toList());
    }

    private AuctionListVo buildAuctionListVo(Auction e, Map<Long, Goods> goodsIdMap) {
        AuctionListVo vo = new AuctionListVo();
        vo.setAuctionId(e.getId());
        vo.setAuctionStatus(e.getAuctionStatus());
        vo.setGoodsId(e.getGoodsId());
        String imageUrl = Optional.ofNullable(goodsIdMap.get(e.getGoodsId()))
                .map(Goods::getImgUrl)
                .orElse("");
        vo.setImageUrl(imageUrl);
        return vo;
    }
}
