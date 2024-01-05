package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Role;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    Role selectByPrimaryKey(Long roleId);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
}