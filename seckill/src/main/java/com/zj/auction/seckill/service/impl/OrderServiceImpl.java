package com.zj.auction.seckill.service.impl;

import com.zj.auction.common.mapper.AuctionMapper;
import com.zj.auction.common.mapper.AuctionStockRelationMapper;
import com.zj.auction.common.mapper.GoodsMapper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.Order;
import com.zj.auction.seckill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final AuctionMapper auctionMapper;
    private final AuctionStockRelationMapper relationMapper;
    private final GoodsMapper goodsMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, AuctionMapper auctionMapper, AuctionStockRelationMapper relationMapper, GoodsMapper goodsMapper) {
        this.orderMapper = orderMapper;
        this.auctionMapper = auctionMapper;
        this.relationMapper = relationMapper;
        this.goodsMapper = goodsMapper;
    }

    @Override
    public void generatorOrder(String sn, Long userId) {
        //
        Long auctionId = relationMapper.selectAuctionIdBySn(sn);
        Auction auction = auctionMapper.selectByPrimaryKey(auctionId);
        BigDecimal prices = auction.getPrices();
        Order order = new Order();
        order.setTotalAmount(prices);
        // todo 全局id
        // item 不够绑定
        // 需要提前生产一个id 代表当前订单id
        order.setOrderSn("");
        order.setGoodsId(auction.getGoodsId());
        order.setCreateTime(LocalDateTime.now());
        order.setUserId(userId);
        orderMapper.insert(order);
    }
}
