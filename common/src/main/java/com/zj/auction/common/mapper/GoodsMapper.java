package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    Goods selectByPrimaryKey(Long id);

    List<Goods> selectAll();

    int updateByPrimaryKey(Goods record);

    List<Goods> listGoodsInfoByIds(@Param("goodIds") List<Long> goodIds);
}