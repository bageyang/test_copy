package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Permis;
import java.util.List;

public interface PermisMapper {
    int deleteByPrimaryKey(Long permisId);

    int insert(Permis record);

    Permis selectByPrimaryKey(Long permisId);

    List<Permis> selectAll();

    int updateByPrimaryKey(Permis record);
}