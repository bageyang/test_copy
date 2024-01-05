package com.zj.auction.common.mapper;

import com.zj.auction.common.model.AuctionArea;

public interface AuctionAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuctionArea record);

    int insertSelective(AuctionArea record);

    AuctionArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuctionArea record);

    int updateByPrimaryKey(AuctionArea record);
}