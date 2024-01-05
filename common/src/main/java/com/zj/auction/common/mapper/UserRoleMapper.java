package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.UserRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    int deleteByPrimaryKey(Integer userRoleId);

    @Override
    int insert(UserRole record);

    UserRole selectByPrimaryKey(Integer userRoleId);

    List<UserRole> selectAll();

    int updateByPrimaryKey(UserRole record);

    List<Role> listSysRoleTbl(Integer userId);

    /**
     * 超级管理员修改用户角色
     * @param userId
     * @param roleId
     */
    @Select("update zj_user_role set role_id=#{userId} where user_id=#{roleId}")
    void updateUserAuthority(String userId, String roleId);

    @Select(value = "select * from zj_user_role where user_id=#{userId}  ")
    List<UserRole> selectAllByUserId(Long userId);

    /**
     * @title: rolecount
     * @description: 查询该用户是否拥有该角色
     * @author: Mao Qi
     * @date: 2019年8月5日下午7:37:49
     * @param userId 用户id
     * @param roleId 角色id
     * @return: Integer
     */
    @Select(value="select count(*) from zj_user_role where user_id=#{userId} and role_id=#{roleId} ")
    Integer rolecount(Long userId, Integer roleId);

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    @Select("SELECT r.role_name FROM `zj_user_role` ur LEFT JOIN zj_role r ON r.role_id = ur.role_id WHERE ur.user_id = #{userId}")
    List<String> selectRolesByUserId(Long userId);

    @Select("select role_id from zj_user_role where user_id=#{userId}")
    List<Integer> roleList (Long userId);

    /**
     * 删除多余的角色
     */
    @Select(value="delete from zj_user_role where user_id=#{userId} and role_id in(#{roleIds}) ")
    @Transactional
    boolean deleteSysUserRoleMd(Long userId, List<Integer> roleIds);
}