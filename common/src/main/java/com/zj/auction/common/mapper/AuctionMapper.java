package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Auction;
import java.util.List;

public interface AuctionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Auction record);

    Auction selectByPrimaryKey(Long id);

    List<Auction> selectAll();

    int updateByPrimaryKey(Auction record);
}