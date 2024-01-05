package com.zj.auction.common.mapper;

import com.zj.auction.common.model.SystemCnf;
import java.util.List;

public interface SystemCnfMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(SystemCnf record);

    SystemCnf selectByPrimaryKey(Long keyId);

    List<SystemCnf> selectAll();

    int updateByPrimaryKey(SystemCnf record);
}