package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Withdraw;
import com.zj.auction.common.model.WithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WithdrawMapper {
    long countByExample(WithdrawExample example);

    int deleteByExample(WithdrawExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Withdraw record);

    int insertSelective(Withdraw record);

    List<Withdraw> selectByExample(WithdrawExample example);

    Withdraw selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Withdraw record, @Param("example") WithdrawExample example);

    int updateByExample(@Param("record") Withdraw record, @Param("example") WithdrawExample example);

    int updateByPrimaryKeySelective(Withdraw record);

    int updateByPrimaryKey(Withdraw record);

    List<Withdraw> listWithdrawRecord(@Param("userId") Long userId);
}