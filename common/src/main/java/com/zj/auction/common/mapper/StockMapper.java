package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Stock;
import java.util.List;

public interface StockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    Stock selectByPrimaryKey(Long id);

    List<Stock> selectAll();

    int updateByPrimaryKey(Stock record);
}