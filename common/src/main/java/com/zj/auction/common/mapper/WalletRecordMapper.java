package com.zj.auction.common.mapper;

import com.zj.auction.common.model.WalletRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WalletRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WalletRecord record);

    WalletRecord selectByPrimaryKey(Long id);

    List<WalletRecord> selectAll();

    int updateByPrimaryKey(WalletRecord record);
}