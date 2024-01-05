package com.zj.auction.general.pc.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.GoodsCategoryMapper;
import com.zj.auction.common.mapper.GoodsMapper;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.model.GoodsCategory;
import com.zj.auction.general.pc.service.GoodsManagerService;
import com.zj.auction.common.query.GoodsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GoodsManagerServiceImpl implements GoodsManagerService {
    private final GoodsMapper goodsMapper;
    private final GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    public GoodsManagerServiceImpl(GoodsMapper goodsMapper, GoodsCategoryMapper goodsCategoryMapper) {
        this.goodsMapper = goodsMapper;
        this.goodsCategoryMapper = goodsCategoryMapper;
    }

    @Override
    public boolean addGoods(Goods goods) {
        if (Objects.isNull(goods)) {
            throw new CustomException(StatusEnum.GOODS_INFO_BLANK_ERROR);
        }
        return goodsMapper.insertSelective(goods) > 0;
    }

    @Override
    public boolean updateGoods(Goods goods) {
        if (Objects.isNull(goods) || Objects.isNull(goods.getId())) {
            throw new CustomException(StatusEnum.GOODS_INFO_BLANK_ERROR);
        }
        return goodsMapper.updateByPrimaryKeySelective(goods) > 0;
    }

    @Override
    public List<Goods> listGoods(GoodsQuery goodsQuery) {
        PageHelper.startPage(goodsQuery.getPageNum(), goodsQuery.getPageSize());
        return goodsMapper.listGoodsInfo(goodsQuery);
    }

    @Override
    public Goods getGoodsInfo(Long goodsId) {
        if (Objects.isNull(goodsId)) {
            throw new CustomException(StatusEnum.GOODS_INFO_BLANK_ERROR);
        }
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public List<GoodsCategory> listGoodsCategory() {
        return goodsCategoryMapper.selectAll();
    }

    @Override
    public boolean addGoodsCategory(GoodsCategory goodsCategory) {
        return goodsCategoryMapper.insert(goodsCategory) > 0;
    }

    @Override
    public boolean updateGoodsCategory(GoodsCategory goodsCategory) {
        return goodsCategoryMapper.updateByPrimaryKey(goodsCategory) > 0;
    }
}
