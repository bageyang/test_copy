package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Auction;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Auction record);

    int insertSelective(Auction record);

    Auction selectByPrimaryKey(Long id);

    List<Auction> selectAll();

    int updateByPrimaryKey(Auction record);

    int updateByPrimaryKeySelective(Auction record);

    List<Auction> listAuctionByAreaId(@Param("areaId") Integer areaId);

    Auction selectAuctionByGoodsIdAndPrice(@Param("goodsId") Long goodsId, @Param("price")BigDecimal price);
}