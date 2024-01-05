package com.zj.auction.common.mapper;

import com.zj.auction.common.model.UserBill;
import java.util.List;

public interface UserBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBill record);

    UserBill selectByPrimaryKey(Long id);

    List<UserBill> selectAll();

    int updateByPrimaryKey(UserBill record);
}