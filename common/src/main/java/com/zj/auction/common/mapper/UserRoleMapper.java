package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.UserRole;
import java.util.List;
import java.util.Map;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    int deleteByPrimaryKey(Integer userRoleId);

    int insert(UserRole record);

    UserRole selectByPrimaryKey(Integer userRoleId);

    List<UserRole> selectAll();

    int updateByPrimaryKey(UserRole record);

    List<Role> listSysRoleTbl(Integer userId);
}