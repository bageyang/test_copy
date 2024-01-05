package com.zj.auction.general.pc.service.impl;

import com.zj.auction.common.enums.OrderStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.AuctionStockRelationMapper;
import com.zj.auction.common.mapper.GoodsMapper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.mapper.StockMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import com.zj.auction.common.util.SnowFlake;
import com.zj.auction.general.app.service.AuctionService;
import com.zj.auction.general.pc.service.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StockManagerServiceImpl implements StockManagerService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final GoodsMapper goodsMapper;
    private final AuctionStockRelationMapper auctionStockMapper;
    private final StockMapper stockMapper;
    private final OrderMapper orderMapper;
    private final AuctionService auctionService;

    @Autowired
    public StockManagerServiceImpl(GoodsMapper goodsMapper, AuctionStockRelationMapper auctionStockMapper, StockMapper stockMapper, OrderMapper orderMapper, AuctionService auctionService) {
        this.goodsMapper = goodsMapper;
        this.auctionStockMapper = auctionStockMapper;
        this.stockMapper = stockMapper;
        this.orderMapper = orderMapper;
        this.auctionService = auctionService;
    }

    @Override
    public List<Stock> listGoodsStock(Long goodsId) {
        return null;
    }

    @Override
    public Stock getStockInfo(Long stockNumber) {
        return null;
    }

    @Override
    public boolean addAndTransfer2Auction(Long goodsId, Integer num,Long ownerId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if(Objects.isNull(goods)){
            throw new CustomException(StatusEnum.GOODS_INFO_BLANK_ERROR);
        }
        BigDecimal price = goods.getPrice();
        List<Stock> stockList = new ArrayList<>(num);
        List<Order> orderList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            byte code = OrderStatEnum.ON_AUCTION.getCode();
            Stock stock = new Stock();
            stock.setStockNumber(SnowFlake.nextId());
            stock.setGoodsId(goodsId);
            stock.setTransferNum(0);
            stock.setStockStatus(code);
            stock.setOwnerId(ownerId);
            stock.setPrice(price);
            stockList.add(stock);
            stockMapper.insertSelective(stock);
            Order order = new Order();
            order.setOrderSn(SnowFlake.nextId());
            order.setOrderStatus(code);
            order.setStockId(stock.getId());
            order.setGoodsId(goodsId);
            order.setStockNumber(stock.getStockNumber());
            order.setPayAmount(price);
            order.setUserId(ownerId);
            orderList.add(order);
        }
        Auction auction = auctionService.addStockListIntoAuction(stockList);
        orderList.forEach(e->{
            e.setAuctionId(auction.getId());
            orderMapper.insertSelective(e);
        });
        return true;
    }

    @Override
    public List<Order> listStockTransferInfo(Long stockNumber) {
        return null;
    }
}
