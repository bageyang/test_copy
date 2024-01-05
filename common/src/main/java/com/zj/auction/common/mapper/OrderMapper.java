package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    int updateByPrimaryKeySelective(Order record);

    Order selectOwnerOrderBySnAndStatus(@Param("sn")Long sn, @Param("orderStat") int code);
}