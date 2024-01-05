package com.zj.auction.general.pc.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.enums.OrderStatEnum;
import com.zj.auction.common.mapper.AuctionMapper;
import com.zj.auction.common.mapper.AuctionStockRelationMapper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.mapper.SystemCnfMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.AuctionStockRelation;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.AuctionQuery;
import com.zj.auction.general.pc.service.AuctionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuctionManagerServiceImpl implements AuctionManagerService {
    private final AuctionMapper auctionMapper;
    private final AuctionStockRelationMapper relationMapper;
    private final SystemCnfMapper systemCnfMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public AuctionManagerServiceImpl(AuctionMapper auctionMapper, AuctionStockRelationMapper relationMapper, SystemCnfMapper systemCnfMapper, OrderMapper orderMapper) {
        this.auctionMapper = auctionMapper;
        this.relationMapper = relationMapper;
        this.systemCnfMapper = systemCnfMapper;
        this.orderMapper = orderMapper;
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
        return auctionMapper.selectByPrimaryKey(auctionId);
    }

    @Override
    public Auction disAbleAuction(Long auction) {
        return null;
    }


    @Override
    public void handUnAuctionStock() {
        // 查询今日所有拍卖场次
        // 筛选已结束的拍卖场次
        List<Long> areaIds = new ArrayList<>();
        String s = systemCnfMapper.selectValueByKeyName(Constant.WAIT_PAY_TIME_OUT);
        // 查询结束场次的拍品
        List<Auction> unAuctionList = auctionMapper.listUnAuctionOrderByAreaId(areaIds);
        if(CollectionUtils.isEmpty(unAuctionList)){
            return;
        }
        for (Auction auction : unAuctionList) {
            Long auctionId = auction.getId();
            // 未占有库存
            List<AuctionStockRelation> relations = relationMapper.listUnExclusiveStock(auctionId);
            if(CollectionUtils.isEmpty(relations)){
                continue;
            }
            List<Long> stockNumberList = relations.stream()
                    .map(AuctionStockRelation::getStockNumber)
                    .collect(Collectors.toList());
            List<Order> orders = orderMapper.listOrderByStockNumberAndStatus(stockNumberList, OrderStatEnum.ON_AUCTION.getCode());
            if(CollectionUtils.isEmpty(orders)){
                continue;
            }
            List<Long> orderIds = orders.stream().map(Order::getId).collect(Collectors.toList());
            orderMapper.updateOrders2Status(orderIds,OrderStatEnum.AUCTION_ABORTED.getCode());
        }
    }

    @Override
    public void reAuctionOrder() {
        // 获取流拍订单
        List<Order> orders = orderMapper.listOrderByStatus(OrderStatEnum.AUCTION_ABORTED.getCode());
        // todo
    }
}
