package com.zj.auction.general.pc.service;

import com.github.pagehelper.PageInfo;
import com.zj.auction.common.condition.PermissionCondition;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Permis;
import com.zj.auction.common.model.PermisRole;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.vo.PageAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PermissionService {

    /**
     *
     * @Title: listPermission
     * @Description: 根据角色查询权限
     * @return：List<Map<String,Object>>
     *
     */
    List<Map<String, Object>> listPermission(PermisRole sysPermisRoleMd);


    /**
     * 	显示菜单列表
     * @return
     */
    List<Map<String,Object>> menuTree();

    /**
     *
     *************************************************
     * 查询三级以内所有菜单
     * @author  MengDaNai
     * @return
     *************************************************
     */
    List<Map<String,Object>> findMenuAll(Integer levelNum);






    /**
     *************************************************
     * 修改菜单
     * @author  MengDaNai
     * @param sysPermisTbl
     * @return
     * @date    2019年4月19日 创建文件
     *************************************************
     */
    Permis getMenu(Permis sysPermisTbl);

    /**
     * @Description 修改菜单信息
     * @Title updateMenu
     * @param sysPermisTbl
     * @return	java.lang.Boolean
     */
    Boolean updateMenu(Permis sysPermisTbl);

    /**
     * @Description 添加菜单信息
     * @Title addMenu
     * @param sysPermisTbl
     * @return	java.lang.Boolean
     */
    Boolean addMenu(Permis sysPermisTbl);

    /**
     * @Description 删除菜单
     * @Title deleteMenu
     * @param permisId
     * @return	java.lang.Boolean
     */
    Boolean deleteMenu(Long permisId);


    /**
     *
     * @Title: findMenuList
     * @return
     * @return：List<Map<String,Object>>
     *
     */
    Map<String, Object> findMenuList();

    /**
     * @title: listPermissionByRoleId
     * @description: 根据管理员角色查询权限
     * @param roleId
     * @return: Map<String,Object>
     */
    Ret listPermissionByRoleId(Integer roleId);

    /**
     * @Title: listSysMenuCfg
     * @Description: 根据角色查询权限
     * @param sysRoleTbl
     * @return：List<Integer>
     */
    List<Permis> listSysMenuByRoleId(Role sysRoleTbl);



    /**
     * @Description 分页查询所有菜单
     * @Title getAllPermis
     * @Author Mao Qi
     */
    PageInfo<Permis> getPermisPage(PermissionCondition condition);
}
