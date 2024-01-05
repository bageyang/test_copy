package com.zj.auction.common.mapper;

import com.zj.auction.common.model.ExpressOrder;
import java.util.List;

public interface ExpressOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressOrder record);

    int insertSelective(ExpressOrder record);

    ExpressOrder selectByPrimaryKey(Long id);

    List<ExpressOrder> selectAll();

    int updateByPrimaryKeySelective(ExpressOrder record);
}