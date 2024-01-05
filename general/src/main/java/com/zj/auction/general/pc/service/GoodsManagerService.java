package com.zj.auction.general.pc.service;

import com.zj.auction.common.model.Goods;

import java.util.List;

public interface GoodsManagerService {
    // todo 添加商品
    // todo 修改商品
    // todo 商品列表
    // todo 商品详情
    //
    boolean addGoods(Goods goods);

    boolean updateGoods(Goods goods);

    List<Goods> listGoods(Goods goods);

    Goods getGoodsInfo(Long goodsId);
}
