package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Wallet;
import com.zj.auction.common.model.example.WalletExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletMapper {
    long countByExample(WalletExample example);

    int deleteByExample(WalletExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    List<Wallet> selectByExample(WalletExample example);

    Wallet selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Wallet record, @Param("example") WalletExample example);

    int updateByExample(@Param("record") Wallet record, @Param("example") WalletExample example);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);
}