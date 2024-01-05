package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * @Title: deleteSysPermisRoleMd
     * @Description: 删除角色对应的权限
     */
    @Select(value="delete from zj_permis_role where role_id=#{roleId} and permis_id in(#{permisIds})")
    @Transactional
    void deleteSysPermisRoleMd(@Param("roleId") Integer roleId, @Param("permisIds") List<Integer> permisIds);

    /**
     *
     * @Title: listSysRoleTbl
     * @Description: 根据用户id查询用户所有角色
     * @return：List<Map<String,Object>>
     *
     */
    @Select(value="select a.*,b.role_name,b.role_code from (select role_id from zj_user_role where user_id=#{userId} ) a left join zj_role b on a.role_id=b.role_id where b.delete_flag=0 ")
    List<Map<String, Object>> listSysRoleTbl(@Param("userId") Long userId);


}