package com.zj.auction.general.shiro;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.auction.common.mapper.PermisRoleMapper;
import com.zj.auction.common.mapper.UserRoleMapper;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.User;
import com.zj.auction.common.util.StringUtils;
import com.zj.auction.general.auth.AppTokenUtils;
import com.zj.auction.general.auth.AuthToken;
import com.zj.auction.general.auth.PcTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Title ShiroRealm
 * @Package com.duoqio.boot.framework.shiro
 * @Description shiro权限
 * @Author Mao Qi
 * @Date 2020/5/12 12:01
 * @Copyright 重庆多企源科技有限公司
 * @Website {http://www.duoqio.com/index.asp?source=code}
 */
@Log4j2
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ShiroRealm extends AuthorizingRealm {

    //用于用户查询
//    private UserService userService;
//    private UserRoleService userRoleService;
//    private RoleService roleService;
    private UserRoleMapper userRoleMapper;
    private PermisRoleMapper permisRoleMapper;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       // log.info("身份认证");
        AuthToken authToken = (AuthToken) authenticationToken;
        String token = (String) authToken.getPrincipal();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String userId = AppTokenUtils.getUserIdFromToken(token);
        if(StringUtils.isEmpty(userId)){
            throw new ExpiredCredentialsException();//过期
        }
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
//        String token =  (String) authenticationToken.getPrincipal();
//        String userId = PcTokenUtils.getUserIdFromToken(token);
        if(StringUtils.isEmpty(userId)){
            throw new ExpiredCredentialsException();//过期
        }
        /*UserInfoTbl user = userService.findByDeleteFlagFalseAndUserName(token.getUsername());*/
        //UserInfoTbl user = pcUserRepository.findByUserNameAndRoleTypeAndDeleteFlagFalse(token.getUsername(), 1);
//        User user = pcUserRepository.findByDeleteFlagFalseAndUserId(Long.parseLong(userId));
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getUserId,userId).eq(User::getDeleteFlag,0);
        User user = null;
        if (user == null) {
            //	账号错误
            throw new UnknownAccountException();
        }
        if (Objects.equals(user.getStatus(), 1)) {
            //	账户已锁定
            throw new LockedAccountException();
        }
        //员工登录控制
        /*if (user.getRoleRange().compareTo(RoleRangeEnum.EMPLOYEE)==0) {//员工登录控制
            UserInfoTbl pUser = pcUserRepository.findByDeleteFlagFalseAndStatusAndUserIdAndRoleRange(0, user.getAgentUserId(), RoleRangeEnum.COUNTY);
            if(Objects.isNull(pUser)) throw new LockedAccountException();
        }*/
        //return new SimpleAuthenticationInfo(user, user.getPcPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return new SimpleAuthenticationInfo(user, token, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("授权");
        //获取登录用户名
        User usercfg = (User) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = null;
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //查询当前用户拥有的角色
        List<Role> sysRoleTblList = userRoleMapper.listSysRoleTbl(user.getUserId().intValue());
        if (!sysRoleTblList.isEmpty()) {
            List<Long> collect = sysRoleTblList.stream().map(m -> {
                //添加角色
                simpleAuthorizationInfo.addRole(StringUtils.emptyIfNull(m.getRoleCode()));
                //返回角色id
                return  m.getRoleId();
            }).collect(Collectors.toList());
            //查询该角色权限
            if (!collect.isEmpty()) {
                List<Map<String, Object>> permissionList = permisRoleMapper.listSysPermisTblByRoleIds(collect);
                if (!permissionList.isEmpty()) {
                    //添加权限
                    permissionList.stream().distinct().map(m -> StringUtils.emptyIfNull(m.get("permis"))).forEach(simpleAuthorizationInfo::addStringPermission);
                }
            }
        }
        return simpleAuthorizationInfo;
    }

}
