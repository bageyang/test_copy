package com.zj.auction.common.mapper;

import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.model.WalletRecordExample;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface WalletRecordMapper {
    long countByExample(WalletRecordExample example);

    int deleteByExample(WalletRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WalletRecord record);

    int insertSelective(WalletRecord record);

    List<WalletRecord> selectByExample(WalletRecordExample example);

    WalletRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WalletRecord record, @Param("example") WalletRecordExample example);

    int updateByExample(@Param("record") WalletRecord record, @Param("example") WalletRecordExample example);

    int updateByPrimaryKeySelective(WalletRecord record);

    int updateByPrimaryKey(WalletRecord record);

    List<WalletRecord> listUserWalletRecord(@Param("userId") Long userId);
}