package com.zj.auction.common.mapper;

import com.zj.auction.common.model.PayLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PayLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PayLog record);

    PayLog selectByPrimaryKey(Long id);

    List<PayLog> selectAll();

    int updateByPrimaryKey(PayLog record);
}