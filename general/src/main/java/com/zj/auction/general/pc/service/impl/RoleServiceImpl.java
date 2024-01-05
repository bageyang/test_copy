package com.zj.auction.general.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zj.auction.common.base.BaseServiceImpl;
import com.zj.auction.common.constant.SystemConstant;
import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.exception.ServiceException;
import com.zj.auction.common.mapper.PermisRoleMapper;
import com.zj.auction.common.mapper.RoleMapper;
import com.zj.auction.common.mapper.UserRoleMapper;
import com.zj.auction.common.model.*;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.util.PubFun;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.common.vo.PageAction;
import com.zj.auction.general.pc.service.RoleService;
import com.zj.auction.general.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 胖胖不胖
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermisRoleMapper permisRoleMapper;


    @Override
    public List<Map<String, Object>> listSysRoleTbl(User userCfg) {
        if(super.baseCheck(userCfg.getUserId(), Objects:: isNull)) {
            throw new RuntimeException("用户信息有误");
        }
        return roleMapper.listSysRoleTbl(userCfg.getUserId());
    }

    //管理员查询所有角色
    @Override
    public GeneralResult findRoleAll() {
        User user =SecurityUtils.getPrincipal();
        List<Integer> roleByUserId = findUserRoles(user.getUserId());
        List<Role> roleList = new ArrayList<>();
        if (user.getUserId()!=1&&roleByUserId.size()>0){
            for (Integer a :roleByUserId){
                roleList.addAll(roleMapper.selectRoleByRoles(a));
            }
        }
        return GeneralResult.success(roleList);
    }

    @Override
    public PageVo<Role> listRole(PageQuery query){
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        return PageVo.of((Page<Role>)roleMapper.selectByAll());
    }




    @Override
    public GeneralResult getUserRoles(Long userId) {
        return GeneralResult.success(findUserRoles(userId));
    }

    //查询用户对应的角色信息
    public List<Integer> findUserRoles(Long userId) {
        List<UserRole> allByUserId = userRoleMapper.selectAllByUserId(userId);
        return allByUserId.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    //保存管理员的角色
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Boolean updateUserRoles(Long userId,String roleStr) {
        PubFun.check(userId);
        //	查询当前管理员所有角色
        List<Integer> collect = userRoleMapper.roleList(userId);
        List<Integer> collect2 = new ArrayList<>();
        collect2.addAll(collect);
        List<Integer> collect3 = new ArrayList<>();
        collect3.addAll(collect);
        List<Integer> roleIds = new ArrayList<>();
        //	判断是否有勾选角色
        if(roleStr!=null && StringUtils.hasText(roleStr)) {
            roleIds = JSONObject.parseArray(roleStr, Integer.class);
        }
        List<Integer> roleIds2 = new ArrayList<>();
        roleIds2.addAll(roleIds);
        List<Integer> roleIds3 = new ArrayList<>();
        roleIds3.addAll(roleIds);
        //求collect2有的值，而menuIds2中没有的值(要删除)
        collect2.removeAll(roleIds2);
        if(!collect2.isEmpty()) {
            //	删除多余的角色
            userRoleMapper.deleteSysUserRoleMd(userId, collect2);
        }
        //求menuIds3有的值，而collect3中没有的值(要添加的值)
        roleIds3.removeAll(collect3);
        if(!roleIds3.isEmpty()) {
            //	添加缺的角色
            List<UserRole> roleList = new ArrayList<>();
            roleIds3.forEach(f -> {
                UserRole sysUserRoleMd = new UserRole();
                sysUserRoleMd.setUserId(Math.toIntExact(userId));
                sysUserRoleMd.setRoleId(f);
                roleList.add(sysUserRoleMd);
            });
            for (UserRole userRole : roleList){
                userRoleMapper.insert(userRole);
            }
        }
        return true;
    }



    @Override
    public List<Role> getRole(Role sysRoleTbl) {
        SecurityUtils.getPrincipal();
        if (!super.baseCheck(sysRoleTbl, param -> !Objects.isNull(param)||Objects.isNull(param.getRoleId()))) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
        }
        StringBuilder sql = new StringBuilder();
        Map<String, Object> map = new HashMap<>();
        sql.append(" select t1.*,t2.role_name as pRoleName from (select * from sys_role_tbl where role_id=:roleId and delete_flag = 0) t1 left join sys_role_tbl t2 on t1.pid = t2.role_id ");
        map.put("roleId", sysRoleTbl.getRoleId());
       List<Role> roleList= roleMapper.selectAllByPidStr(sysRoleTbl.getRoleId());

        if(roleList.isEmpty()) {
            throw new RuntimeException(SystemConstant.NO_DATA_OBTAINED);
        }
        return roleList;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Boolean updateRole(Role sysRoleTbl) {
        Function<Role, Boolean> deal = parm -> {
            Optional<Role> oldRoleOpt = Optional.ofNullable(roleMapper.selectByPrimaryKey(sysRoleTbl.getRoleId()));
            Role oldRole = oldRoleOpt.orElseThrow(() -> throwException("未查询到角色信息"));
            oldRole.setDescription(parm.getDescription());
            oldRole.setRoleName(parm.getRoleName());
            oldRole.setUpdateTime(LocalDateTime.now());
            roleMapper.insertSelective(oldRole);


            Role save = saveAndFlush(oldRole);
            //保存权限
            saveRolePermission(save, sysRoleTbl.getPermisStr());
            return true;
        };
        return super.base(sysRoleTbl, deal);
    }
    public Role saveAndFlush(Role role){
        if (PubFun.dataCheck(role)){
            if (role.getRoleId()!=null){
                roleMapper.updateByPrimaryKey(role);
            }else {
                roleMapper.insertSelective(role);
            }
        }
        return roleMapper.selectByPrimaryKey(role.getRoleId());
    }

    /**
     * @description: 添加角色
     * @date: 2019年8月5日下午3:23:19
     * @param sysRoleTbl
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Boolean addRole(Role sysRoleTbl) {
        User user =SecurityUtils.getPrincipal();
        PubFun.check(sysRoleTbl.getRoleName());
        //查询用户角色

        List<UserRole> allByUserId = userRoleMapper.selectAllByUserId(user.getUserId());
        if(allByUserId.size()>0){
            sysRoleTbl.setPid(allByUserId.get(0).getRoleId());
        }
        Function<Role, Boolean> deal = param -> {
            Role pRole = null;
            if(sysRoleTbl.getPid()!=null && sysRoleTbl.getPid()!=0){
                pRole = Optional.ofNullable(roleMapper.selectByPrimaryKey((sysRoleTbl.getPid().longValue()))).orElse(null);
            }
            Role role = new Role();
            role.setRoleName(param.getRoleName());//角色名称
            role.setDescription(param.getDescription());//角色描述
            role.setPid(pRole!=null && pRole.getRoleId()!=null?pRole.getRoleId().intValue():0);
            if(pRole!=null && pRole.getPidStr()!=null) {
                role.setPidStr(pRole.getPidStr()+","+pRole.getRoleId()+",");
            }else{
                role.setPidStr(","+pRole.getRoleId()+",");
            }
            role.setLevelNum(pRole==null?1:pRole.getLevelNum()+1);
            role.setDeleteFlag(0);
            role.setAddTime(LocalDateTime.now());
            role.setUpdateTime(LocalDateTime.now());
            role.setAddUserid(user.getUserId());
            Role save = saveAndFlush(role);
            //保存权限
            saveRolePermission(save, sysRoleTbl.getPermisStr());
            return true;
        };
        return super.base(sysRoleTbl, deal);
    }

    /**
     * @description: 删除角色
     * @date: 2019年8月5日下午8:32:09
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public Boolean deleteRole(Long roleId) {
        User user =SecurityUtils.getPrincipal();
        if (super.baseCheck(roleId, param -> Objects.isNull(roleId))) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
        }
        Optional<Role> oldRoleOpt = Optional.ofNullable(roleMapper.selectByPrimaryKey(roleId.longValue()));
        Role oldRole = oldRoleOpt.orElseThrow(() -> throwException("未查询到角色信息"));
        oldRole.setDeleteFlag(1);
        oldRole.setUpdateTime(LocalDateTime.now());
        oldRole.setAddUserid(user.getUserId());
        roleMapper.insertSelective(oldRole);

        //要删除该角色下级的角色对应的权限
        final Long roleid = roleId;
        Thread thread=new Thread(()-> delSubRole(roleid));
        thread.start();
        return true;
    }

    private RuntimeException throwException(String errorMessage){
        return new RuntimeException(errorMessage);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Boolean saveRolePermission(Role sysRoleTbl, String menuStr) {
        User user =SecurityUtils.getPrincipal();
        if(super.baseCheck(sysRoleTbl.getRoleId(), Objects::isNull)) {
            throw new RuntimeException("未获取到角色ID");
        }
        //非开发者
        if(sysRoleTbl.getRoleId()!=1) {
            //	判断该用户与该角色的关系
            Integer rolecount = userRoleMapper.rolecount(user.getUserId(), sysRoleTbl.getRoleId().intValue());
            if(rolecount>0) {
                throw new RuntimeException("只能设置自身下级角色权限");
            }
        }
        //	查询当前角色所有权限
        List<Integer> collect = permisRoleMapper.findPermisId(sysRoleTbl.getRoleId());
        List<Integer> collect2 = new ArrayList<>();
        collect2.addAll(collect);
        List<Integer> collect3 = new ArrayList<>();
        collect3.addAll(collect);
        List<Integer> menuIds = new ArrayList<>();
        //	判断是否有勾选权限
        if(menuStr!=null && StringUtils.hasText(menuStr)) {
            menuIds = JSONObject.parseArray(menuStr, Integer.class);
        }
        List<Integer> menuIds2 = new ArrayList<>();
        menuIds2.addAll(menuIds);
        List<Integer> menuIds3 = new ArrayList<>();
        menuIds3.addAll(menuIds);
        //	求collect2有的值，而menuIds2中没有的值(要删除)
        collect2.removeAll(menuIds2);
        if(!collect2.isEmpty()) {
            //	删除多余的权限
            roleMapper.deleteSysPermisRoleMd(sysRoleTbl.getRoleId().intValue(), collect2);
            //要删除该角色下级的角色对应的权限
            final Long roleId = sysRoleTbl.getRoleId();
            final List<Integer> permisIds = collect2;
            Thread thread=new Thread(()-> delSubRolePermission( roleId, permisIds));
            thread.start();
        }
        //	求menuIds3有的值，而collect3中没有的值(要添加的值)
        menuIds3.removeAll(collect3);
        if(!menuIds3.isEmpty()) {
            //	添加缺的权限
            List<PermisRole> roleList = new ArrayList<>();
            menuIds3.forEach(f -> {
                PermisRole roleacc = new PermisRole();
                roleacc.setRoleId(sysRoleTbl.getRoleId());
                roleacc.setPermisId(f.longValue());
                roleList.add(roleacc);
            });
            for (PermisRole permisRole : roleList){
                permisRoleMapper.insert(permisRole);
            }
        }
        return true;
    }


    @Override
    public List<Role> findRoleByManagerId(Long userId) {
        if(super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("未获取到管理员ID");
        }
        if (userId==1){
           return roleMapper.selectByAll();
        }else {
            return roleMapper.selectRolesByUserId(userId);
        }
    }

    /**
     * @title: delSubRolePermission
     * @description: 删除下级角色的权限
     * @param roleId 角色id
     * @param permisIds 权限list
     * @return: void
     */
    private  void delSubRolePermission(Long roleId, List<Integer> permisIds) {
        //根据角色查询下级角色id
       List<Integer> roles= roleMapper.selectRoleIdList(roleId);
        //删除下级角色所有对饮的权限
        for (Integer id:roles){
            roleMapper.deleteByPermisList(id,permisIds);
        }
    }

    /**
     * @title: delSubRole
     * @description: 删除下级角色
     * @param roleId
     * @return: void
     * @Date:  2022年6月17日01:07:22
     */
    private  void delSubRole(Long roleId) {
        //根据角色查询下级角色id
        List<Integer> roles= roleMapper.selectRoleIdList(roleId);
            //删除下级角色所有对饮的权限
            roleMapper.deleteSubRole(roles);

    }
}
