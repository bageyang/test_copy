package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Permis;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PermisMapper extends BaseMapper<Permis> {
    int deleteByPrimaryKey(Long permisId);

    int insert(Permis record);

    Permis selectByPrimaryKey(Long permisId);

    List<Permis> selectAll();

    int updateByPrimaryKey(Permis record);

    @Select(value = "select a.*,b.permis from (select permis_id from zj_permis_role where role_id =:roleId ) a left join zj_permis b on a.permis_id=b.permis_id where b.delete_flag=0  ")
    List<Map<String, Object>> listSysPermisTbl(Long roleId);

    @Select(value = "select a.*,b.permis from (select permis_id from zj_permis_role where role_id in(:roleIds)) a left join zj_permis b on a.permis_id=b.permis_id where b.delete_flag=0  ")
    List<Map<String, Object>> listSysPermisTblByRoleIds(@Param("roleIds") List<Integer> roleIds);

    @Select(value = "update zj_permis set delete_flag = 1 where permis_id = :permisId and delete_flag = 0 ")
    Integer updateUserPermis(Integer permisId);

    @Select(value = "update zj_permis set delete_flag = 1 where pid = :permisId and delete_flag = 0 ")
    Integer updateUserChildPermis(Long permisId);

    @Select(value = "update zj_permis set delete_flag = 1 where pid in :permisIds and delete_flag = 0  ")
    Integer updateAllUserChildPermis(List<Long> permisIds);

    //显示菜单列表
    @Select(value = "select permis_id as id,(case when pid=0 then '#' else pid end) as parent,permis_name as text from zj_permis where delete_flag = 0 ")
    List<Map<String, Object>> menuTree();

    @Select(value = " select permis_id,level_num,permis_name from zj_permis where level_num<3 and delete_flag = 0 order by level_num asc ")
    List<Map<String, Object>> findMenuAll();

    @Select(value = "select permis_id,permis_name,level_num,pid from zj_permis where delete_flag = 0 ")
    List<Map<String, Object>> findMenuList();

    @Select(value = " select t1.*,t2.permis_id as permisId,t2.permis_name permisName,t2.level_num levelNum,t2.pid from (select permis_id from zj_permis_role where role_id =:roleId ) t1 left join zj_permis t2 on t1.permis_id=t2.permis_id where t2.delete_flag=0")
    List<Map<String, Object>> listPermissionByRoleId(Integer roleId);
}