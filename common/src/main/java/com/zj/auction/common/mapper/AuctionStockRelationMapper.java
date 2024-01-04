package com.zj.auction.common.mapper;

import com.zj.auction.common.model.AuctionStockRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuctionStockRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuctionStockRelation record);

    AuctionStockRelation selectByPrimaryKey(Long id);

    List<AuctionStockRelation> selectAll();

    int updateByPrimaryKey(AuctionStockRelation record);
}