package com.zj.auction.common.mapper;

import com.zj.auction.common.model.SystemCnf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemCnfMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(SystemCnf record);

    SystemCnf selectByPrimaryKey(Long keyId);

    List<SystemCnf> selectAll();

    int updateByPrimaryKey(SystemCnf record);

    String selectValueByKeyName(@Param("keyName") String keyName);
}