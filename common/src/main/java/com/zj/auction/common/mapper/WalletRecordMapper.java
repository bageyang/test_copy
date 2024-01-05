package com.zj.auction.common.mapper;

import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.model.WalletRecordExample;

import java.math.BigDecimal;
import java.util.List;

import com.zj.auction.common.query.WalletQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface WalletRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WalletRecord record);

    int insertSelective(WalletRecord record);

    WalletRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WalletRecord record);

    int updateByPrimaryKey(WalletRecord record);

    List<WalletRecord> listWalletRecord(WalletQuery query);
}