//package com.zj.auction.general.pc.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zj.auction.common.base.BaseServiceImpl;
//import com.zj.auction.common.constant.SystemConstant;
//import com.zj.auction.common.exception.ServiceException;
//import com.zj.auction.common.mapper.RoleMapper;
//import com.zj.auction.common.mapper.UserRoleMapper;
//import com.zj.auction.common.model.Role;
//import com.zj.auction.common.model.User;
//import com.zj.auction.common.model.UserRole;
//import com.zj.auction.common.util.PubFun;
//import com.zj.auction.common.vo.GeneralResult;
//import com.zj.auction.common.vo.PageAction;
//import com.zj.auction.general.pc.service.RoleService;
//import com.zj.auction.general.shiro.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * @author 胖胖不胖
// */
//public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
//    @Autowired
//    private RoleMapper roleMapper;
//    @Autowired
//    private UserRoleMapper userRoleMapper;
//
//    @Override
//    public List<Map<String, Object>> listSysRoleTbl(User userCfg) {
//        if(super.baseCheck(userCfg.getUserId(), Objects:: isNull)) {
//            throw new RuntimeException("用户信息有误");
//        }
//        return roleMapper.listSysRoleTbl(userCfg.getUserId());
//    }
//
//    //管理员查询所有角色
//    @Override
//    public GeneralResult findRoleAll() {
//        User user =SecurityUtils.getPrincipal();
//        List<Integer> roleByUserId = findUserRoles(user.getUserId());
//		/*Specification<SysRoleTbl> params = Specifications.<SysRoleTbl>and()
//				.eq("deleteFlag", 0)
//				.predicate(authToken.getUserId()!=1 && !roleByUserId.isEmpty(), Specifications.or()
//						.like("pidStr", )
//						.like("memo", "%"+pageAction.getKeyword()+"%")
//						.build())
//				.build();*/
//        StringBuffer wheres = new StringBuffer();
//        HashMap<String, Object> sqMap = new HashMap<>();
//        if(user.getUserId()!=1 && roleByUserId.size()>0) {
//            wheres.append(" and  ( ");
//            for (int i = 0; i <roleByUserId.size() ; i++) {
//                wheres.append("pid_str like " ).append("'%"+roleByUserId.get(i)+"%'");
//                if(i !=roleByUserId.size() -1) {
//                    wheres.append(" or ");
//                }
//            }
//            wheres.append(" ) ");
//        }
//        StringBuffer sql = super.base(StringBuffer::new);
//        sql.append(" select role_id as roleId,role_name as roleName from sys_role_tbl where 1=1 "+wheres+" and delete_flag = 0 order by level_num");
//        List<Map<String,Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), sqMap);
//        return GeneralResult.success(list);
//    }
//
//    @Override
//    public Map<String, Object> findEmployeeRole(Integer roleId) {
//        User user =  SecurityUtils.getPrincipal();
//        Map<String, Object> map = new HashMap<>();
//        List<Role> list = pcSysrolecfgRepository.findAllByDeleteFlagAndRoleId(0, roleId);
//        StringBuffer resultStr = new StringBuffer();
//        if(!list.isEmpty()) {
//            list.forEach(f ->resultStr.append("<div id='"+f.getRoleId()+"' class='check-box '><i class='fa fa-check'></i> <span>"+f.getRoleName()+"</span></div>"));
//        }
//        map.put("resultStr", resultStr);
//        return map;
//    }
//
//    @Override
//    public GeneralResult getUserRoles(Long userId) {
//		/*List<SysUserRoleMd> allByUserId = pcSysUserRoleMdRepository.findAllByUserId(userId);
//		List<Integer> collect = allByUserId.stream().map(SysUserRoleMd::getRoleId).collect(Collectors.toList());
//		List<SysRoleTbl> res = new ArrayList<>();
//		if (collect.size()>0){
//			res =pcSysrolecfgRepository.findAllByDeleteFlagAndRoleIdIn(0,collect);
//		}*/
//        return GeneralResult.success(findUserRoles(userId));
//    }
//
//    //查询用户对应的角色信息
//    public List<Integer> findUserRoles(Long userId) {
//        List<UserRole> allByUserId = userRoleMapper.selectAllByUserId(userId);
//        return allByUserId.stream().map(UserRole::getRoleId).collect(Collectors.toList());
//    }
//
//    //保存管理员的角色
//    @Transactional(rollbackFor=Exception.class)
//    @Override
//    public Boolean updateUserRoles(Long userId,String roleStr) {
//        PubFun.check(userId);
//        //	查询当前管理员所有角色
//        StringBuffer sql = new StringBuffer();
//        HashMap<String, Object> map = new HashMap<>(1);
//        sql.append("select role_id from sys_user_role_md where user_id=:userId ");
//        map.put("userId", userId);
//        List<Map<String, Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), map);
//        List<Integer> collect = list.stream().map(m -> PubFun.ObjectStrongToInt(m.get("role_id"))).collect(Collectors.toList());
//        List<Integer> collect2 = new ArrayList<>();
//        collect2.addAll(collect);
//        List<Integer> collect3 = new ArrayList<>();
//        collect3.addAll(collect);
//        List<Integer> roleIds = new ArrayList<>();
//        //	判断是否有勾选角色
//        if(roleStr!=null && StringUtils.hasText(roleStr)) {
//            roleIds = JSONObject.parseArray(roleStr, Integer.class);
//        }
//        List<Integer> roleIds2 = new ArrayList<>();
//        roleIds2.addAll(roleIds);
//        List<Integer> roleIds3 = new ArrayList<>();
//        roleIds3.addAll(roleIds);
//        //求collect2有的值，而menuIds2中没有的值(要删除)
//        collect2.removeAll(roleIds2);
//        if(!collect2.isEmpty()) {
//            //	删除多余的角色
//            pcSysUserRoleMdRepository.deleteSysUserRoleMd(userId, collect2);
//        }
//        //求menuIds3有的值，而collect3中没有的值(要添加的值)
//        roleIds3.removeAll(collect3);
//        if(!roleIds3.isEmpty()) {
//            //	添加缺的角色
//            List<SysUserRoleMd> roleList = new ArrayList<>();
//            roleIds3.forEach(f -> {
//                SysUserRoleMd sysUserRoleMd = new SysUserRoleMd();
//                sysUserRoleMd.setUserId(userId);
//                sysUserRoleMd.setRoleId(f);
//                roleList.add(sysUserRoleMd);
//            });
//            pcSysUserRoleMdRepository.saveAll(roleList);
//        }
//        return true;
//    }
//
//    /**
//     * @description: 查询角色信息
//     * @date: 2019年8月5日下午7:04:34
//     * @param pageAction
//     * @return
//     */
//    @Override
//    public GeneralResult listRole(PageAction pageAction) {
//        User user = SecurityUtils.getPrincipal();
//        Function<PageAction, GeneralResult> deal = parm -> {
//            //根据管理员ID查询角色id
//            List<Integer> roleIds = findUserRoles(user.getUserId());
//            Map<String, Object> map = new HashMap<>();
//            StringBuilder sql = new StringBuilder();
//            sql.append(" select role_id roleId ,role_name roleName ,description,status  from sys_role_tbl where delete_flag = 0 ");
//            if(Objects.nonNull(roleIds) && roleIds.size()>0){
//                sql.append(" and ( role_id in ( :roleIds ) or ");
//                map.put("roleIds", roleIds);
//                roleIds.forEach(e->{
//                    sql.append(" pid_str like :pid"+e);
//                    map.put("pid"+e, "%,"+e+",%");
//                    if (roleIds.indexOf(e) != roleIds.size()-1) sql.append(" or ");
//                });
//                sql.append(" ) ");
//            }
//            if(super.baseCheck(pageAction, param -> StringUtils.hasText(parm.getKeyword()))){
//                sql.append( " and CONCAT(IFNULL(role_name,''),IFNULL(description,''),IFNULL(role_id,'')) like CONCAT('%',:keyword,'%')");
//                map.put("keyword", parm.getKeyword());
//            }
//            Integer total = shCommonDaoImpl.getSQLRecordNumber(sql.toString(), map);
//            List<Map<String, Object>> result = shCommonDaoImpl.getSqlPageListCheakNull(sql.toString(), map, pageAction);
//            pageAction.setTotalCount(total);
//            return GeneralResult.success(result,pageAction);
//        };
//        return super.base(pageAction, deal);
//    }
//
//    @Override
//    public List<Map<String, Object>> getRole(SysRoleTbl sysRoleTbl) {
//        SecurityUtils.getPrincipal();
//        if (!super.baseCheck(sysRoleTbl, param -> !Objects.isNull(param)||Objects.isNull(param.getRoleId()))) {
//            throw new BaseException(SystemConstant.DATA_ILLEGALITY_CODE);
//        }
//        StringBuilder sql = new StringBuilder();
//        Map<String, Object> map = new HashMap<>();
//        sql.append(" select t1.*,t2.role_name as pRoleName from (select * from sys_role_tbl where role_id=:roleId and delete_flag = 0) t1 left join sys_role_tbl t2 on t1.pid = t2.role_id ");
//        map.put("roleId", sysRoleTbl.getRoleId());
//        List<Map<String, Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), map);
//        if(list.isEmpty()) {
//            throw new RuntimeException(SystemConstant.NO_DATA_OBTAINED);
//        }
//        return list;
//    }
//
//    @Transactional(rollbackFor=Exception.class)
//    @Override
//    public Boolean updateRole(Role sysRoleTbl, String permisStr) {
//        Function<Role, Boolean> deal = parm -> {
//            Optional<Role> oldRoleOpt = Optional.ofNullable(roleMapper.selectByPrimaryKey(sysRoleTbl.getRoleId()));
//            Role oldRole = oldRoleOpt.orElseThrow(() -> throwException("未查询到角色信息"));
//            oldRole.setDescription(parm.getDescription());
//            oldRole.setRoleName(parm.getRoleName());
//            oldRole.setUpdateTime(LocalDateTime.now());
//            roleMapper.insertSelective(oldRole);
//
//            Role save = pcSysrolecfgRepository.saveAndFlush(oldRole);
//            //保存权限
//            saveRolePermission(save, permisStr);
//            return true;
//        };
//        return super.base(sysRoleTbl, deal);
//    }
//
//    /**
//     * @description: 添加角色
//     * @date: 2019年8月5日下午3:23:19
//     * @param sysRoleTbl
//     */
//    @Transactional(rollbackFor=Exception.class)
//    @Override
//    public Boolean addRole(SysRoleTbl sysRoleTbl,String permisStr) {
//        UserInfoTbl user =SecurityUtils.getPrincipal();
//        PubFun.check(sysRoleTbl.getRoleName());
//        //查询用户角色
//        List<SysUserRoleMd> allByUserId = pcSysUserRoleMdRepository.findAllByUserId(user.getUserId());
//        if(allByUserId.size()>0){
//            sysRoleTbl.setPid(allByUserId.get(0).getRoleId());
//        }
//        Function<SysRoleTbl, Boolean> deal = param -> {
//            SysRoleTbl pRole = null;
//            if(sysRoleTbl.getPid()!=null && sysRoleTbl.getPid()!=0){
//                pRole = pcSysrolecfgRepository.findById((sysRoleTbl.getPid())).orElse(null);
//            }
//            SysRoleTbl role = new SysRoleTbl();
//            role.setRoleName(param.getRoleName());//角色名称
//            role.setDescription(param.getDescription());//角色描述
//            role.setPid(pRole!=null && pRole.getRoleId()!=null?pRole.getRoleId():0);
//            if(pRole!=null && pRole.getPidStr()!=null) {
//                role.setPidStr(pRole.getPidStr()+","+pRole.getRoleId()+",");
//            }else{
//                role.setPidStr(","+pRole.getRoleId()+",");
//            }
//            role.setLevelNum(pRole==null?1:pRole.getLevelNum()+1);
//            role.setDeleteFlag(0);
//            role.setAddTime(new Date());
//            role.setUpdateTime(new Date());
//            role.setAddUserid(user.getUserId());
//            SysRoleTbl save = pcSysrolecfgRepository.saveAndFlush(role);
//            //保存权限
//            saveRolePermission(save, permisStr);
//            return true;
//        };
//        return super.base(sysRoleTbl, deal);
//    }
//
//    /**
//     * @description: 删除角色
//     * @date: 2019年8月5日下午8:32:09
//     * @param roleId
//     * @return
//     */
//    @Transactional
//    @Override
//    public Boolean deleteRole(Integer roleId) {
//        User user =SecurityUtils.getPrincipal();
//        if (super.baseCheck(roleId, param -> Objects.isNull(roleId))) {
//            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
//        }
//        Optional<Role> oldRoleOpt = Optional.ofNullable(roleMapper.selectByPrimaryKey(roleId.longValue()));
//        Role oldRole = oldRoleOpt.orElseThrow(() -> throwException("未查询到角色信息"));
//        oldRole.setDeleteFlag(1);
//        oldRole.setUpdateTime(LocalDateTime.now());
//        oldRole.setAddUserid(user.getUserId());
//        roleMapper.insertSelective(oldRole);
//
//        //要删除该角色下级的角色对应的权限
//        final Integer roleid = roleId;
//        Thread thread=new Thread(()-> delSubRole(shCommonDaoImpl, roleid));
//        thread.start();
//        return true;
//    }
//
//    private RuntimeException throwException(String errorMessage){
//        return new RuntimeException(errorMessage);
//    }
//
//    @Transactional(rollbackFor=Exception.class)
//    @Override
//    public Boolean saveRolePermission(Role sysRoleTbl, String menuStr) {
//        User user =SecurityUtils.getPrincipal();
//        if(super.baseCheck(sysRoleTbl.getRoleId(), Objects::isNull)) {
//            throw new RuntimeException("未获取到角色ID");
//        }
//        //非开发者
//        if(sysRoleTbl.getRoleId()!=1) {
//            //	判断该用户与该角色的关系
//            Integer rolecount = userRoleMapper.rolecount(user.getUserId(), sysRoleTbl.getRoleId().intValue());
//            if(rolecount>0) {
//                throw new RuntimeException("只能设置自身下级角色权限");
//            }
//        }
//        //	查询当前角色所有权限
//        StringBuilder sql = new StringBuilder();
//        HashMap<String, Object> map = new HashMap<>(1);
//        sql.append("select permis_id from sys_permis_role_md where role_id =:roleId ");
//        map.put("roleId", sysRoleTbl.getRoleId());
//        List<Map<String, Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), map);
//        List<Integer> collect = list.stream().map(m -> PubFun.ObjectStrongToInt(m.get("permis_id"))).collect(Collectors.toList());
//        List<Integer> collect2 = new ArrayList<>();
//        collect2.addAll(collect);
//        List<Integer> collect3 = new ArrayList<>();
//        collect3.addAll(collect);
//        List<Integer> menuIds = new ArrayList<>();
//        //	判断是否有勾选权限
//        if(menuStr!=null && StringUtils.hasText(menuStr)) {
//            menuIds = JSONObject.parseArray(menuStr, Integer.class);
//			/*menuIds = Arrays.asList(menuStr.split(",")).stream().filter(e -> e.trim().length() != 0)
//					.map(m -> PubFun.StringStrongToInt(m.trim()))
//					.collect(Collectors.toList());*/
//        }
//        List<Integer> menuIds2 = new ArrayList<>();
//        menuIds2.addAll(menuIds);
//        List<Integer> menuIds3 = new ArrayList<>();
//        menuIds3.addAll(menuIds);
//        //	求collect2有的值，而menuIds2中没有的值(要删除)
//        collect2.removeAll(menuIds2);
//        if(!collect2.isEmpty()) {
//            //	删除多余的权限
//            pcRoleacccfgRepository.deleteSysPermisRoleMd(sysRoleTbl.getRoleId(), collect2);
//            //要删除该角色下级的角色对应的权限
//            final Integer roleId = sysRoleTbl.getRoleId();
//            final List<Integer> permisIds = collect2;
//            Thread thread=new Thread(()-> delSubRolePermission(shCommonDaoImpl, roleId, permisIds));
//            thread.start();
//        }
//        //	求menuIds3有的值，而collect3中没有的值(要添加的值)
//        menuIds3.removeAll(collect3);
//        if(!menuIds3.isEmpty()) {
//            //	添加缺的权限
//            List<SysPermisRoleMd> roleList = new ArrayList<>();
//            menuIds3.forEach(f -> {
//                SysPermisRoleMd roleacc = new SysPermisRoleMd();
//                roleacc.setRoleId(sysRoleTbl.getRoleId());
//                roleacc.setPermisId(f);
//                roleList.add(roleacc);
//            });
//            pcRoleacccfgRepository.saveAll(roleList);
//        }
//        return true;
//    }
//
//
//    @Override
//    public List<Map<String, Object>> findRoleByManagerId(Long userId) {
//        if(super.baseCheck(userId, Objects::isNull)) {
//            throw new RuntimeException("未获取到管理员ID");
//        }
//        Function<Long, List<Map<String, Object>>> deal = param -> {
//            StringBuffer sql = super.base(StringBuffer::new);
//            if(userId==1){
//                sql.append("select * from sys_role_tbl where delete_flag = 0 ");
//                return shCommonDaoImpl.getSqlList(sql.toString(), null);
//            }else{
//                HashMap<String, Object> map = new HashMap<>(1);
//                sql.append("select t1.*,t2.role_name from (select role_id from sys_user_role_md where user_id=:userId) t1 "
//                        + "left join sys_role_tbl t2 on t1.role_id = t2.role_id  ");
//                map.put("userId", param);
//                return shCommonDaoImpl.getSqlList(sql.toString(), map);
//            }
//        };
//        return super.base(userId, deal);
//    }
//
//    /**
//     * @title: delSubRolePermission
//     * @description: 删除下级角色的权限
//     * @author: Mao Qi
//     * @date: 2019年8月5日下午8:31:12
//     * @param shCommonDaoImpl
//     * @param roleId 角色id
//     * @param permisIds 权限list
//     * @return: void
//     */
//    private static void delSubRolePermission(SHCommonDaoImpl shCommonDaoImpl,Integer roleId, List<Integer> permisIds) {
//        //根据角色查询下级角色id
//        String sql = "select role_id from sys_role_tbl where pid_str like CONCAT('%',:roleIdStr,'%') and delete_flag=0 ";
//        HashMap<String, Object> map = new HashMap<>(1);
//        map.put("roleIdStr", ","+roleId+",");
//        List<Map<String, Object>> list = shCommonDaoImpl.getSqlList(sql, map);
//        list.forEach(f ->{
//            //删除下级角色所有对饮的权限
//            Integer roleid = PubFun.ObjectStrongToInt(f.get("role_id"));
//            String delSql = "delete from sys_permis_role_md where role_id=:roleId and permis_id in(:permisIds)";
//            HashMap<String, Object> delMap = new HashMap<>(2);
//            delMap.put("roleId", roleid);
//            delMap.put("permisIds", permisIds);
//            shCommonDaoImpl.updateJDBC(delSql, delMap);
//        });
//    }
//
//    /**
//     * @title: delSubRole
//     * @description: 删除下级角色
//     * @param roleId
//     * @return: void
//     * @Date:  2022年6月17日01:07:22
//     */
//    private  void delSubRole(Integer roleId) {
//        //根据角色查询下级角色id
//        List<Integer> roles= roleMapper.selectRoleIdList(roleId);
//            //删除下级角色所有对饮的权限
//            roleMapper.deleteSubRole(roles);
//
//    }
//}
