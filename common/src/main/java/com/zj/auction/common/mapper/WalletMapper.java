package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Wallet;

import java.math.BigDecimal;
import java.util.List;

import com.zj.auction.common.query.WalletQuery;
import org.apache.ibatis.annotations.Param;

public interface WalletMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Wallet record);

    int insertSelective(Wallet record);


    Wallet selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);

    List<Wallet> selectAllByUserId(@Param("userId") Long userId);

    Wallet selectWalletByUserIdAndType(@Param("userId")Long userId,@Param("fundType") Byte fundType);

    int incrementUserBalance(@Param("id") Long id, @Param("changeNum") BigDecimal changeNum,@Param("changeBefore") BigDecimal before);

    int decrementUserBalance(@Param("id") Long id, @Param("changeNum") BigDecimal changeNum,@Param("changeBefore") BigDecimal before);

    List<Wallet> listWallet(WalletQuery query);
}