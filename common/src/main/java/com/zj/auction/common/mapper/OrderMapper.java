package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;
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

    Order selectOwnerOrderByStockNumberAndStatus(@Param("sn")Long sn, @Param("orderStat") int code);

    Order selectOrderByOrderNumber(@Param("sn")Long sn);

    List<Order> listOrderByUserId(@Param("userId") Long userId);

    List<Order> listOrderByCondition(OrderQuery query);

    int countExclusiveAuctionUserNum(@Param("stockNumber") Long sn,@Param("orderStat") byte code);

    List<Order> listOrderByStockNumberOrderByCreatTime(@Param("stockNumber")Long stockNumber);

    List<Order> listOrderByStockNumberAndStatus(@Param("snList")List<Long> stockNumberList, @Param("orderStat") byte code);

    void updateOrders2Status(@Param("idList")List<Long> orderIds,@Param("orderStat") byte code);

    List<Order> listOrderByStatus(@Param("orderStat") byte code);
}