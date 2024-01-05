package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Goods;
import java.util.List;

import com.zj.auction.common.query.GoodsQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    List<Goods> listGoodsInfoByIds(@Param("goodIds") List<Long> goodIds);

    List<Goods> listGoodsInfo(GoodsQuery goodsQuery);
}