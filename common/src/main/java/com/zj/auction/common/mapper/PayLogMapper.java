package com.zj.auction.common.mapper;

import com.zj.auction.common.model.PayLog;
import java.util.List;

public interface PayLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PayLog record);

    PayLog selectByPrimaryKey(Long id);

    List<PayLog> selectAll();

    int updateByPrimaryKey(PayLog record);
}