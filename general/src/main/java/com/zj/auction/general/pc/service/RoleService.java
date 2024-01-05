//package com.zj.auction.general.pc.service;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.zj.auction.common.model.Role;
//import com.zj.auction.common.model.User;
//import com.zj.auction.common.vo.GeneralResult;
//import com.zj.auction.common.vo.PageAction;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//
//public interface RoleService {
//    /**
//     *
//     * @Title: listSysRoleTbl
//     * @Description: 根据用户查询用户角色
//     * @author：Mao Qi
//     * @date： 2019年6月20日上午10:36:27
//     * @param userCfg
//     * @return
//     * @return：List<Map<String,Object>>
//     *
//     */
//    List<Map<String, Object>> listSysRoleTbl(User userCfg);
//
//
//    /**
//     * @Title: findRoleAll
//     * @Description: 管理员设置角色查询所有角色
//     * @author：Mao Qi
//     * @date： 2019年7月4日上午10:25:45
//     * @return：Map<String, Object>
//     */
//    GeneralResult findRoleAll();
//
//    /**
//     * @Title: findRoleByUserId
//     * @Description: 管理员页面根据管理员id查询角色
//     * @author：Mao Qi
//     * @date： 2019年7月4日上午10:25:45
//     * @return：List<Integer>
//     */
//    GeneralResult getUserRoles(Long userId);
//
//    /**
//     * @Description 查询员工角色
//     * @Title findEmployeeRole
//     * @Author Mao Qi
//     * @Date 2020/12/3 18:55
//     * @param
//     * @return	java.util.Map<java.lang.String,java.lang.Object>
//     */
//    Map<String, Object> findEmployeeRole(Integer roleId);
//
//    /**
//     * @title: findRoleByManagerId
//     * @description: 根据管理员id查询所有角色
//     * @author: Mao Qi
//     * @date: 2019年8月5日下午5:11:50
//     * @param userId 管理员ID
//     * @return: List<Map<String,Object>>
//     */
//    List<Map<String, Object>> findRoleByManagerId(Long userId);
//
//    /**
//     * @Title: saveManagerRole
//     * @Description: 保存管理员的角色
//     * @author：Mao Qi
//     * @date： 2019年7月4日上午10:54:27
//     * @param userId
//     * @param roleStr
//     * @return：Boolean
//     */
//    Boolean updateUserRoles(Long userId,String roleStr);
//    /**
//     *************************************************
//     * 	查询角色list
//     * @author  MengDaNai
//     * @param pageAction
//     * @return
//     * @date    2019年4月19日 创建文件
//     *************************************************
//     */
//    GeneralResult listRole(PageAction pageAction);
//
//    /**
//     *************************************************
//     * 修改角色
//     * @author  MengDaNai
//     * @param sysRoleTbl
//     * @return
//     * @date    2019年4月19日 创建文件
//     *************************************************
//     */
//    List<Map<String, Object>> getRole(Role sysRoleTbl);
//
//    /**
//     * @Description 修改角色
//     * @Title updateRole
//     * @Author Mao Qi
//     * @Date 2021/1/18 10:21
//     * @param sysRoleTbl
//     * @param permisStr
//     * @return	java.lang.Boolean
//     */
//    Boolean updateRole(Role sysRoleTbl,String permisStr);
//
//    /**
//     * @Description 添加角色信息
//     * @Title addRole
//     * @Author Mao Qi
//     * @Date 2021/1/15 22:49
//     * @param sysRoleTbl
//     * @return	java.lang.Boolean
//     */
//    Boolean addRole(Role sysRoleTbl,String permisStr);
//
//    /**
//     * @Description 删除角色
//     * @Title deleteRole
//     * @Author Mao Qi
//     * @Date 2021/1/15 22:49
//     * @param roleId
//     * @return	java.lang.Boolean
//     */
//    Boolean deleteRole(Integer roleId);
//
//    /**
//     *
//     * @Title: saveRolePermission
//     * @Description: 保存角色权限
//     * @author：Mao Qi
//     * @date： 2020年4月2日上午11:04:25
//     * @param sysRoleTbl
//     * @param menuStr
//     * @return
//     * @return：Boolean
//     *
//     */
//    Boolean saveRolePermission(Role sysRoleTbl, String menuStr);
//
//}
