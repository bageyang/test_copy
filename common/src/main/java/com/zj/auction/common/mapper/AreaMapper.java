package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Area;
import java.util.List;

public interface AreaMapper {
    int deleteByPrimaryKey(Long areaId);

    int insert(Area record);

    Area selectByPrimaryKey(Long areaId);

    List<Area> selectAll();

    int updateByPrimaryKey(Area record);
}