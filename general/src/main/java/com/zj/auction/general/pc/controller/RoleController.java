package com.zj.auction.general.pc.controller;

import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.User;
import com.zj.auction.common.util.PubFun;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.common.vo.PageAction;
import com.zj.auction.general.pc.service.RoleService;
import com.zj.auction.general.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/system/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;


    /**
     * @Title: listSysRoleTbl
     * @Description: 根据用户查询用户角色
     * @author：Mao Qi
     * @date： 2019年6月20日上午10:36:27
     * @param userCfg
     * @return
     * @return：List<Map<String,Object>>
     */

    @PostMapping(value="/listSysRoleTbl")
    public GeneralResult listSysRoleTbl(User userCfg) {
        GeneralResult result = new GeneralResult();
        result.setResult(roleService.listSysRoleTbl(userCfg));
        return result;
    }

    /**
     * @Title: findRoleAll
     * @Description: 管理员设置角色查询所有角色
     * @author：Mao Qi
     * @date： 2019年7月4日上午10:25:45
     * @return：GeneralResult
     */
    @PostMapping(value="/findRoleAll")
    public GeneralResult findRoleAll() {
        return roleService.findRoleAll();
    }

    /**
     * @Title: findRoleByUserId
     * @Description: 管理员页面根据管理员id查询角色
     * @author：Mao Qi
     * @date： 2019年7月4日上午10:25:45
     * @return：GeneralResult
     */
    @PostMapping(value="/getUserRoles")
    public GeneralResult<List<Role>> getUserRoles(Long userId) {
        PubFun.check(userId);
        return roleService.getUserRoles(userId);
    }


    /**
     * @title: findRoleByManagerId
     * @description: 根据管理员ID查询所有角色
     * @author: Mao Qi
     * @date: 2019年8月5日下午5:10:46
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
     * @author：Mao Qi
     * @date： 2019年7月4日上午10:53:14
     * @param userId
     * @param roleStr
     * @return：GeneralResult
     */
    @PostMapping(value="/updateUserRoles")
    public GeneralResult updateUserRoles(Long userId,String roleStr) {
        return GeneralResult.success(roleService.updateUserRoles(userId,roleStr));
    }

    /**
     * @title: findRoleList
     * @description: 查询角色list
     * @author: Mao Qi
     * @date: 2019年7月29日下午6:56:42
     * @param pageAction
     * @return: GeneralResult
     */
    @PostMapping(value="/getRolePage")
    public GeneralResult getRolePage(PageAction pageAction) {
        return roleService.listRole(pageAction);
    }

    /**
     * @title: getRole
     * @description: 修改角色弹框
     * @author: Mao Qi
     * @date: 2019年7月29日下午6:54:39
     * @param sysRoleTbl
     * @return: GeneralResult
     */
    @PostMapping(value="/getRole")
    public GeneralResult getRole(Role sysRoleTbl) {
        GeneralResult result = new GeneralResult();
        result.setResult(roleService.getRole(sysRoleTbl));
        return result;
    }

    /**
     * @title: updateRole
     * @description: 修改角色
     * @author: Mao Qi
     * @date: 2019年7月29日下午6:54:16
     * @param sysRoleTbl
     * @return: GeneralResult
     */
    @RequiresPermissions(value = {"role:update"})
    @PostMapping(value="/updateRole")
    public GeneralResult updateRole(Role sysRoleTbl,String permisStr) {
        return GeneralResult.success(roleService.updateRole(sysRoleTbl,permisStr));
    }

    /**
     * @title: addRole
     * @description: 添加角色信息
     * @author: Mao Qi
     * @date: 2019年7月29日下午6:55:31
     * @param sysRoleTbl
     * @return: GeneralResult
     */
    @RequiresPermissions(value = {"role:add"})
    @PostMapping(value="/addRole")
    public GeneralResult addRole(Role sysRoleTbl,String permisStr) {
        return GeneralResult.success(roleService.addRole(sysRoleTbl,permisStr));
    }

    /**
     * @title: deleteRole
     * @description: 删除角色
     * @author: Mao Qi
     * @date: 2019年7月29日下午6:55:38
     * @param roleId
     * @return: GeneralResult
     */
    @RequiresPermissions(value = {"role:delete"})
    @PostMapping(value="/deleteRole")
    public GeneralResult deleteRole(Integer roleId) {
        return GeneralResult.success(roleService.deleteRole(roleId));
    }

}
