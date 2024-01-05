package com.zj.auction.general.pc.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.zj.auction.common.mapper.*;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.AuctionStockRelation;
import com.zj.auction.common.model.Stock;
import com.zj.auction.common.query.AuctionQuery;
import com.zj.auction.general.app.service.AuctionService;
import com.zj.auction.general.pc.service.AuctionManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuctionManagerServiceImpl implements AuctionManagerService {
    private final AuctionMapper auctionMapper;
    private final AuctionStockRelationMapper relationMapper;
    private final StockMapper stockMapper;
    private final AuctionService auctionService;

    @Autowired
    public AuctionManagerServiceImpl(AuctionMapper auctionMapper, AuctionStockRelationMapper relationMapper, SystemCnfMapper systemCnfMapper, OrderMapper orderMapper, StockMapper stockMapper, AuctionService auctionService) {
        this.auctionMapper = auctionMapper;
        this.relationMapper = relationMapper;
        this.stockMapper = stockMapper;
        this.auctionService = auctionService;
    }


    @Override
    public List<Auction> listAuction(AuctionQuery queryCondition) {
        if (Objects.isNull(queryCondition)) {
            return Collections.emptyList();
        }
        if (Objects.nonNull(queryCondition.getStockNumber())) {
            Long auctionId = relationMapper.getAuctionIdByStockNumber(queryCondition.getStockNumber());
            queryCondition.setAuctionId(auctionId);
        }
        PageHelper.startPage(queryCondition.getPageNum(), queryCondition.getPageSize());
        return auctionMapper.listAuction(queryCondition);
    }

    @Override
    public Auction getAuctionInfo(Long auctionId) {
        return auctionMapper.selectByPrimaryKey(auctionId);
    }

    @Override
    public Auction disAbleAuction(Long auction) {
        return null;
    }



    @Override
    public void reAuctionOrder() {
        // 查询今日所有拍卖场次
        // 筛选已结束的拍卖场次
        // 获取流拍订单
        List<AuctionStockRelation> relations = relationMapper.listUnExclusiveStock(1L);
        if (CollectionUtils.isEmpty(relations)) {
            return;
        }
        // 获取对应 stockNumber
        // 查询是否有对应的拍品
        Map<Long, Long> stockRelationIdMap = relations.stream()
                .collect(Collectors.toMap(AuctionStockRelation::getStockId, AuctionStockRelation::getId));
        List<Long> stockIds = Lists.newArrayList(stockRelationIdMap.keySet());
        List<List<Long>> partition = Lists.partition(stockIds, 200);
        Map<String, Auction> auctionMap = new HashMap<>();
        for (List<Long> stockSnPart : partition) {
            List<Stock> stockList = stockMapper.listStockBySnList(stockSnPart);
            for (Stock stock : stockList) {
                Auction auction = getAuction(auctionMap, stock);
                Long relationId = stockRelationIdMap.get(stock.getId());
                // 重组 reorganization
                relationMapper.regroupAuctionRelation(relationId,auction.getId());
                log.info("重新上架库存:{}到拍品:{}",stock.getStockNumber(),auction.getId());
            }

        }
    }

    private Auction getAuction(Map<String, Auction> auctionMap, Stock stock) {
        Long goodsId = stock.getGoodsId();
        BigDecimal cashPrice = stock.getCashPrice();
        BigDecimal integralPrice = stock.getIntegralPrice();
        String key = getKey(goodsId, cashPrice);
        if (auctionMap.containsKey(key)) {
            return auctionMap.get(key);
        }
        Auction auction = auctionMapper.selectAuctionByGoodsIdAndCashPrice(goodsId, cashPrice);
        if (Objects.isNull(auction)) {
            auction = auctionService.createAuction(goodsId, cashPrice, integralPrice);
        }
        auctionMap.put(key, auction);
        return auction;
    }

    private String getKey(Long goodsId, BigDecimal cashPrice) {
        return String.valueOf(goodsId).concat(cashPrice.toPlainString());
    }
}
