package com.zj.auction.general.pc.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zj.auction.common.base.BaseServiceImpl;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.constant.SystemConfig;
import com.zj.auction.common.constant.SystemConstant;
import com.zj.auction.common.date.CalculateTypeEnum;
import com.zj.auction.common.date.DateUtil;
import com.zj.auction.common.date.TimeTypeEnum;
import com.zj.auction.common.dto.UserDTO;
import com.zj.auction.common.exception.ServiceException;
import com.zj.auction.common.mapper.*;
import com.zj.auction.common.model.*;
import com.zj.auction.common.util.*;
import com.zj.auction.general.pc.service.UserService;
import com.zj.auction.general.shiro.SecurityUtils;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.common.vo.LoginResp;
import com.zj.auction.common.vo.PageAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * 用户服务impl
 *
 * @author 变秃变强
 * @date 2022/06/07
 */
@Log4j2
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final UserConfigMapper userConfigMapper;
    private final AreaMapper areaMapper;
    private final WalletMapper walletMapper;
    private final GoodsMapper goodsMapper;


    /**
     * @param userName
     * @param password
     * @return
     * @Title: getPcLogin
     * @Description: 后台登录
     * @author：Mao Qi
     */
    @Override
    public LoginResp getPcLogin(String userName, String password) {
        // 数据校验
        PubFun.check(userName, password);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getUserName, userName).eq(User::getDeleteFlag, 0).eq(User::getUserType, 1);
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new ServiceException(610, "账号或密码错误,请重新输入");
        }
        //校验密码
        String md5 = MD5Utils.isEncryption(password, user.getSalt());
        if (!user.getPassWord().equals(md5)) {
            throw new ServiceException(612, "您输入的密码错误,请重新输入");
        }
        // 保存最近一次登入时间
        user.setLoginTime(LocalDateTime.now());
        userMapper.insert(user);
        return getPcLoginResp(user.getUserId(), user);
    }

    //生成包括token的返回登录数据
    private LoginResp getPcLoginResp(Long userId, User userInfo) {
        //生成请求token
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("userId", userId);
        //生成token
        String subject = json.toJSONString();
        String token = AppTokenUtils.createToken(userId.toString().trim(), subject, 30 * 24 * 60 * 60 * 1000L);
        //设置登录信息
        LoginResp data = new LoginResp();
        data.setUserId(userId);
        data.setToken(token);
        data.setUserInfo(userInfo);
        //敏感信息不返回给前端，加入缓存2天方便解密时使用
        AuthToken authToken = new AuthToken(userId, token);
        authToken.setUser(userInfo);
        RedisUtil.set(String.format(RedisConstant.PC_USER_TOKEN, userId), JSON.toJSONString(authToken), 60 * 60 * 24 * 30L);
        return data;
    }


    /**
     * @param userId
     * @return
     * @Title: findByMenuId
     * @Description:根据权限获取左侧菜单
     * @author：Mao Qi
     * @date： 2019年7月3日下午5:00:19
     */
    @Override
    public List<Permis> findByMenuId(Long userId) {
        if (userId == null) {
            throwException("参数缺失");
        }
        //	业务逻辑
        Supplier<List<Permis>> deal = () -> {
            //	查询当前用户的角色
            StringBuilder sql = new StringBuilder();
            Map<String, Object> map = new HashMap<>();
            LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>(UserRole.class);
            wrapper.select(UserRole::getRoleId).eq(UserRole::getUserId, userId);
            List<Map<String, Object>> list = userRoleMapper.selectMaps(wrapper);
            System.out.println(list);
//			sql.append("select role_id from sys_user_role_md where user_id=:userId ");
//			map.put("userId", userId);
//			List<Map<String,Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), map);
            List<Permis> collect2 = new ArrayList<>();
            if (!list.isEmpty()) {
                List<Integer> collect = list.stream().map(m -> PubFun.ObjectStrongToInt(m.get("role_id"))).collect(Collectors.toList());
                //获取用户角色

//				List<Role> sysRoleTblList = pcSysRoleTblRepository.findAllByDeleteFlagAndRoleIdIn(0, collect);
                LambdaQueryWrapper<Role> wrapper1 = new LambdaQueryWrapper<>(Role.class);
                wrapper1.in(Role::getRoleId, 0 + "," + collect);
                List<Role> sysRoleTblList = roleMapper.selectList(wrapper1);
                System.out.println("==========角色列表=========" + sysRoleTblList);
                if (!sysRoleTblList.isEmpty()) {
                    //获取菜单
                    collect2 = sysRoleTblList.stream().filter(f -> f.getDeleteFlag() == 0).map(Role::getPermisList).flatMap(fm -> fm.stream().filter(e -> e.getDeleteFlag() == 0)).distinct().collect(Collectors.toList());
                }
            }
            return collect2;
        };

        return super.base(deal);
    }


    /*----------------------------平台管理员-----------------------------*/

    //查询平台管理员
    @Override
    public GeneralResult getManagerList(PageAction pageAction) {
        User user = SecurityUtils.getPrincipal();
        PageHelper.startPage(pageAction.getCountPage(), pageAction.getPageSize());
//		Pageable pageable = PageUtil.getPageable(pageAction.getCurrentPage(), pageAction.getPageSize(), Sort.Direction.DESC, "userId");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getDeleteFlag, 0)
                .eq(User::getUserType, 1)
                .ne(User::getUserId, 1)
                .or(!StringUtils.isEmpty(pageAction.getKeyword()))
                .or(StringUtils.isNumeric(pageAction.getKeyword()))
                .like(User::getUserId, com.zj.auction.common.util.StringUtils.toLong(pageAction.getKeyword()))
                .like(User::getUserName, pageAction.getKeyword())
                .like(User::getNickName, pageAction.getKeyword())
                .like(User::getTel, pageAction.getKeyword());
        List<User> userList = userMapper.selectList(wrapper);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageAction.setTotalCount(pageInfo.getSize());
        pageAction.setTotalPage(pageInfo.getPageNum());
        return GeneralResult.success(pageInfo.getList(), pageAction);
    }

    //添加管理员
    @Override
    @Transactional
    public Boolean createManager(User userCfg) {
        PubFun.check(userCfg.getUserName(), userCfg.getTel(), userCfg.getPassWord());
        User user = SecurityUtils.getPrincipal();
        //	查询该用户名是否使用
        Integer count = userMapper.countByName(userCfg.getUserName());
        if (count > 0) {
            throw new ServiceException(404, "该账号已存在!");
        }
//		if(userCfg.getUserName().length()<6) throw new ServiceException(407,"账号设置太短");
        User newUser = new User();
        newUser.setUserName(userCfg.getUserName());
        newUser.setNickName(userCfg.getRealName());
        newUser.setRealName(userCfg.getRealName());
        if (!StringUtils.isEmpty(userCfg.getUserImg())) {
            newUser.setUserImg(userCfg.getUserImg());
        }
        String[] md5pwd = MD5Utils.encryption(userCfg.getPassWord());//md5加密
        newUser.setPassWord(md5pwd[0]);
        newUser.setSalt(md5pwd[1]);
        newUser.setUserType(1);//后台管理员 用于区分APP登录
        newUser.setTel(userCfg.getTel());
        newUser.setStatus(0);
        newUser.setDeleteFlag(0);
        newUser.setAddUserId(user.getUserId());
        newUser.setUpdateTime(LocalDateTime.now());
		/*newUser.setRoleRange(authToken.getRoleRange());
		if(!StringUtils.isEmpty(userCfg.getRoleShopId())) {
			newUser.setRoleShopId(param.getRoleShopId());
		}*/

        return userMapper.insert(newUser) > 0;
    }

    //根据id查询会员信息
    @Override
    public User findManagerByUserId(User userCfg) {
        if (super.baseCheck(userCfg.getUserId(), Objects::isNull)) {
            throw new RuntimeException("未获取到管理员ID");
        }
        Function<User, User> deal = param -> {
            User u = Optional.ofNullable(userMapper.selectById(param.getUserId())).orElseThrow(() -> throwException("未查询到管理员信息"));
            u.setSalt("");
            u.setPassWord("******");
            return u;
        };
        return super.base(userCfg, deal);
    }

    //修改管理员
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateManager(UserDTO userCfg) {
        User user = SecurityUtils.getPrincipal();
        PubFun.check(userCfg, userCfg.getTel(), userCfg.getUserName(), userCfg.getRealName());
        if (super.baseCheck(userCfg.getUserId(), Objects::isNull)) {
            throw new RuntimeException("未获取到管理员ID");
        }
        Function<UserDTO, Boolean> deal = param -> {
            //	查询该用户名是否使用
            Integer count = userMapper.countByName(param.getUserName());
            if (count > 0) {
                throw new ServiceException(407, "该账号已注册");
            }
//			User oldUser = pcUserRepository.findById(param.getUserId()).orElseThrow(()-> throwException("未查询到管理员信息"));
            User oldUser = Optional.ofNullable(userMapper.selectById(param.getUserId())).orElseThrow(() -> throwException("未查询到管理员信息"));
            if (!StringUtils.isEmpty(param.getUserName())) {
                oldUser.setUserName(param.getUserName());
            }
            if (!StringUtils.isEmpty(param.getRealName())) {
                oldUser.setRealName(param.getRealName());
            }
            //if(!StringUtils.isEmpty(param.getRealName())) oldUser.setNickName(param.getRealName());
            if (!StringUtils.isEmpty(param.getUserImg())) {
                oldUser.setUserImg(param.getUserImg());
            }
            if (param.getPassWord() != null) {//x要修改密码
                String[] md5pwd = MD5Utils.encryption(param.getPassWord());//md5加密
                oldUser.setPassWord(md5pwd[0]);
                oldUser.setSalt(md5pwd[1]);
            }
            oldUser.setRoleShopId(userCfg.getRoleShopId() == null ? 0L : userCfg.getRoleShopId());
            if (!StringUtils.isEmpty(param.getTel())) {
                oldUser.setTel(param.getTel());
            }
            UserConfig userConfig = new UserConfig();

            if (!StringUtils.isEmpty(param.getAddress())) {
                userConfig.setAddr(param.getAddress());
            }
            oldUser.setUpdateTime(LocalDateTime.now());
            oldUser.setUpdateUserId(user.getUserId());
            userConfig.setUserId(oldUser.getUserId());
            userConfig.setUpdateTime(new Date());
//			if(param.getAgentUserId()==0) oldUser.setAgentUserId(user.getUserId());
            //if(!StringUtils.isEmpty(param.getRoleRange())) oldUser.setRoleRange(param.getRoleRange());
            //if(!StringUtils.isEmpty(param.getRoleShopId())) oldUser.setRoleShopId(param.getRoleShopId());
            userMapper.insert(oldUser);
            userConfigMapper.updateById(userConfig);
            return true;
        };
        return super.base(userCfg, deal);
    }


    //删除管理员
    @Override
    @Transactional
    public Boolean deleteUser(User userCfg) {
        User user = SecurityUtils.getPrincipal();
        if (super.baseCheck(userCfg.getUserId(), Objects::isNull)) {
            throw new RuntimeException("未获取到管理员ID");
        }
        Function<User, Boolean> deal = param -> {
            Optional<User> userOpt = Optional.ofNullable(userMapper.selectById(userCfg.getUserId()));
            User oldUser = userOpt.orElseThrow(() -> throwException("未查询到管理员信息"));
            oldUser.setDeleteFlag(1);
            oldUser.setUpdateTime(LocalDateTime.now());
            oldUser.setUpdateUserId(user.getUserId());
            userMapper.updateById(oldUser);
            return true;
        };
        return super.base(userCfg, deal);
    }


    //修改管理员状态
    @Override
    @Transactional
    public Boolean updateManagerStatus(User userCfg) {
        User user = SecurityUtils.getPrincipal();
        Function<User, Boolean> deal = param -> {
            Optional<User> userOpt = Optional.ofNullable(userMapper.selectById(param.getUserId()));
            User oldUser = userOpt.orElseThrow(() -> throwException("未查询到管理员信息"));
            if (param.getStatus() == 1) {//禁用
                oldUser.setStatus(1);
                oldUser.setFrozenExplain(param.getFrozenExplain());
//				//冻结用户转拍并未抢拍的产品
//				pcGoodsRepository.updAuctionGoods(-1,param.getUserId(),user.getUserId());
            } else {
                oldUser.setStatus(0);
                oldUser.setFrozenExplain(null);
//				//解冻用户转拍并未抢拍的产品
//				pcGoodsRepository.updAuctionGoods(0,param.getUserId(),user.getUserId());
            }
            oldUser.setUpdateTime(LocalDateTime.now());
            oldUser.setUpdateUserId(user.getUserId());
            userMapper.updateById(oldUser);
            return true;
        };
        return super.base(userCfg, deal);
    }




    /*----------------------------APP会员信息-----------------------------*/


    //查询平台管理员
    public PageInfo<User> getUserPage(PageAction pageAction, List<Long> userIds) {
        LocalDateTime sTime = null;
        LocalDateTime eTime = null;
        PageHelper.startPage(pageAction.getCurrentPage(), pageAction.getPageSize());
        if (!StringUtils.isEmpty(pageAction.getStartTime()) && !StringUtils.isEmpty(pageAction.getEndTime())) {
            sTime = DateTimeUtils.parse(pageAction.getStartTime());
            eTime = DateTimeUtils.parse(pageAction.getEndTime());
        }
        User user = SecurityUtils.getPrincipal();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getDeleteFlag, 0)
                .ge(User::getUserType, 0)
                .in(User::getUserId, userIds)
                .between(Objects.nonNull(sTime) && Objects.nonNull(eTime), User::getAddTime, sTime, eTime)
                .or()
                .eq(StringUtils.isNumeric(pageAction.getKeyword()), User::getUserId, com.zj.auction.common.util.StringUtils.toLong(pageAction.getKeyword()))
                .like(User::getNickName, pageAction.getKeyword())
                .like(User::getRealName, pageAction.getKeyword())
                .like(User::getUserName, pageAction.getKeyword());
        List<User> userList = userMapper.selectList(wrapper);
