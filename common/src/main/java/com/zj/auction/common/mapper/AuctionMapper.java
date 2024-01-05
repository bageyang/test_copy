package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Auction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AuctionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Auction record);

    Auction selectByPrimaryKey(Long id);

    List<Auction> selectAll();

    int updateByPrimaryKey(Auction record);

    List<Auction> listAuctionByAreaId(Integer areaId);
}