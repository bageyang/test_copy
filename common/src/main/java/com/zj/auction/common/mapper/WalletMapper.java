package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.enums.FundTypeEnum;
import com.zj.auction.common.model.Wallet;
import com.zj.auction.common.model.example.WalletExample;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletMapper extends BaseMapper<Wallet> {
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

    Wallet selectAllByUserId(@Param("userId") Long userId);

    Wallet selectWalletByUserIdAndType(@Param("userId")Long userId,@Param("fundType") Byte fundType);

    int incrementUserBalance(@Param("id") Long id, @Param("changeNum") BigDecimal changeNum,@Param("changeBefore") BigDecimal before);

    int decrementUserBalance(@Param("id") Long id, @Param("changeNum") BigDecimal changeNum,@Param("changeBefore") BigDecimal before);
}