//		if (userList.size()>0){
//			userList.forEach(e->{
//
//			});
//		}
        return new PageInfo<>(userList);
    }


    //根据用户id查询直推下级
    @Override
    public GeneralResult getSubUserPage(PageAction pageAction, Long pid) {
        PubFun.check(pid);
        LocalDateTime sTime = DateTimeUtils.parse(pageAction.getStartTime());
        LocalDateTime eTime = DateTimeUtils.parse(pageAction.getEndTime());
        User user = SecurityUtils.getPrincipal();
        PageHelper.startPage(pageAction.getCurrentPage(), pageAction.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getDeleteFlag, 0)
                .eq(User::getPid, pid)
                .eq(User::getUserType, 0)
                .between(Objects.nonNull(sTime) && Objects.nonNull(eTime), User::getAddTime, sTime, eTime)
                .or(!StringUtils.isEmpty(pageAction.getKeyword()))
                .eq(StringUtils.isNumeric(pageAction.getKeyword()), User::getUserId, com.zj.auction.common.util.StringUtils.toLong(pageAction.getKeyword()))
                .like(User::getNickName, pageAction.getKeyword())
                .like(User::getUserName, pageAction.getKeyword());
        List<User> userList = userMapper.selectList(wrapper);
        PageInfo<User> page = new PageInfo<>(userList);
        LambdaQueryWrapper<User> wrapper2 = new LambdaQueryWrapper<>(User.class);
        if (page.getList().size() > 0) {
            page.getList().forEach(e -> {
                //查询直推统计
                wrapper2.eq(User::getDeleteFlag, 0).eq(User::getPid, e.getUserId());
                Long count = userMapper.selectCount(wrapper);
                e.setSubUserNum(count.intValue());
            });
        }
        pageAction.setTotalCount((int) page.getTotal());
        return GeneralResult.success(page.getList(), pageAction);
    }


    //修改会员状态
    @Override
    @Transactional
    public Boolean updateUserStatus(Long userId, Integer status, String frozenExplain) {
        User user = SecurityUtils.getPrincipal();
        User oldUser = Optional.ofNullable(userMapper.selectById(userId)).orElseThrow(() -> throwException("未查询到用户信息"));
        oldUser.setStatus(status);
        if (status == 1) {
            oldUser.setFrozenExplain(frozenExplain);
        } else {
            oldUser.setFrozenExplain(null);
        }
        oldUser.setUpdateUserId(user.getUserId());
        userMapper.updateById(oldUser);
        return true;
    }


