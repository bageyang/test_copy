package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.AuctionArea;

public interface AuctionAreaMapper extends BaseMapper<AuctionArea> {
    int deleteByPrimaryKey(Long id);

    int insert(AuctionArea record);

    int insertSelective(AuctionArea record);

    AuctionArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuctionArea record);

    int updateByPrimaryKey(AuctionArea record);
}