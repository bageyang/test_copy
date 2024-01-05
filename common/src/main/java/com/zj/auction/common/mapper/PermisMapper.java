package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Permis;
import java.util.List;

public interface PermisMapper extends BaseMapper<Permis> {
    int deleteByPrimaryKey(Long permisId);

    int insert(Permis record);

    Permis selectByPrimaryKey(Long permisId);

    List<Permis> selectAll();

    int updateByPrimaryKey(Permis record);
}