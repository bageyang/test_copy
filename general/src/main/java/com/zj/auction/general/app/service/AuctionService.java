package com.zj.auction.general.app.service;

import com.zj.auction.common.model.Stock;

public interface AuctionService {
    /**
     * 添加拍品
     */
    void addAuction(Stock stock);
}
