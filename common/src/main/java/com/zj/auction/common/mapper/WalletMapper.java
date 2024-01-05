package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Wallet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WalletMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Wallet record);

    Wallet selectByPrimaryKey(Long id);

    List<Wallet> selectAll();

    int updateByPrimaryKey(Wallet record);
}