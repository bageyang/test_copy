package com.zj.auction.general.app.service;

import com.zj.auction.common.enums.OrderTypeEnum;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.Stock;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionService {
    /**
     * 添加拍品
     */
    Auction addAuction(Stock stock, OrderTypeEnum orderType);

    Auction createAuction(Long goodsId, BigDecimal cashPrice, BigDecimal integralPrice);

    Auction getAuction(Long goodsId, BigDecimal cashPrice, BigDecimal integralPrice);

    /**
     * 批量添加库存进同一拍品
     * @param stockList
     */
    Auction addStockListIntoAuction(List<Stock> stockList);

    Auction getAuctionById(Long auctionId);

    boolean decrementQuantity(Long id,Long stockNumber);
}
