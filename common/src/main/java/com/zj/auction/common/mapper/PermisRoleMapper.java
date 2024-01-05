package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.Permis;
import com.zj.auction.common.model.PermisRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PermisRoleMapper extends BaseMapper<PermisRole> {
    int deleteByPrimaryKey(Long rolePermisId);

    int insert(PermisRole record);

    PermisRole selectByPrimaryKey(Long rolePermisId);

    List<PermisRole> selectAll();

    int updateByPrimaryKey(PermisRole record);

    List<Map<String, Object>> listSysPermisTblByRoleIds(List<Long> roleIds);

    @Select("select permis_id from zj_permis_role where role_id =:roleId")
    List<Integer> findPermisId(Long roleId);
}