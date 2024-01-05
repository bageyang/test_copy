package com.zj.auction.seckill.service.impl;

import com.zj.auction.common.dto.BaseOrderDto;
import com.zj.auction.common.enums.OrderStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.*;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import com.zj.auction.seckill.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderMapper orderMapper;
    private final AuctionMapper auctionMapper;
    private final StockMapper stockMapper;
    private final AuctionStockRelationMapper relationMapper;
    private final GoodsMapper goodsMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, AuctionMapper auctionMapper, StockMapper stockMapper, AuctionStockRelationMapper relationMapper, GoodsMapper goodsMapper) {
        this.orderMapper = orderMapper;
        this.auctionMapper = auctionMapper;
        this.stockMapper = stockMapper;
        this.relationMapper = relationMapper;
        this.goodsMapper = goodsMapper;
    }


    @Override
    public void generatorOrder(BaseOrderDto orderInfo) {
        logger.info("生成用户订单:{}",orderInfo);
        Long sn = orderInfo.getSn();
        Long auctionId = orderInfo.getAuctionId();
        Auction auction = auctionMapper.selectByPrimaryKey(auctionId);
        Order ownerOrder = orderMapper.selectOwnerOrderBySnAndStatus(sn, OrderStatEnum.ON_AUCTION.getCode());
        Stock stock = stockMapper.selectOneBySn(sn);
        checkOrderStatus(ownerOrder,orderInfo,auction);
        Order order = buildOrder(orderInfo, auction, ownerOrder, stock);
        orderMapper.insert(order);
    }

    private Order buildOrder(BaseOrderDto orderInfo, Auction auction, Order ownerOrder, Stock stock) {
        Order order = new Order();
        order.setGoodsId(auction.getGoodsId());
        order.setAuctionId(orderInfo.getAuctionId());
        order.setOrderSn(orderInfo.getOrderSn());
        order.setStockId(stock.getId());
        order.setUserId(orderInfo.getUserId());
        order.setTotalAmount(auction.getPrices());
        order.setPayAmount(auction.getPrices());
        order.setItemId(ownerOrder.getId());
        order.setCreateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatEnum.UN_PAYMENT.getCode());
        return order;
    }

    /**
     * 检查秒杀订单状态
     * @param ownerOrder 原拥有这订单信息
     * @param orderInfo 秒杀订单信息
     * @param auction   拍品信息
     */
    private void checkOrderStatus(Order ownerOrder, BaseOrderDto orderInfo, Auction auction) {
        if(Objects.isNull(ownerOrder)){
            throw new CustomException(StatusEnum.OWNER_ORDER_MISS_ERROR);
        }
        if(Objects.isNull(auction)){
            throw new CustomException(StatusEnum.AUCTION_MISS_ERROR);
        }
        Long ownerSn = ownerOrder.getStockNumber();
        Long sn = orderInfo.getSn();
        Long ownerAuctionId = ownerOrder.getAuctionId();
        Long orderAuctionId = orderInfo.getAuctionId();
        if(!Objects.equals(ownerSn,sn)){
            throw new CustomException(StatusEnum.STOCK_NOT_MATCH_ERROR);
        }
        if(!Objects.equals(ownerAuctionId,orderAuctionId)){
            throw new CustomException(StatusEnum.AUCTION_NOT_MATCH_ERROR);
        }
    }
}
