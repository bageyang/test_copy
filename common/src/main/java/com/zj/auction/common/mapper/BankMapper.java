package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Bank;
import java.util.List;

public interface BankMapper {
    int deleteByPrimaryKey(Long bankId);

    int insert(Bank record);

    Bank selectByPrimaryKey(Long bankId);

    List<Bank> selectAll();

    int updateByPrimaryKey(Bank record);
}