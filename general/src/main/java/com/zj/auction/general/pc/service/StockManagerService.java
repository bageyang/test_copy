package com.zj.auction.general.pc.service;

import com.zj.auction.common.dto.AuctionStockNumDto;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import io.swagger.models.auth.In;

import java.util.List;

public interface StockManagerService {

    List<Stock> listGoodsStock(Long goodsId);

    Stock getStockInfo(Long stockNumber);

    boolean addAndTransfer2Auction(AuctionStockNumDto auctionStockNumDto);

    List<Order> listStockTransferInfo(Long stockNumber);
}
