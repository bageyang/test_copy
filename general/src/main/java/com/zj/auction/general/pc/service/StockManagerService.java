package com.zj.auction.general.pc.service;

import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import io.swagger.models.auth.In;

import java.util.List;

public interface StockManagerService {
    // todo 添加商品库存
    // todo 查看商品库存状态
    List<Stock> listGoodsStock(Long goodsId);
    // todo 查看库存详情
    Stock getStockInfo(Long stockNumber);
    // todo 添加库存并上架拍品
    boolean addAndTransfer2Auction(Long goodsId,Integer num,Long ownerId);
    // todo 查看库存流转情况
    List<Order> listStockTransferInfo(Long stockNumber);
}
