package com.zj.auction.general.pc.controller;

import com.github.pagehelper.PageInfo;
import com.zj.auction.common.condition.PermissionCondition;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Permis;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.User;
import com.zj.auction.common.util.PubFun;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.general.pc.service.PermissionService;
import com.zj.auction.general.pc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/system/permission")
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    /**
     * @Description 显示菜单jstree
     * @Title menuTree
     */
    @PostMapping(value="/menuTree")
    public Ret menuTree() {
        Ret result = new Ret();
        result.setData(permissionService.menuTree());
        return result;
    }

    /**
     * @Description 查询三级以内所有菜单
     * @Title findMenuAll
     */

    @PostMapping(value="/findMenuAll")
    public Ret findMenuAll(@RequestParam(name = "levelNum")Integer levelNum) {
        Ret result = new Ret();
        result.setData(permissionService.findMenuAll(levelNum));
        return result;
    }

    /**
     * @param condition 条件
     * @return {@link PageInfo}<{@link Permis}>
     * @Description 分页查询所有菜单
     * @Title getAllPermis
     */
    @RequiresPermissions(value = {"permission"})
    @PostMapping(value="/getPermisPage")
    public PageInfo<Permis> getPermisPage(PermissionCondition condition) {
        return permissionService.getPermisPage(condition);
    }




    /**
     * @Description 修改菜单弹框
     * @Title getMenu
     */
    @PostMapping(value="/getMenu")
    public Ret getMenu(Permis sysPermisTbl) {
        Ret result = new Ret();
        result.setData(permissionService.getMenu(sysPermisTbl));
        return result;
    }

    /**
     * @Description 修改菜单
     * @Title updateMenu
     */
    @RequiresPermissions(value = {"permis:upd"})
    @PostMapping(value="/updateMenu")
    public GeneralResult updateMenu(Permis sysPermisTbl) {
        return GeneralResult.success(permissionService.updateMenu(sysPermisTbl));
    }

    /**
     * @Description 添加菜单信息
     * @Title addMenu
     */
    @RequiresPermissions(value = {"permis:add"})
    @PostMapping(value="/addMenu")
    public GeneralResult addMenu(Permis sysPermisTbl) {
        return GeneralResult.success(permissionService.addMenu(sysPermisTbl));
    }

    /**
     * @Description 删除菜单
     * @Title deleteMenu
     */
    @RequiresPermissions(value = {"permis:del"})
    @PostMapping(value="/deleteMenu")
    public Boolean deleteMenu(Long permisId) {
        PubFun.check(permisId);
        return permissionService.deleteMenu(permisId);
    }

    /**
     * @Description 查询所有权限
     * @Title findAllMenuList
     */

    @PostMapping(value="/findAllMenuList")
    public Ret findAllMenuList() {
        Ret result = new Ret();
        result.setData(permissionService.findMenuList());
        return result;
    }

    /**
     * @title: findAllMenuList
     * @description: 根据用户角色查询权限
     */
    @PostMapping(value="/listPermissionByRoleId")
    public Ret listPermissionByRoleId(@RequestParam(value = "roleId") Integer roleId) {
        return permissionService.listPermissionByRoleId(roleId);
    }

    /**
     * @Title: listSysMenuByRoleId
     * @Description: 根据角色id查询权限
     * @param sysRoleTbl
     * @return：GeneralResult
     */
    @PostMapping(value="/listSysMenuByRoleId")
    public Ret listSysMenuByRoleId(Role sysRoleTbl) {
        Ret result = new Ret();
        result.setData(permissionService.listSysMenuByRoleId(sysRoleTbl));
        return result;
    }

    /**
     * @Description 查询用户所有菜单权限
     * @Title getUserAuthority
     */
    @PostMapping(value="/getUserAuthority")
    public Ret getUserAuthority() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        //System.out.println("getUserAuthority----------->"+user);
        List<Permis> listResult = userService.findByMenuId(user.getUserId());
        return Ret.ok(listResult);
    }

    /**
     * @Description 超级管理员修改用户角色
     * @Title getUserAuthority
     */
    @PostMapping(value="/updateUserAuthority")
    public Ret updateUserAuthority(@RequestParam(value = "userId") String userId,@RequestParam(value = "roleId") String roleId) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (user.getRoleId()!=2) {
            return Ret.ok("没有权限！");
        }
        userService.updateUserAuthority(userId,roleId);
        return Ret.ok();
    }

}
