package com.zj.auction.common.mapper;

import com.zj.auction.common.model.WalletRecord;
import java.util.List;

public interface WalletRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WalletRecord record);

    WalletRecord selectByPrimaryKey(Long id);

    List<WalletRecord> selectAll();

    int updateByPrimaryKey(WalletRecord record);
}