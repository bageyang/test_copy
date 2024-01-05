package com.zj.auction.general.pc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zj.auction.common.base.BaseServiceImpl;
import com.zj.auction.common.condition.PermissionCondition;
import com.zj.auction.common.constant.SystemConstant;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.exception.ServiceException;
import com.zj.auction.common.mapper.PermisMapper;
import com.zj.auction.common.mapper.RoleMapper;
import com.zj.auction.common.model.Permis;
import com.zj.auction.common.model.PermisRole;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.util.PubFun;
import com.zj.auction.general.pc.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService {
    @Autowired
    private PermisMapper permisMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Map<String, Object>> listPermission(PermisRole sysPermisRoleMd) {
        if(super.baseCheck(sysPermisRoleMd.getRoleId(), Objects:: isNull)) {
            throw new RuntimeException("角色信息有误");
        }
        return permisMapper.listSysPermisTbl(sysPermisRoleMd.getRoleId());
    }

    //	显示菜单列表
    @Override
    public List<Map<String,Object>> menuTree() {
        return permisMapper.menuTree();
    }

    @Override
    public List<Map<String, Object>> findMenuAll(Integer levelNum) {
        return permisMapper.findMenuAll(levelNum);
    }


    @Override
    public Permis getMenu(Permis sysPermisTbl) {
       // SecurityUtils.getPrincipal();
        if (!super.baseCheck(sysPermisTbl, param -> !Objects.isNull(param)||Objects.isNull(param.getPermisId()))) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
        }
        Function<Permis,Permis> deal = param -> {
            Optional<Permis> findById = Optional.ofNullable(permisMapper.selectByPrimaryKey(param.getPermisId()));
            return findById.orElseThrow(() ->  new RuntimeException(SystemConstant.DATA_ILLEGALITY));
        };
        return super.base(sysPermisTbl, deal);
    }

    //修改菜单
    @Transactional
    @Override
    public Boolean updateMenu(Permis sysPermisTbl) {
        Function<Permis, Boolean> deal = parm -> {
            Optional<Permis> oldSysmenuOpt = Optional.ofNullable(permisMapper.selectByPrimaryKey(sysPermisTbl.getPermisId()));
            Permis oldSysmenu = oldSysmenuOpt.orElseThrow(() -> new ServiceException(518,"未查询到菜单信息"));
            oldSysmenu.setPermisName(parm.getPermisName());
            oldSysmenu.setLevelNum(parm.getLevelNum());
            oldSysmenu.setImgPath(parm.getImgPath());
            //oldSysmenu.setResUrl(parm.getResUrl());
            if(parm.getLevelNum()!=1) {//	不为一级菜单
                oldSysmenu.setPid(sysPermisTbl.getPid());
            }else {
                oldSysmenu.setPid((long) 0);
            }
            oldSysmenu.setPermis(parm.getPermis());
            oldSysmenu.setOrderIndex(parm.getOrderIndex());
            permisMapper.updateByPrimaryKey(oldSysmenu);
            return true;
        };
        return super.base(sysPermisTbl, deal);
    }

    //添加菜单
    @Transactional
    @Override
    public Boolean addMenu(Permis sysPermisTbl) {
        Function<Permis, Boolean> deal = parm -> {
            parm.setPid(sysPermisTbl.getPid());
            parm.setStatus(0);
            parm.setDeleteFlag(0);
            permisMapper.insert(parm);
            return true;
        };
        return super.base(sysPermisTbl, deal);
    }

    //删除菜单
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteMenu(Long permisId) {
        Function<Long, Boolean> deal = parm -> {
            Permis sysPermisTbl = Optional.ofNullable(permisMapper.selectByPrimaryKey(parm)).orElseThrow(() -> new ServiceException(519,"该菜单已经删除了!"));
            if(sysPermisTbl.getLevelNum()==1){
                LambdaQueryWrapper<Permis> wrapper = new LambdaQueryWrapper<>(Permis.class);
                wrapper.eq(Permis::getDeleteFlag,0).eq(Permis::getStatus,0).eq(Permis::getPid,parm);
                List<Permis> secList = permisMapper.selectList(wrapper);
                List<Long> collect = secList.stream().map(Permis::getPermisId).collect(Collectors.toList());
                permisMapper.updateAllUserChildPermis(collect);
            }else{
                permisMapper.updateUserChildPermis(parm);
            }
            sysPermisTbl.setDeleteFlag(1);
            permisMapper.updateByPrimaryKey(sysPermisTbl);
            return true;
        };
        return super.base(permisId, deal);
    }


    /**
     *
     * @Title: findMenuList
     * @Description: 角色管理查询所有权限
     * @return
     *
     */
    @Override
    public Map<String, Object> findMenuList() {
        Map<String, Object> map = new HashMap<>(1);
        StringBuilder resultStr = super.base(StringBuilder::new);
        StringBuilder sql = super.base(StringBuilder::new);
        List<Map<String,Object>> list = permisMapper.findMenuList();
        if(!list.isEmpty()) {
            //一级
            list.stream().filter(f -> PubFun.ObjectStrongToInt(f.get("level_num"))==1 ).forEach(f -> {
                resultStr.append("<div class='col-sm-12'>");
                resultStr.append("<div class='ibox float-e-margins lf-main'>");
                resultStr.append("<div class='ibox-title clearfix'>");
                resultStr.append("<div value='"+f.get("permis_id")+"' class='ibox-tools pull-left'>");
                resultStr.append("<a class='collapse-link'><i class='fa fa-chevron-up'></i></a>");
                resultStr.append("</div>");
                resultStr.append("<div class='checkbox m-l m-r-xs col-sm-9 show-set' style='margin-left: 15px;'>");
                resultStr.append("<div class='clearfix total-box'>");
                resultStr.append("<div id='"+f.get("permis_id")+"' data-level = '"+f.get("level_num")+"' class='check-box'>");
                resultStr.append("<i class='fa fa-check'></i> ");
                resultStr.append("<span>"+f.get("permis_name")+"</span>");
                resultStr.append("</div></div></div></div></div>");
                resultStr.append("<div id='show"+f.get("permis_id")+"' class='padd-box'>");
                //二级
                list.stream().filter(e -> f.get("permis_id").equals(e.get("pid")) && PubFun.ObjectStrongToInt(e.get("level_num"))==2).forEach(e -> {
                    resultStr.append("<div class='ibox-content clearfix show-hide'>");
                    resultStr.append("<div class='checkbox lf-check-left'>");
                    resultStr.append("<div id='"+e.get("permis_id")+"' data-level = '"+e.get("level_num")+"' data-pidone = '"+e.get("pid")+"' class='check-box no-check'>");
                    resultStr.append("<span class='back-color'>");
                    resultStr.append("<i class='fa fa-check'></i>");
                    resultStr.append("<span>"+e.get("permis_name")+"</span>");
                    resultStr.append("</span></div></div>");
                    resultStr.append("<div id='childBox' class='lf-check-right'>");
                    //三级
                    list.stream().filter(b -> e.get("permis_id").equals(b.get("pid")) && PubFun.ObjectStrongToInt(b.get("level_num"))==3).forEach(b ->{
                        resultStr.append("<div id='"+b.get("permis_id")+"' data-level = '"+b.get("level_num")+"' data-pidone = '"+f.get("permis_id")+"' data-pidtwo = '"+b.get("pid")+"' class='check-box '><i class='fa fa-check'></i> <span>"+b.get("permis_name")+"</span></div>");
                    });
                    resultStr.append("</div></div>");
                });
                resultStr.append("</div>");
                resultStr.append("</div></div></div>");
            });
        }

        map.put("resultStr", resultStr);
        return map;
    }

    /**
     * @description: 根据角色id查询权限
     * @param roleId 角色id
     */
    @Override
    public Ret listPermissionByRoleId(Integer roleId) {
        if(super.baseCheck(roleId, Objects::isNull)) {
            throw new RuntimeException("角色ID不能为空");
        }
        List<Map<String,Object>> list = permisMapper.listPermissionByRoleId(roleId);
        return Ret.ok(list);
    }

    /**
     *
     * @Title: listSysMenuByRoleId
     * @Description: 根据角色id查询权限
     * @param sysRoleTbl
     * @return
     *
     */
    @Override
    public List<Permis> listSysMenuByRoleId(Role sysRoleTbl) {
        List<Permis> list = new ArrayList<>();
        //获取用户角色
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>(Role.class);
        wrapper.eq(Role::getRoleId,sysRoleTbl.getRoleId()).eq(Role::getDeleteFlag,0);
        List<Role> sysRoleTblList = roleMapper.selectList(wrapper);
        if(!sysRoleTblList.isEmpty()) {
            //获取权限
            list =  sysRoleTblList.stream().map(Role::getPermisList).flatMap(fm -> fm.stream().filter( e -> e.getDeleteFlag()==0)).collect(Collectors.toList());
        }
        return list;
    }




    //分页查询所有菜单
    @Override
    public PageInfo<Permis> getPermisPage(PermissionCondition condition) {
        PageHelper.startPage(condition.getPage(),condition.getPageSize());
        LambdaQueryWrapper<Permis> wrapper = new LambdaQueryWrapper<>(Permis.class);
        wrapper.eq(Permis::getDeleteFlag,0).eq(Permis::getLevelNum,condition.getLevelNum());
        if (ObjectUtils.isNotEmpty(condition.getPermissionName())){
            wrapper.like(Permis::getPermisName,condition.getPermissionName());
        }
        if (ObjectUtils.isNotEmpty(condition.getPermis())){
            wrapper.like(Permis::getPermis,condition.getPermis());
        }
        List<Permis> permisList = permisMapper.selectList(wrapper);
        PageInfo<Permis> pageInfo = new PageInfo<>(permisList);
        return pageInfo;
    }
}
