//package com.zj.auction.general.app.service.impl;
//
//import com.zj.auction.common.constant.RedisConstant;
//import com.zj.auction.common.enums.AuctionStatEnum;
//import com.zj.auction.common.mapper.AuctionMapper;
//import com.zj.auction.common.mapper.AuctionStockRelationMapper;
//import com.zj.auction.common.mapper.GoodsMapper;
//import com.zj.auction.common.model.Auction;
//import com.zj.auction.common.model.AuctionStockRelation;
//import com.zj.auction.common.model.Goods;
//import com.zj.auction.common.model.Stock;
//import com.zj.auction.general.app.service.AuctionService;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Objects;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class AuctionServiceImpl implements AuctionService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AuctionServiceImpl.class);
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
//    private final AuctionMapper auctionMapper;
//    private final GoodsMapper goodsMapper;
//    private final AuctionStockRelationMapper auctionStockMapper;
//    private final RedisTemplate<String,Object> redisTemplate;
//    private final RedissonClient redissonClient;
//
//    @Autowired
//    public AuctionServiceImpl(AuctionMapper auctionMapper, GoodsMapper goodsMapper, AuctionStockRelationMapper auctionStockMapper, RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
//        this.auctionMapper = auctionMapper;
//        this.goodsMapper = goodsMapper;
//        this.auctionStockMapper = auctionStockMapper;
//        this.redisTemplate = redisTemplate;
//        this.redissonClient = redissonClient;
//    }
//
//    @Override
//    @Transactional
//    public void addAuction(Stock stock) {
//        Long goodsId = stock.getGoodsId();
//        BigDecimal price = stock.getPrice();
//        Auction auction = auctionMapper.selectAuctionByGoodsIdAndPrice(goodsId, price);
//        if(Objects.isNull(auction)){
//            auction = createAuction(goodsId, price);
//            if(Objects.isNull(auction)){
//                return;
//            }
//        }
//        AuctionStockRelation relation = new AuctionStockRelation();
//        relation.setAuctionId(auction.getId());
//        relation.setStockId(stock.getId());
//        relation.setStockNumber(stock.getStockNumber());
//        auctionStockMapper.insertSelective(relation);
//    }
//
//    private Auction createAuction(Long goodsId, BigDecimal price) {
//        // 全局锁
//        RLock lock = redissonClient.getLock(RedisConstant.AUCTION_GENERATOR_LOCK_KEY);
//        Auction auction;
//        try {
//            boolean b = lock.tryLock(5, TimeUnit.SECONDS);
//            if (b) {
//                auction = auctionMapper.selectAuctionByGoodsIdAndPrice(goodsId, price);
//                if(Objects.nonNull(auction)){
//                    return auction;
//                }else {
//                    auction = new Auction();
//                }
//                Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
//                LocalDate now = LocalDate.now();
//                LocalDate tomorrow = now.plusDays(1);
//                String auctionName = createAuctionName(goods.getGoodsName(), tomorrow.format(FORMATTER));
//                auction.setGoodsId(goodsId);
//                auction.setAuctionName(auctionName);
//                auction.setAuctionStatus(AuctionStatEnum.UN_START.getCode());
//                auction.setPrice(price);
//                auction.setStockQuantity(1);
//                // todo 竞拍区
//        //        auction.setAuctionAreaId();
//                auctionMapper.insertSelective(auction);
//                return auction;
//            }else {
//                LOGGER.error("创建拍品获取锁失败,goodsId:{},price:{}",goodsId,price);
//            }
//        }catch (Exception e){
//            LOGGER.error("转拍回调获取锁异常",e);
//        }
//        return null;
//    }
//
//    private String createAuctionName(String goodsName,String tagDateStr){
//        return goodsName.concat("-").concat(getAutoId(tagDateStr));
//    }
//
//    public String getAutoId(String dateStr) {
//        String key = RedisConstant.AUCTION_SEQUENCE_KEY+dateStr;
//        Long increment=redisTemplate.opsForValue().increment(key);
//        if (Objects.isNull(increment)) {
//            throw new RuntimeException("生成自增序列错误!");
//        }
//        if (1 == increment) {
//            redisTemplate.expire(key,1, TimeUnit.DAYS);
//        }
//        return String.format("%04d",increment);
//    }
//
//}
