package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface StockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long id);

    List<Stock> selectAll();

    int updateByPrimaryKey(Stock record);

    int updateByPrimaryKeySelective(Stock record);

    Stock selectOneBySn(@Param("sn")Long sn);

    List<Stock> listStockBySnList(@Param("snList")List<Long> sn);

    List<Stock> listStockByIds(@Param("ids")List<Long> sn);
}