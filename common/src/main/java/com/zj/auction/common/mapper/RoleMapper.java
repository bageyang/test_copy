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

    @Select("select role_id from zj_role where pid_str like CONCAT('%',#{roleIdStr},'%') and delete_flag=0")
    List<Integer> selectRoleIdList(Long roleIdStr);

    @Select("update zj_role set delete_flag=1,update_time=now() where role_id in (#{roles}) and delete_flag=0")
    Integer deleteSubRole(List<Integer> roles);
    @Select("delete from zj_permis_role where role_id=#{roleId} and permis_id in(#{permisIds})")
    boolean deleteByPermisList(Integer roleId,List<Integer> permisIds);


    @Select("SELECT role_id AS roleId,role_name AS roleName FROM zj_role WHERE 1 = 1 and pid_str like CONCAT('%',#{roles},'%') AND delete_flag = 0 ORDER BY level_num")
    List<Role> selectRoleByRoles(Integer roles);

    @Select("select role_id roleId ,role_name roleName ,description,`status`  from zj_role where delete_flag = 0")
    List<Role> selectAllByDeleteFlag();

    @Select("select t1.*,t2.role_name as pRoleName from (select * from zj_role where role_id=#{roleId}  and delete_flag = 0) t1 left join zj_role t2 on t1.pid = t2.role_id")
    List<Role> selectAllByPidStr(Long roleId);

    @Select("select * from zj_role where delete_flag= 0")
    List<Role> selectByAll();

    @Select("SELECT t1.*,t2.role_name FROM ( SELECT role_id FROM zj_user_role WHERE user_id =#{userId} ) t1 LEFT JOIN zj_role t2 ON t1.role_id = t2.role_id")
    List<Role> selectRolesByUserId(Long userId);
}