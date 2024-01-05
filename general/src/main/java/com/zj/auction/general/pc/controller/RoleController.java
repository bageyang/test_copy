package com.zj.auction.general.pc.controller;

import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.dto.UserRoleDTO;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.User;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.util.PubFun;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.general.pc.service.RoleService;
import com.zj.auction.general.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 胖胖不胖
 */
@RequestMapping("/system/role")
@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    /**
     * @Title: listSysRoleTbl
     * @Description: 根据用户查询用户角色
     * @param userCfg
     * @return
     * @return：List<Map<String,Object>>
     */

    @PostMapping(value="/listSysRoleTbl")
    public GeneralResult listSysRoleTbl(@RequestBody User userCfg) {
        GeneralResult result = new GeneralResult();
        result.setResult(roleService.listSysRoleTbl(userCfg));
        return result;
    }

    /**
     * @Title: findRoleAll
     * @Description: 管理员设置角色查询所有角色
     * @return：GeneralResult
     */
    @PostMapping(value="/findRoleAll")
    public GeneralResult findRoleAll() {
        return roleService.findRoleAll();
    }

    /**
     * @Title: findRoleByUserId
     * @Description: 管理员页面根据管理员id查询角色
     * @return：GeneralResult
     */
    @PostMapping(value="/getUserRoles")
    public GeneralResult<List<Role>> getUserRoles(@RequestBody UserRoleDTO dto) {
        PubFun.check(dto.getUserId());
        return roleService.getUserRoles(dto.getUserId());
    }


    /**
     * @title: findRoleByManagerId
     * @description: 根据管理员ID查询所有角色
     * @return: GeneralResult
     */
    @PostMapping(value="/findRoleByManagerId")
    public GeneralResult findRoleByManagerId() {
        User user = SecurityUtils.getPrincipal();
        GeneralResult result = new GeneralResult();
        result.setResult(roleService.findRoleByManagerId(user.getUserId()));
        return result;
    }

    /**
     * @Title: updateUserRoles
     * @Description: 保存管理员角色
     * @return：GeneralResult
     */
    @PostMapping(value="/updateUserRoles")
    public GeneralResult updateUserRoles(@RequestBody UserRoleDTO dto) {
        return GeneralResult.success(roleService.updateUserRoles(dto.getUserId(),dto.getRoleStr()));
    }

    /**
     * @title: findRoleList
     * @description: 查询角色list
     * @return: GeneralResult
     */
    @PostMapping(value="/getRolePage")
    public Ret getRolePage(@RequestBody PageQuery query) {
        return Ret.ok(roleService.listRole(query));
    }

    /**
     * @title: getRole
     * @description: 修改角色弹框
     * @param sysRoleTbl
     * @return: GeneralResult
     */
    @PostMapping(value="/getRole")
    public GeneralResult getRole(@RequestBody Role sysRoleTbl) {
        GeneralResult result = new GeneralResult();
        result.setResult(roleService.getRole(sysRoleTbl));
        return result;
    }

    /**
     * @title: updateRole
     * @description: 修改角色
     * @param sysRoleTbl
     * @return: GeneralResult
     */
    @RequiresPermissions(value = {"role:update"})
    @PostMapping(value="/updateRole")
    public GeneralResult updateRole(@RequestBody Role sysRoleTbl) {
        return GeneralResult.success(roleService.updateRole(sysRoleTbl));
    }

    /**
     * @title: addRole
     * @description: 添加角色信息
     * @param sysRoleTbl
     * @return: GeneralResult
     */
    @RequiresPermissions(value = {"role:add"})
    @PostMapping(value="/addRole")
    public GeneralResult addRole(@RequestBody Role sysRoleTbl) {
        return GeneralResult.success(roleService.addRole(sysRoleTbl));
    }

    /**
     * @title: deleteRole
     * @description: 删除角色
     * @return: GeneralResult
     */
    @RequiresPermissions(value = {"role:delete"})
    @PostMapping(value="/deleteRole")
    public GeneralResult deleteRole(@RequestBody UserRoleDTO dto) {
        return GeneralResult.success(roleService.deleteRole(dto.getRoleId()));
    }

}
