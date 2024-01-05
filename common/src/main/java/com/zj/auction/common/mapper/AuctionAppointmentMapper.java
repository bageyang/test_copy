package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.AuctionAppointment;

public interface AuctionAppointmentMapper extends BaseMapper<AuctionAppointment> {
    int deleteByPrimaryKey(Long id);

    int insert(AuctionAppointment record);

    int insertSelective(AuctionAppointment record);

    AuctionAppointment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuctionAppointment record);

    int updateByPrimaryKey(AuctionAppointment record);
}