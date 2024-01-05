//package com.zj.auction.common.shiro;
//
//import com.duoqio.boot.business.pc.repository.PcSysPermisTblRepository;
//import com.duoqio.boot.business.pc.repository.PcUserRepository;
//import com.duoqio.boot.business.pc.service.sysrolecfg.PcSysRoleTblService;
//import com.duoqio.boot.business.pc.service.user.PcUserService;
//import com.duoqio.common.util.PcTokenUtils;
//import com.duoqio.common.util.StringUtils;
//import com.duoqio.entity.custom.AuthToken;
//import com.duoqio.entity.pojo.UserInfoTbl;
//import lombok.extern.log4j.Log4j2;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @Title ShiroRealm
// * @Package com.duoqio.boot.framework.shiro
// * @Description shiro权限
// * @Author Mao Qi
// * @Date 2020/5/12 12:01
// * @Copyright 重庆多企源科技有限公司
// * @Website {http://www.duoqio.com/index.asp?source=code}
// */
//@Log4j2
//@Service
//public class ShiroRealm extends AuthorizingRealm {
//
//    //用于用户查询
//    @Autowired
//    private PcUserService userService;
//
//    @Autowired
//    private PcUserRepository pcUserRepository;
//
//    @Autowired
//    private PcSysRoleTblService pcSysRoleTblService;
//
//    @Autowired
//    private PcSysPermisTblRepository pcSysPermisTblRepository;
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof AuthToken;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//       // log.info("身份认证");
//        AuthToken authToken = (AuthToken) authenticationToken;
//        String token = (String) authToken.getPrincipal();
//        if (StringUtils.isEmpty(token)) {
//            return null;
//        }
//        String userId = PcTokenUtils.getUserIdFromToken(token);
//        if(StringUtils.isEmpty(userId)){
//            throw new ExpiredCredentialsException();//过期
//        }
//        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
//        /*if (authenticationToken.getPrincipal() == null) {
//            return null;
//        }
//        String token =  (String) authenticationToken.getPrincipal();
//        String userId = PcTokenUtils.getUserIdFromToken(token);
//        if(StringUtils.isEmpty(userId)){
//            throw new ExpiredCredentialsException();//过期
//        }*/
//        /*UserInfoTbl user = userService.findByDeleteFlagFalseAndUserName(token.getUsername());*/
//        //UserInfoTbl user = pcUserRepository.findByUserNameAndRoleTypeAndDeleteFlagFalse(token.getUsername(), 1);
//        UserInfoTbl user = pcUserRepository.findByDeleteFlagFalseAndUserId(Long.parseLong(userId));
//        if (user == null) {
//            //	账号错误
//            throw new UnknownAccountException();
//        }
//        if (Objects.equals(user.getStatus(), 1)) {
//            //	账户已锁定
//            throw new LockedAccountException();
//        }
//        //员工登录控制
//        /*if (user.getRoleRange().compareTo(RoleRangeEnum.EMPLOYEE)==0) {//员工登录控制
//            UserInfoTbl pUser = pcUserRepository.findByDeleteFlagFalseAndStatusAndUserIdAndRoleRange(0, user.getAgentUserId(), RoleRangeEnum.COUNTY);
//            if(Objects.isNull(pUser)) throw new LockedAccountException();
//        }*/
//        //return new SimpleAuthenticationInfo(user, user.getPcPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
//        return new SimpleAuthenticationInfo(user, token, getName());
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        log.info("授权");
//        //获取登录用户名
//        UserInfoTbl usercfg = (UserInfoTbl) principalCollection.getPrimaryPrincipal();
//        //查询用户名称
//        UserInfoTbl user = userService.findByDeleteFlagFalseAndUserName(usercfg.getUserName());
//        //添加角色和权限
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        //查询当前用户拥有的角色
//        List<Map<String, Object>> sysRoleTblList = pcSysRoleTblService.listSysRoleTbl(user);
//        if (!sysRoleTblList.isEmpty()) {
//            List<Integer> collect = sysRoleTblList.stream().map(m -> {
//                //添加角色
//                simpleAuthorizationInfo.addRole(StringUtils.emptyIfNull(m.get("role_code")));
//                //返回角色id
//                return StringUtils.toInteger(m.get("role_id"));
//            }).collect(Collectors.toList());
//            //查询该角色权限
//            if (!collect.isEmpty()) {
//                List<Map<String, Object>> permissionList = pcSysPermisTblRepository.listSysPermisTblByRoleIds(collect);
//                if (!permissionList.isEmpty()) {
//                    //添加权限
//                    permissionList.stream().distinct().map(m -> StringUtils.emptyIfNull(m.get("permis"))).forEach(simpleAuthorizationInfo::addStringPermission);
//                }
//            }
//        }
//        return simpleAuthorizationInfo;
//    }
//
//}
