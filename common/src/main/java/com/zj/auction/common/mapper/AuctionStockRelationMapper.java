package com.zj.auction.common.mapper;

import com.zj.auction.common.model.AuctionStockRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AuctionStockRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuctionStockRelation record);

    int insertSelective(AuctionStockRelation record);

    AuctionStockRelation selectByPrimaryKey(Long id);

    List<AuctionStockRelation> selectAll();

    int updateByPrimaryKey(AuctionStockRelation record);

    int updateByPrimaryKeySelective(AuctionStockRelation record);

    List<AuctionStockRelation> listStockByAuctionIds(@Param("auctionIds") List<Long> ids);

    List<AuctionStockRelation> listStockByAuctionId(@Param("auctionId") Long id);

    Long getAuctionIdByStockNumber(@Param("stockNumber") Long stockNumber);

    List<AuctionStockRelation> listUnExclusiveStock(@Param("auctionId") Long auctionId);

    List<AuctionStockRelation> listRelationByStockIds(@Param("stockIds") List<Long> ids);

    int regroupAuctionRelation(@Param("id") Long id,@Param("actionId") Long actionId);
}