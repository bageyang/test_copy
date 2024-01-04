package com.zj.auction.common.mapper;

import com.zj.auction.common.model.AuctionStockRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuctionStockRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuctionStockRelation record);

    AuctionStockRelation selectByPrimaryKey(Long id);

    List<AuctionStockRelation> selectAll();

    int updateByPrimaryKey(AuctionStockRelation record);

    List<AuctionStockRelation> listStockByAuctionIds(@Param("auctionIds") List<Long> auctionIds);

    Long selectAuctionIdBySn(@Param("sn")String sn);
}