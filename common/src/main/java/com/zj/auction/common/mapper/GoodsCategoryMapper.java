package com.zj.auction.common.mapper;

import com.zj.auction.common.model.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GoodsCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsCategory record);

    GoodsCategory selectByPrimaryKey(Long id);

    List<GoodsCategory> selectAll();

    int updateByPrimaryKey(GoodsCategory record);
}