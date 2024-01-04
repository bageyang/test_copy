package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Wallet;
import java.util.List;

public interface WalletMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Wallet record);

    Wallet selectByPrimaryKey(Long id);

    List<Wallet> selectAll();

    int updateByPrimaryKey(Wallet record);
}