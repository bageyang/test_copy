package com.zj.auction.general.pc.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.User;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.common.vo.PageAction;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface RoleService {
    /**
     *
     * @Title: listSysRoleTbl
     * @Description: 根据用户查询用户角色
     * @author：Mao Qi
     * @date： 2019年6月20日上午10:36:27
     * @param userCfg
     * @return
     * @return：List<Map<String,Object>>
     *
     */
    List<Map<String, Object>> listSysRoleTbl(User userCfg);


    /**
     * @Title: findRoleAll
     * @Description: 管理员设置角色查询所有角色
     * @author：Mao Qi
     * @date： 2019年7月4日上午10:25:45
     * @return：Map<String, Object>
     */
    GeneralResult findRoleAll();

    /**
     * @Title: findRoleByUserId
     * @Description: 管理员页面根据管理员id查询角色
     * @author：Mao Qi
     * @date： 2019年7月4日上午10:25:45
     * @return：List<Integer>
     */
    GeneralResult getUserRoles(Long userId);



    /**
     * @title: findRoleByManagerId
     * @description: 根据管理员id查询所有角色
     * @author: Mao Qi
     * @date: 2019年8月5日下午5:11:50
     * @param userId 管理员ID
     * @return: List<Map<String,Object>>
     */
    List<Role> findRoleByManagerId(Long userId);

    /**
     * @Title: saveManagerRole
     * @Description: 保存管理员的角色
     * @author：Mao Qi
     * @date： 2019年7月4日上午10:54:27
     * @param userId
     * @param roleStr
     * @return：Boolean
     */
    Boolean updateUserRoles(Long userId,String roleStr);

    /**

     * 查询角色list
     * @param query 查询
     * @return {@link GeneralResult}
     * @author MengDaNai
     * <p>
     * ************************************************
     */
    PageVo<Role> listRole(PageQuery query);

    /**
     *************************************************
     * 修改角色
     * @author  MengDaNai
     * @param sysRoleTbl
     * @return
     *************************************************
     */
    List<Role> getRole(Role sysRoleTbl);

    /**
     * @Description 修改角色
     * @Title updateRole
     * @param sysRoleTbl
     * @return	java.lang.Boolean
     */
    Boolean updateRole(Role sysRoleTbl);

    /**
     * @Description 添加角色信息
     * @Title addRole
     * @param sysRoleTbl
     * @return	java.lang.Boolean
     */
    Boolean addRole(Role sysRoleTbl);

    /**
     * @Description 删除角色
     * @Title deleteRole
     * @param roleId
     * @return	java.lang.Boolean
     */
    Boolean deleteRole(Long roleId);

    /**
     *
     * @Title: saveRolePermission
     * @Description: 保存角色权限
     * @param sysRoleTbl
     * @param menuStr
     * @return
     * @return：Boolean
     *
     */
    Boolean saveRolePermission(Role sysRoleTbl, String menuStr);

}
