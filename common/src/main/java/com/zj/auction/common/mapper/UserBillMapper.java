package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.UserBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserBillMapper extends BaseMapper<UserBill> {
    int deleteByPrimaryKey(Long id);

    int insert(UserBill record);

    UserBill selectByPrimaryKey(Long id);

    List<UserBill> selectAll();

    int updateByPrimaryKey(UserBill record);

    UserBill selectUserBillByTranstionSn(@Param("sn")String sn);
}