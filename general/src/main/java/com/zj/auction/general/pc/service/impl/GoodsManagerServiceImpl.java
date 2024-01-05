package com.zj.auction.general.pc.service.impl;

import com.zj.auction.common.model.Goods;
import com.zj.auction.general.pc.service.GoodsManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsManagerServiceImpl implements GoodsManagerService {
    @Override
    public boolean addGoods(Goods goods) {
        return false;
    }

    @Override
    public boolean updateGoods(Goods goods) {
        return false;
    }

    @Override
    public List<Goods> listGoods(Goods goods) {
        return null;
    }

    @Override
    public Goods getGoodsInfo(Long goodsId) {
        return null;
    }
}