//	//根据用户id查询间接下级
//	@Override
//	public GeneralResult listMemberIndirect(PageAction pageAction, HashMap<String, Object> maps) {
//		GeneralResult generalResult = new GeneralResult();
//		Function<PageAction, GeneralResult> deal = parm -> {
//
//			HashMap<String, Object> map = new HashMap<>();
//			String wheres = "";
//			LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
//			wrapper.sql
//			if(super.baseCheck(pageAction, param -> StringUtils.isNotBlank(param.getKeyword()))){
//				wheres+=" and CONCAT(IFNULL(user_id,''),IFNULL(user_name,''),IFNULL(real_name,''),IFNULL(nick_name,''),IFNULL(tel,''),IFNULL(addr,'')) like CONCAT('%',:keyword,'%') ";
//				map.put("keyword", parm.getKeyword());
//			}
//			//根据开始时间
//			if(super.baseCheck(maps, paramMaps -> StringUtils.isNotBlank(PubFun.ObjectStrongToString(paramMaps.get("startDate"))))) {
//				wheres+=" and add_time>=:startDate ";
//				map.put("startDate", PubFun.ObjectStrongToString(maps.get("startDate")));
//			}
//			//结束开始时间
//			if(super.baseCheck(maps, paramMaps -> StringUtils.isNotBlank(PubFun.ObjectStrongToString(paramMaps.get("endDate"))))) {
//				wheres+=" and add_time<=:endDate ";
//				map.put("endDate", PubFun.ObjectStrongToString(maps.get("endDate")));
//			}
//			//用户等级
//			if(super.baseCheck(maps, paramMaps -> StringUtils.isNotBlank(PubFun.ObjectStrongToString(paramMaps.get("userType"))))) {
//				wheres+=" and user_type =:userType ";
//				map.put("userType", PubFun.ObjectStrongToString(maps.get("userType")));
//			}
//			totalSql.append("select user_id from user_info_tbl where pid_str like CONCAT('%',:userIds,'%') and pid!=:userId and user_type = 0 "+wheres+" and delete_flag = 0");
//			map.put("userIds", ","+maps.get("userId")+",");
//			map.put("userId", maps.get("userId"));
//			Integer allPage = shCommonDaoImpl.getSQLRecordNumber(totalSql.toString(), map);
//			if(allPage>0) {
//				StringBuffer sql = new StringBuffer();
//				sql.append(" select a.*,b.role_name from (select * from user_info_tbl where pidStr like CONCAT('%',:userIds,'%') and pid!=:userId and user_type = 0 "+wheres+" and delete_flag = 0 order by add_time desc LIMIT :currentSize , :pageSize ) a left join level_role_tbl b on a.user_type = b.role_id ");
//				Integer currentSize = (parm.getCurrentPage()-1)*parm.getPageSize();
//				map.put("currentSize", currentSize);//页数
//				map.put("pageSize", parm.getPageSize());//每页行数
//				List<Map<String, Object>> userList = shCommonDaoImpl.getSqlList(sql.toString(), map);
//				PageAction page = parm;
//				page.setTotalCount(allPage);
//				page.validateSite();
//				PubFun.isNull(userList, SystemConstant.NO_MORE_DATA_CODE);
//				generalResult.setPageAction(page);
//				generalResult.setResult(userList);
//				return generalResult;
//			}
//			throw new BaseException(SystemConstant.NO_MORE_DATA_CODE);
//		};
//		return super.base(pageAction, deal);
//	}

    //根据用户id查询间接下级
    @Override
    public PageInfo<Map<String, Object>> listMemberIndirect(UserDTO dto) {
        List<Map<String, Object>> map = userMapper.listMemberIndirect(dto);
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        return new PageInfo<>(map);
    }

    //修改用户VIP类型
    @Transactional
    @Override
    public GeneralResult updVipType(Long userId, Integer vipType, Long tagId) {
        PubFun.check(userId, vipType);
        User pUser = SecurityUtils.getPrincipal();
        User user = userMapper.selectByPrimaryKey(userId);
        if ((user.getVipType() == 0 || user.getVipType() == 1) && vipType.compareTo(2) == 0) {//设置团长必须设置分馆馆长
            PubFun.check(userId, tagId);
            if (tagId <= 0) throw new ServiceException(410, "请选择分馆");
            if (user.getTagId().compareTo(tagId) == 0) throw new ServiceException(411, "该用户已经属于该馆成员了");
            if (user.getTagId().compareTo(tagId) != 0 && user.getTagId() > 0)
                throw new ServiceException(412, "该用户已经属于其他馆成员了");
            //判断这个馆是否已经有馆长了
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
            wrapper.eq(User::getDeleteFlag, 0).eq(User::getTagId, tagId).eq(User::getVipType, 2);
            Long count = userMapper.selectCount(wrapper);
            if (count > 0) throw new ServiceException(413, "该分馆已经有馆长了");
            user.setTagId(tagId);
            //所有下级团队都可以进入
            userMapper.updUserChildByPidStr(tagId, user.getUserId(), pUser.getUserId());
            //附带的产品同时进入相应的分馆
            goodsMapper.updGoodsTagIdByPidStr(tagId,user.getUserId(),pUser.getUserId());
        } else if (user.getVipType() == 2 && (vipType.compareTo(0) == 0 || vipType.compareTo(1) == 0)) {//取消馆长
            user.setTagId(0L);
            //取消用户分馆归属
            userMapper.updUserChildByPidStr(0L, user.getUserId(), pUser.getUserId());
            //附带的产品同时进入相应的分馆
            goodsMapper.updGoodsTagIdByPidStr(0L,user.getUserId(),pUser.getUserId());
        }
        user.setVipType(vipType);
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateUserId(pUser.getUserId());
        userMapper.updateById(user);
        return GeneralResult.success();
    }


    // 添加用户
    @Override
    public Boolean insertUser(User userCfg) {
        User principal = SecurityUtils.getPrincipal();
        PubFun.check(userCfg, userCfg.getUserName(), userCfg.getNickName(), userCfg.getUserImg(), userCfg.getPassWord(),
                userCfg.getUserType());
        //较验手机号
        //根据用户名称和用户类型以及未删除状态的查询所有
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>(User.class);
        userWrapper.eq(User::getUserName, userCfg.getUserName()).eq(User::getUserType, 0).eq(User::getDeleteFlag, 0);

        List<User> findByTel = userMapper.selectList(userWrapper);
        if (!findByTel.isEmpty()) {
            throw new ServiceException(501, "此手机已经注册了!");
        }
        userCfg.setPid(0L);
        userCfg.setLevelNum(1);
        userCfg.setTel(userCfg.getUserName());
        userCfg.setUserType(0);
        userCfg.setStatus(0);
        userCfg.setShareType(0);
        userCfg.setAudit(2);//审核状态：0默认，1待审核，2审核通过，3拒绝审核
        String[] md5pwd = MD5Utils.encryption(userCfg.getPassWord());//md5加密
        userCfg.setPassWord(md5pwd[0]);
        userCfg.setSalt(md5pwd[1]);
        UserConfig userConfig = new UserConfig();
//1
        LambdaQueryWrapper<Area> wrapper = new LambdaQueryWrapper<>(Area.class);
        wrapper.eq(Area::getAreaId, PubFun.selectToLong(userCfg.getMemo1()));
        wrapper.eq(Area::getDeleteFlag, 0);
        Area provinceArea = areaMapper.selectOne(wrapper);
        userConfig.setProvince(provinceArea.getAreaName());
        //2
        LambdaQueryWrapper<Area> wrapper2 = new LambdaQueryWrapper<>(Area.class);
        wrapper.eq(Area::getAreaId, PubFun.selectToLong(userCfg.getMemo2()));
        wrapper.eq(Area::getDeleteFlag, 0);
        Area cityArea = areaMapper.selectOne(wrapper2);
        userConfig.setCityId(PubFun.selectToLong(userCfg.getMemo2()));
        userConfig.setCity(cityArea.getAreaName());
//3
        LambdaQueryWrapper<Area> wrapper3 = new LambdaQueryWrapper<>(Area.class);
        wrapper.eq(Area::getAreaId, PubFun.selectToLong(userCfg.getMemo3()));
        wrapper.eq(Area::getDeleteFlag, 0);
        Area countyArea = areaMapper.selectOne(wrapper3);
        userConfig.setCountyId(PubFun.selectToLong(userCfg.getMemo3()));
        userConfig.setCounty(countyArea.getAreaName());
        userConfig.setAddr(provinceArea.getAreaName() + "" + cityArea.getAreaName() + "" + countyArea.getAreaName());

        userCfg.setIsFirstLogin(0);
        userCfg.setSubUserNum(0);
        userCfg.setSubUserNumAll(0);
        userConfig.setUserId(userCfg.getUserId());

        userMapper.insert(userCfg);
        userConfigMapper.insert(userConfig);

		/*if(userCfg.getUserType().compareTo(2)==0){//经纪人
			//TODO 生成小程序码  分享经纪人下载的小程序码
			String src = wxAppService.getWxAppCodeUnlimit(userInfoTbl.getUserId().toString(), null, 430, false, null, false,3);
			userInfoTbl.setShareImg(src);//经纪人分享小程序二维码
			pcUserRepository.save(userInfoTbl);
		}*/
        return true;
    }

    // 查看用户信息
    @Override
    public User getUserByUserId(Long userId) {
        SecurityUtils.getPrincipal();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, "数据非法");
        }
        Function<Long, User> deal = userMapper::selectByPrimaryKey;
        return super.base(userId, deal);
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMember(Long userId) {
        User user = SecurityUtils.getPrincipal();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("参数为空");
        }
        User deletedUser = userMapper.selectByPrimaryKey(userId);
        deletedUser.setDeleteFlag(1);
        deletedUser.setUpdateUserId(user.getUserId());
        return userMapper.updateByPrimaryKey(deletedUser) > 0;
    }


    private RuntimeException throwException(String errorMessage) {
        return new RuntimeException(errorMessage);
    }

    /**
     * 列表区域
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> listArea() {
        Map<String, Object> map = new HashMap<>();
        LambdaQueryWrapper<Area> areaLambdaQueryWrapper = new LambdaQueryWrapper<>(Area.class);
        areaLambdaQueryWrapper.eq(Area::getDeleteFlag, 0);
        List<Map<String, Object>> list = areaMapper.selectMaps(areaLambdaQueryWrapper);
        if (!list.isEmpty()) {
            List<Map<String, Object>> collect = list.stream().filter(f -> PubFun.ObjectStrongToInt(f.get("level_num")) == 1).collect(Collectors.toList());
            map.put("collect", collect);
            List<Map<String, Object>> collect2 = list.stream().filter(f -> PubFun.ObjectStrongToInt(f.get("level_num")) == 2).collect(Collectors.toList());
            map.put("collect2", collect2);
            List<Map<String, Object>> collect3 = list.stream().filter(f -> PubFun.ObjectStrongToInt(f.get("level_num")) == 3).collect(Collectors.toList());
            map.put("collect3", collect3);
        }
        return map;
    }

    /**
     * @param userId
     * @param
     * @return
     * @title: updateAuditRejection
     * @description: 会员审核拒绝通过
     * @author: Mao Qi
     * @date: 2020年4月3日下午7:22:33
     */
    @Transactional
    public Integer updateAuditRejection(Long userId, String auditExplain) {
        SecurityUtils.getPrincipal();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, "数据非法");
        }
        return userMapper.updateAuditRejection(userId, auditExplain);
    }

    //    实名通过审核
    @Transactional
    public Integer updateAuditApproval(Long userId) {
        SecurityUtils.getPrincipal();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, "数据非法");
        }

        return userMapper.updateAuditApproval(userId);
    }

    //后台操作变更资金
    @Override
    public Integer changeBalance(Long userId, Integer moneyType, Integer type, BigDecimal integral, String remark) {
        User pcUser = SecurityUtils.getPrincipal();
        PubFun.check(userId, moneyType, type);
        if (com.zj.auction.common.util.StringUtils.isEmpty(integral) || integral.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException(506, "变更数量输入有误!");
        }
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(userId)).orElseThrow(() -> PubFun.throwException("该用户不存在"));
        Wallet wallet = walletMapper.selectAllByUserId(userId);
        if (type == 1) {//增加
            String transactionId = UidGenerator.createOrderXid();
            String memo = "";
            if (moneyType == 0) {
                memo = "后台人工增加店铺收入";
            } else if (moneyType == 1) {
                memo = "后台人工充值获得";
            } else {
                memo = "后台人工充值获得";
            }
            //添加余额
            walletMapper.insert(Wallet.builder()
                    .userId(wallet.getUserId())
                    .updateBalance(integral)
                    .balanceBefore(wallet.getBalance())
                    .balance(integral.add(wallet.getBalance()))
                    .transactionType(1)
                    .fundType(moneyType)
                    .tradeNo(transactionId)
                    .updateTime(LocalDateTime.now())
                    .remark(memo + "-" + remark)
                    .build());
            // addTotalPlatform(1, integral, saveMoney);//给平台加明细
        } else if (type == 2) {//减少
            //查询该用户剩余余额数量
            if (wallet.getBalance().compareTo(BigDecimal.ZERO) < 1 || wallet.getBalance().compareTo(integral) < 0) {
                throw new ServiceException(508, "余额不足");
            }
            String transactionId = UidGenerator.createOrderXid();
            String memo = "";
            if (moneyType == 0) {
                memo = "后台人工减少店铺收入";
            } else if (moneyType == 1) {
                memo = "后台人工回收支出";
            } else {
                memo = "后台人工回收支出";
            }
            //扣除余额
            walletMapper.insert(Wallet.builder()
                    .userId(wallet.getUserId())
                    .updateBalance(integral)
                    .balanceBefore(wallet.getBalance())
                    .balance(integral.add(wallet.getBalance()))
                    .transactionType(0)
                    .fundType(moneyType)
                    .tradeNo(transactionId)
                    .updateTime(LocalDateTime.now())
                    .remark(memo + "-" + remark)
                    .build());
            //addTotalPlatform(-1, integral, saveMoney);//给平台加明细
        } else {
            throw new RuntimeException("变更余额类型有误");
        }
        return null;
    }


    /**
     * 获取用户通过id
     *
     * @param userId 用户id
     * @return {@link User}
     */
    public User getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 获取用户通过电话
     *
     * @param phone 电话
     * @return {@link User}
     */
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getTel, phone).eq(User::getDeleteFlag, 0);
        return userMapper.selectOne(wrapper);
    }

    //根据用户名查找用户


    public List<User> findUserByName(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper(User.class);
        wrapper.eq(User::getDeleteFlag, 0)
                .eq(User::getUserType, 0)
                .eq(User::getUserName, userName);
        return userMapper.selectList(wrapper);
    }

    //导出用户
    @Override
    public void exportUser(PageAction pageAction, Integer userType, String userIds, HttpServletResponse httpServletResponse) {
        List<Long> userList = new ArrayList<>();
        if (!StringUtils.isEmpty(userIds)) {
            userList = JSON.parseArray(userIds, Long.class);
        }
        PageInfo<User> userPage = getUserPage(pageAction, userList);

        List<List<String>> excelData = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("用户ID");
        head.add("账号");
        head.add("昵称");
        head.add("电话");
        head.add("用户类型");
//		head.add("金币余额");
//		head.add("银币余额");
//		head.add("店铺收入");
        head.add("注册时间");
        // 添加头部
        excelData.add(head);
        for (User user : userPage.getList()) {
            List<String> data1 = new ArrayList<>();
            data1.add(user.getUserId().toString());  //ID
            data1.add(user.getUserName());  //账号
            data1.add(Objects.toString(user.getNickName(), ""));  //昵称
            data1.add(Objects.toString(user.getTel(), ""));  //手机号
            data1.add(user.getUserType() == 1 ? "店主" : "用户");   //用户类型
//			data1.add(Objects.toString(user.getGoldBalance(), "0"));   //金币余额
//			data1.add(Objects.toString(user.getSilverBalance(), "0"));   //银币余额
//			data1.add(Objects.toString(user.getBalance(), "0"));   //店铺收入
            data1.add(Objects.toString(DateTimeUtils.toString(user.getAddTime(), "yyyy-MM-dd HH:mm:ss"), ""));
            excelData.add(data1);
        }
        ExcelUtil.exportExcel(httpServletResponse,
                excelData, "会员信息", "member.xls", 20);
    }


    /**
     * @param userId
     * @param upLevel
     * @return java.util.List<com.duoqio.boot.business.entity.UserInfoTbl>
     * @Description 所有上级
     * @Title listParentUser
     * @Author Mao Qi
     * @Date 2019/10/24 21:35
     */
    @Override
    public List<User> listParentUser(Long userId, Integer upLevel) {
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("用户ID为空");
        }
        if (super.baseCheck(upLevel, Objects::isNull)) {
            throw new RuntimeException("等级为空");
        }
        List<User> userList = new ArrayList<>();
        User user = userMapper.selectById(userId);
        String pidStr = user.getPidStr();
        if (pidStr != null && !"".equals(pidStr)) {
            pidStr = pidStr.substring(1, pidStr.length() - 1).replace(",,", ",");
            List<Long> collect = null;
            if (upLevel == -1) {//查询所有上级
//				collect = Arrays.asList(pidStr.split(",")).stream().map(m->Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                collect = Arrays.stream(pidStr.split(",")).map(m -> Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            } else {//查询限制级别的所有上级
//				collect = Arrays.asList(pidStr.split(",")).stream().map(m->Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).limit(upLevel).collect(Collectors.toList());
                collect = Arrays.stream(pidStr.split(",")).map(m -> Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).limit(upLevel).collect(Collectors.toList());
            }
            if (!collect.isEmpty()) {
                LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
                wrapper.in(User::getUserId, collect);
                wrapper.orderByDesc(User::getLevelNum);
//				userList = pcUserRepository.findAllByUserIdInOrderByLevelNumDesc(collect);
                userList = userMapper.selectList(wrapper);
            }
        }
        return userList;
    }


    //注册统计
    @Override
    public GeneralResult statistics() {
        //获取当前用户信息
        User user = SecurityUtils.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        //今年
        Integer year = LocalDateTime.now().getYear();
        //最近一个月
        LocalDateTime now = DateTimeUtils.now();
        LocalDateTime lastMonth = now.minusMonths(1);
        //昨天
        LocalDateTime recentDay = DateUtil.calculatingTime(new Date(), CalculateTypeEnum.SUBTRACT, 1, TimeTypeEnum.DAY);

        List<User> list = userMapper.selectList(null);
        //今年注册
        double incomeYear = list.stream().filter(e -> e.getAddTime().getYear() == year).count();
        //最近一个月注册
        double incomeMonth = list.stream().filter(e -> e.getAddTime().isAfter(lastMonth)).count();
        //日注册
        double incomeDay = list.stream().filter(e -> e.getAddTime().isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))).count();
        //昨日注册
        double yesterdayDay = list.stream().filter(e -> e.getAddTime().toLocalDate().isEqual(recentDay.toLocalDate())).count();
        //总注册
        double income = list.size();
        //LocalDateTime time = DateUtil.stringToDate("2021-11-27 00:00:00", "yyyy-MM-dd HH:mm:ss");
        //double income = list.stream().filter(e -> e.getAddTime().isAfter(time)).count();
        //总实名用户
        double realNameAuth = list.stream().filter(e -> ObjectUtils.isNotEmpty(e.getCardNumber())).count();
        Integer newUserDay = SystemConfig.getNewUserDay() == null ? 30 : Integer.valueOf(SystemConfig.getNewUserDay());
        //新用户
        double newUser = list.stream().filter(e -> Duration.between(e.getAddTime(), now).toDays() > 0 && Duration.between(e.getAddTime(), now).toDays() <= newUserDay).count();

        map.put("incomeYear", incomeYear);
        map.put("incomeMonth", incomeMonth);
        map.put("incomeDay", incomeDay);
        map.put("yesterdayDay", yesterdayDay);
        map.put("income", income);
        map.put("realNameAuth", realNameAuth);
        map.put("newUser", newUser);
        return GeneralResult.success(map);
    }


    //PC修改个人头像和密码
    @Override
    @Transactional
    public GeneralResult updatePassOrImg(String userImg, String oldPass, String newPass) {
        PubFun.check(userImg, oldPass, newPass);
        User user = SecurityUtils.getPrincipal();
        User pcUser = Optional.ofNullable(userMapper.selectById(user.getUserId())).orElseThrow(() -> new ServiceException(407, "该用户已经不存在"));
        //校验旧密码
        String md5 = MD5Utils.isEncryption(oldPass, pcUser.getSalt());
        if (!pcUser.getPassWord().equals(md5)) {
            throw new ServiceException(408, "您输入的旧密码错误,请重新输入");
        }
        String[] encryption = MD5Utils.encryption(newPass);
        pcUser.setPassWord(encryption[0]);
        pcUser.setSalt(encryption[1]);
        pcUser.setUpdateUserId(pcUser.getUserId());
        pcUser.setUpdateTime(LocalDateTime.now());
        pcUser.setUserImg(userImg);
        userMapper.updateById(pcUser);
        return GeneralResult.success();
    }

    //修改用户现金每场竞拍次数
    @Transactional
    @Override
    public GeneralResult updRobOrderLimit(Long userId, Integer remarks) {
        updateUserById(userId, remarks, 1);
        return GeneralResult.success();
    }

    @Transactional
    public void updateUserById(Long userId, Object remarks, Integer type) {
        PubFun.check(userId, remarks);
        User pUser = SecurityUtils.getPrincipal();
        if (((Integer) remarks) <= 0) {
            throw new ServiceException(621, "请输入大于零的数");
        }
        User user = userMapper.selectById(userId);
        switch (type) {
            case 1:
                user.setRobOrderLimit((Integer) remarks);
                break;
            case 2:
                user.setDiamondOrderLimit((Integer) remarks);
                break;
            case 3:
                user.setWithdrawalLimit(BigDecimal.valueOf((Double) remarks));
                break;
        }
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateUserId(pUser.getUserId());
        userMapper.updateById(user);
    }

    //修改用户钻石每场竞拍次数
    @Transactional
    @Override
    public GeneralResult updDiamondOrderLimit(Long userId, Integer remarks) {
        updateUserById(userId, remarks, 2);
        return GeneralResult.success();
    }

    //修改用户提现额度
    @Transactional
    @Override
    public GeneralResult updWithdrawalLimit(Long userId, BigDecimal remarks) {
        updateUserById(userId, remarks, 3);
        return GeneralResult.success();
    }


}

