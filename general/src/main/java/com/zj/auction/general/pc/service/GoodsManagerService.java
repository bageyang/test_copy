package com.zj.auction.general.pc.service;

import com.zj.auction.common.model.Goods;
import com.zj.auction.common.model.GoodsCategory;
import com.zj.auction.common.query.GoodsQuery;

import java.util.List;

public interface GoodsManagerService {

    boolean addGoods(Goods goods);

    boolean updateGoods(Goods goods);

    List<Goods> listGoods(GoodsQuery goods);

    Goods getGoodsInfo(Long goodsId);

    List<GoodsCategory> listGoodsCategory();

    boolean addGoodsCategory(GoodsCategory goodsCategory);

    boolean updateGoodsCategory(GoodsCategory goodsCategory);
}
