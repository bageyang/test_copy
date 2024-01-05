package com.zj.auction.general.pc.service.impl;

import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import com.zj.auction.general.pc.service.StockManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockManagerServiceImpl implements StockManagerService {
    @Override
    public boolean addGoodsStock(Integer num) {
        return false;
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
    public boolean addAndTransfer2Auction(Long goodsId, Integer num) {
        return false;
    }

    @Override
    public List<Order> listStockTransferInfo(Long stockNumber) {
        return null;
    }
}
