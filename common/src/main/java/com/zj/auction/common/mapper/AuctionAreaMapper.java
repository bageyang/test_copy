package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.AuctionArea;
import com.zj.auction.common.model.UserBill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuctionAreaMapper extends BaseMapper<AuctionArea> {

    int deleteByPrimaryKey(Long id);

    int insert(AuctionArea auctionArea);

    AuctionArea selectByPrimaryKey(Long id);

    List<AuctionArea> selectAll();

    int updateByPrimaryKey(AuctionArea record);
}
