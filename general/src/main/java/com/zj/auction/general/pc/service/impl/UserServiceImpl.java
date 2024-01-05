//package com.zj.auction.general.pc.service.impl;
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.enums.SqlLike;
//import com.fasterxml.jackson.databind.util.BeanUtil;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.zj.auction.common.base.BaseService;
//import com.zj.auction.common.base.BaseServiceImpl;
//import com.zj.auction.common.constant.RedisConstant;
//import com.zj.auction.common.dto.UserDTO;
//import com.zj.auction.common.exception.ServiceException;
//import com.zj.auction.common.mapper.UserMapper;
//import com.zj.auction.common.mapper.UserRoleMapper;
//import com.zj.auction.common.model.*;
//import com.zj.auction.common.shiro.SecurityUtils;
//import com.zj.auction.common.util.*;
//import com.zj.auction.general.pc.service.RoleService;
//import com.zj.auction.general.pc.service.UserConfigService;
//import com.zj.auction.general.pc.service.UserRoleService;
//import com.zj.auction.general.pc.service.UserService;
//import com.zj.auction.general.vo.GeneralResult;
//import com.zj.auction.general.vo.LoginResp;
//import com.zj.auction.general.vo.PageAction;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.bouncycastle.util.Integers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.*;
//import java.util.function.Function;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//
///**
// * @Title PcUserServiceImpl
// * @Package com.duoqio.boot.business.pc.service.user.impl
// * @Description PC用户管理
// * @Author Mao Qi
// * @Date 2021/1/20 15:24
// * @Copyright 重庆多企源科技有限公司
// * @Website {http://www.duoqio.com/index.asp?source=code}
// */
//@Log4j2
//@Repository
//@RequiredArgsConstructor(onConstructor_={@Autowired})
//public class UserServiceImpl extends BaseServiceImpl implements UserService {
//	private final UserService userService;
//	private final UserMapper userMapper;
//	private final UserRoleService userRoleService;
//	private final RoleService roleService;
//	private final UserConfigService userConfigService;
//	private final SHCommonDao shCommonDaoImpl;
//	private final BusinessBaseUtil businessBaseUtil;
//	private final PcUserRepository pcUserRepository;
//	private final PcSysrolecfgRepository pcSysRoleTblRepository;
//	private final AppMoneyInfoTblService iAppMoneyInfoTblService;
//	private final PcAreaRepository pcAreaRepository;
//	private final WxAppService wxAppService;
//	private final PcSysRoleTblService pcSysRoleTblService;
//	private final SystemCfgRepository systemCfgRepository;
//	private final PcShopRepository pcShopRepository;
//	private final PcBankCarRepository pcBankCarRepository;
//	private final PcGoodsRepository pcGoodsRepository;
//	private final SysLoadUtil sysLoadUtil;
//
//	@Autowired
//	public PcUserServiceImpl(SHCommonDao shCommonDaoImpl, BusinessBaseUtil businessBaseUtil,
//                             PcUserRepository pcUserRepository, PcSysrolecfgRepository pcSysRoleTblRepository,
//                             AppMoneyInfoTblService iAppMoneyInfoTblService, PcAreaRepository pcAreaRepository,
//                             WxAppService wxAppService, PcSysRoleTblService pcSysRoleTblService,
//                             SystemCfgRepository systemCfgRepository, PcShopRepository pcShopRepository,
//                             PcBankCarRepository pcBankCarRepository, PcGoodsRepository pcGoodsRepository, SysLoadUtil sysLoadUtil) {
//		this.shCommonDaoImpl = shCommonDaoImpl;
//		this.businessBaseUtil = businessBaseUtil;
//		this.pcUserRepository = pcUserRepository;
//		this.pcSysRoleTblRepository = pcSysRoleTblRepository;
//		this.iAppMoneyInfoTblService = iAppMoneyInfoTblService;
//		this.pcAreaRepository = pcAreaRepository;
//		this.wxAppService = wxAppService;
//		this.pcSysRoleTblService = pcSysRoleTblService;
//		this.systemCfgRepository = systemCfgRepository;
//		this.pcShopRepository = pcShopRepository;
//		this.pcBankCarRepository = pcBankCarRepository;
//		this.pcGoodsRepository = pcGoodsRepository;
//		this.sysLoadUtil = sysLoadUtil;
//	}
//
//	/**
//	 * @Title: getPcLogin
//	 * @Description: 后台登录
//	 * @author：Mao Qi
//
//	 * @param userName
//	 * @param password
//	 * @return
//	 *
//	 */
//	@Override
//	public LoginResp getPcLogin(String userName, String password){
//		// 数据校验
//		PubFun.check(userName,password);
//		LambdaQueryWrapper<User> wrapper =  new LambdaQueryWrapper<>(User.class);
//		wrapper.eq(User::getUserName,userName).eq(User::getDeleteFlag,0).eq(User::getUserType,1);
//		User user = userService.selectOne(wrapper);
//
//		if(user==null){
//			throw new ServiceException(610,"账号或密码错误,请重新输入");
//		}
//		//校验密码
//		String md5 = MD5Utils.isEncryption(password,user.getSalt());
//		if(!user.getPassWord().equals(md5)) {
//			throw new ServiceException(612,"您输入的密码错误,请重新输入");
//		}
//		// 保存最近一次登入时间
//		user.setLoginTime(LocalDateTime.now());
//		userService.insert(user);
//		return getPcLoginResp(user.getUserId(), user);
//	}
//
//	//生成包括token的返回登录数据
//	private LoginResp getPcLoginResp(Long userId, User userInfo) {
//		//生成请求token
//		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
//		json.put("userId", userId);
//		//生成token
//		String subject = json.toJSONString();
//		String token = AppTokenUtils.createToken(userId.toString().trim(), subject, 30 * 24 * 60 * 60 * 1000L);
//		//设置登录信息
//		LoginResp data = new LoginResp();
//		data.setUserId(userId);
//		data.setToken(token);
//		data.setUserInfo(userInfo);
//		//敏感信息不返回给前端，加入缓存2天方便解密时使用
//		AuthToken authToken = new AuthToken(userId, token);
//		authToken.setUser(userInfo);
//		RedisUtil.set(String.format(RedisConstant.PC_USER_TOKEN, userId), JSON.toJSONString(authToken),60*60*24*30L);
//		return data;
//	}
//
//
//	/**
//	 * @Title: findByMenuId
//	 * @Description:根据权限获取左侧菜单
//	 * @author：Mao Qi
//	 * @date： 2019年7月3日下午5:00:19
//	 * @param userId
//	 * @return
//	 *
//	 */
//	@Override
//	public List<Permis> findByMenuId(Long userId) {
//		if (userId==null){
//			throwException("参数缺失");
//		}
//		//	业务逻辑
//		Supplier<List<Permis>> deal = () -> {
//			//	查询当前用户的角色
//			StringBuilder sql = new StringBuilder();
//			Map<String, Object> map = new HashMap<>();
//			LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>(UserRole.class);
//			wrapper.select(UserRole::getRoleId).eq(UserRole::getUserId,userId);
//			List<Map<String,Object>> list= userRoleService.selectMaps(wrapper);
//			System.out.println(list);
////			sql.append("select role_id from sys_user_role_md where user_id=:userId ");
////			map.put("userId", userId);
////			List<Map<String,Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), map);
//			List<Permis> collect2 = new ArrayList<>();
//			if(!list.isEmpty()) {
//				List<Integer> collect = list.stream().map(m -> PubFun.ObjectStrongToInt(m.get("role_id"))).collect(Collectors.toList());
//				//获取用户角色
//
////				List<Role> sysRoleTblList = pcSysRoleTblRepository.findAllByDeleteFlagAndRoleIdIn(0, collect);
//				LambdaQueryWrapper<Role> wrapper1 = new LambdaQueryWrapper<>(Role.class);
//				wrapper1.in(Role::getRoleId,0+","+collect);
//				List<Role> sysRoleTblList = roleService.selectList(wrapper1);
//				System.out.println("==========角色列表========="+sysRoleTblList);
//				if(!sysRoleTblList.isEmpty()) {
//					//获取菜单
//					collect2 = sysRoleTblList.stream().filter(f ->f.getDeleteFlag()==0).map(Role::getPermisList).flatMap(fm -> fm.stream().filter(e -> e.getDeleteFlag()==0)).distinct().collect(Collectors.toList());
//				}
//			}
//			return collect2;
//		};
//
//		return super.base(deal);
//	}
//
//
//	/*----------------------------平台管理员-----------------------------*/
//
//	//查询平台管理员
//	@Override
//	public GeneralResult getManagerList(PageAction pageAction) {
//		User user = SecurityUtils.getPrincipal();
//		PageHelper.startPage(pageAction.getCountPage(),pageAction.getPageSize());
////		Pageable pageable = PageUtil.getPageable(pageAction.getCurrentPage(), pageAction.getPageSize(), Sort.Direction.DESC, "userId");
//		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
//		wrapper.eq(User::getDeleteFlag,0)
//				.eq(User::getUserType,1)
//				.ne(User::getUserId,1)
//				.or(!StringUtils.isEmpty(pageAction.getKeyword()))
//				.or(com.zj.auction.common.util.StringUtils.isNumeric(pageAction.getKeyword()))
//				.like(User::getUserId, com.zj.auction.common.util.StringUtils.toLong(pageAction.getKeyword()))
//				.like(User::getUserName,pageAction.getKeyword())
//				.like(User::getNickName,pageAction.getKeyword())
//				.like(User::getTel,pageAction.getKeyword());
//				List<User> userList =userService.selectList(wrapper);
//				PageInfo<User> pageInfo= new PageInfo<>(userList);
//				pageAction.setTotalCount(pageInfo.getSize());
//				pageAction.setTotalPage(pageInfo.getPageNum());
//				return GeneralResult.success(pageInfo.getList(),pageAction);
//	}
//
//	//添加管理员
//	@Override
//	@Transactional
//	public Boolean createManager(User userCfg) {
//		PubFun.check(userCfg.getUserName(),userCfg.getTel(),userCfg.getPassWord());
//		User user =SecurityUtils.getPrincipal();
//		//	查询该用户名是否使用
//		Integer count = userMapper.countByName(userCfg.getUserName());
//		if(count>0) {
//			throw new ServiceException(404,"该账号已存在!");
//		}
////		if(userCfg.getUserName().length()<6) throw new ServiceException(407,"账号设置太短");
//		User newUser = new User();
//		newUser.setUserName(userCfg.getUserName());
//		newUser.setNickName(userCfg.getRealName());
//		newUser.setRealName(userCfg.getRealName());
//		if(!StringUtils.isEmpty(userCfg.getUserImg())) newUser.setUserImg(userCfg.getUserImg());
//		String[] md5pwd = MD5Utils.encryption(userCfg.getPassWord());//md5加密
//		newUser.setPassWord(md5pwd[0]);
//		newUser.setSalt(md5pwd[1]);
//		newUser.setUserType(1);//后台管理员 用于区分APP登录
//		newUser.setTel(userCfg.getTel());
//		newUser.setStatus(0);
//		newUser.setRoleShopId(userCfg.getRoleShopId()==null?0L:userCfg.getRoleShopId());
//		newUser.setDeleteFlag(0);
//		newUser.setAddUserId(user.getUserId());
//		newUser.setUpdateTime(LocalDateTime.now());
//		/*newUser.setRoleRange(authToken.getRoleRange());
//		if(!StringUtils.isEmpty(userCfg.getRoleShopId())) {
//			newUser.setRoleShopId(param.getRoleShopId());
//		}*/
//
//		return userService.insert(newUser)>0;
//	}
//
//	//根据id查询会员信息
//	@Override
//	public User findManagerByUserId(User userCfg) {
//		if(super.baseCheck(userCfg.getUserId(), Objects::isNull)) {
//			throw new RuntimeException("未获取到管理员ID");
//		}
//		Function<User, User> deal = param->{
//			User u = Optional.ofNullable(userService.selectById(param.getUserId())).orElseThrow(()-> throwException("未查询到管理员信息"));
////			if (u==null){
////				throwException("未查询到管理员信息");
////			}
//			u.setSalt("");
//			u.setPassWord("******");
//			return u;
//		};
//		return super.base(userCfg, deal);
//	}
//
//	//修改管理员
//	@Transactional(rollbackFor = Exception.class)
//	public Boolean updateManager(UserDTO userCfg) {
//		User user =SecurityUtils.getPrincipal();
//		PubFun.check(userCfg,userCfg.getTel(),userCfg.getUserName(),userCfg.getRealName());
//		if(super.baseCheck(userCfg.getUserId(), Objects::isNull)) {
//			throw new RuntimeException("未获取到管理员ID");
//		}
//		Function<UserDTO, Boolean> deal = param->{
//			//	查询该用户名是否使用
//			Integer count = userMapper.countByName(param.getUserName());
//			if(count>0) {
//				throw new ServiceException(407,"该账号已注册");
//			}
////			User oldUser = pcUserRepository.findById(param.getUserId()).orElseThrow(()-> throwException("未查询到管理员信息"));
//			User oldUser = Optional.ofNullable(userService.selectById(param.getUserId())).orElseThrow(()-> throwException("未查询到管理员信息"));
//			if(!StringUtils.isEmpty(param.getUserName())) oldUser.setUserName(param.getUserName());
//			if(!StringUtils.isEmpty(param.getRealName())) oldUser.setRealName(param.getRealName());
//			//if(!StringUtils.isEmpty(param.getRealName())) oldUser.setNickName(param.getRealName());
//			if(!StringUtils.isEmpty(param.getUserImg())) oldUser.setUserImg(param.getUserImg());
//			if(param.getPassWord()!=null) {//x要修改密码
//				String[] md5pwd = MD5Utils.encryption(param.getPassWord());//md5加密
//				oldUser.setPassWord(md5pwd[0]);
//				oldUser.setSalt(md5pwd[1]);
//			}
//			oldUser.setRoleShopId(userCfg.getRoleShopId()==null?0L:userCfg.getRoleShopId());
//			if(!StringUtils.isEmpty(param.getTel())) oldUser.setTel(param.getTel());
//			UserConfig userConfig = new UserConfig();
//
//			if(!StringUtils.isEmpty(param.getAddress())) userConfig.setAddr(param.getAddress());
//			oldUser.setUpdateTime(LocalDateTime.now());
//			oldUser.setUpdateUserId(user.getUserId());
//			userConfig.setUserId(oldUser.getUserId());
//			userConfig.setUpdateTime(new Date());
////			if(param.getAgentUserId()==0) oldUser.setAgentUserId(user.getUserId());
//			//if(!StringUtils.isEmpty(param.getRoleRange())) oldUser.setRoleRange(param.getRoleRange());
//			//if(!StringUtils.isEmpty(param.getRoleShopId())) oldUser.setRoleShopId(param.getRoleShopId());
//            userService.insertUser(oldUser);
//            userConfigService.updateById(userConfig);
//			return true;
//		};
//		return super.base(userCfg, deal);
//	}
//
//
//
//    //删除管理员
//	@Override
//	@Transactional
//	public Boolean deleteUser(UserInfoTbl userCfg) {
//		UserInfoTbl user =SecurityUtils.getPrincipal();
//		if(super.baseCheck(userCfg.getUserId(), Objects::isNull)) {
//			throw new RuntimeException("未获取到管理员ID");
//		}
//		Function<UserInfoTbl, Boolean> deal = param->{
//			Optional<UserInfoTbl> userOpt = pcUserRepository.findById(param.getUserId());
//			UserInfoTbl oldUser = userOpt.orElseThrow(()-> throwException("未查询到管理员信息"));
//			if(oldUser.getUserType()==1){//店主
//				ShopInfoTbl shop = pcShopRepository.findByShopIdAndDeleteFlagFalseAndStatus(oldUser.getRoleShopId(), 0);
//				if(Objects.nonNull(shop)) throw new BaseException("该用户关联的店铺未下架,删除失败!");
//			}
//			oldUser.setDeleteFlag(true);
//			oldUser.setUpdateTime(LocalDateTime.now());
//			oldUser.setUpdateUserId(user.getUserId());
//			pcUserRepository.saveAndFlush(oldUser);
//			return true;
//		};
//		return super.base(userCfg, deal);
//	}
//
//
//
//	//修改管理员状态
//	@Override
//	@Transactional
//	public Boolean updateManagerStatus(UserInfoTbl userCfg) {
//		UserInfoTbl user =SecurityUtils.getPrincipal();
//		Function<UserInfoTbl, Boolean> deal = param->{
//			Optional<UserInfoTbl> userOpt = pcUserRepository.findById(param.getUserId());
//			UserInfoTbl oldUser = userOpt.orElseThrow(()-> throwException("未查询到管理员信息"));
//			if(param.getStatus()==1) {//禁用
//				oldUser.setStatus(1);
//				oldUser.setFrozenExplain(param.getFrozenExplain());
//				//冻结用户转拍并未抢拍的产品
//				pcGoodsRepository.updAuctionGoods(-1,param.getUserId(),user.getUserId());
//			}else {
//				oldUser.setStatus(0);
//				oldUser.setFrozenExplain(null);
//				//解冻用户转拍并未抢拍的产品
//				pcGoodsRepository.updAuctionGoods(0,param.getUserId(),user.getUserId());
//			}
//			oldUser.setUpdateTime(LocalDateTime.now());
//			oldUser.setUpdateUserId(user.getUserId());
//			pcUserRepository.saveAndFlush(oldUser);
//			return true;
//		};
//		return super.base(userCfg, deal);
//	}
//
//
//
//
//	/*----------------------------APP会员信息-----------------------------*/
//
//
//	//查询平台管理员
//	@Override
//	public Page<UserInfoTbl> getUserPage(PageAction pageAction,Integer userType,List<Long> userIds) {
//		LocalDateTime sTime =null;
//		LocalDateTime eTime =null;
//		if(!StringUtils.isEmpty(pageAction.getStartTime()) && !StringUtils.isEmpty(pageAction.getEndTime())){
//			sTime = DateTimeUtils.parse(pageAction.getStartTime());
//			eTime = DateTimeUtils.parse(pageAction.getEndTime());
//		}
//		UserInfoTbl user =SecurityUtils.getPrincipal();
//		Pageable pageable = PageUtil.getPageable(pageAction.getCurrentPage(), pageAction.getPageSize(), Sort.Direction.DESC, "userId");
//		Specification<UserInfoTbl> spec = Specifications.<UserInfoTbl>and()
//				.eq("deleteFlag",0)
//				.ge(StringUtils.isEmpty(userType),"userType",0)
//				.eq(!StringUtils.isEmpty(userType),"userType",userType)
//				.in(Objects.nonNull(userIds) && userIds.size()>0,"userId",userIds)
//				.between(Objects.nonNull(sTime) && Objects.nonNull(eTime),"addTime",sTime,eTime)
//				.predicate(!StringUtils.isEmpty(pageAction.getKeyword()), Specifications.or()
//						.eq(StringUtils.isNumeric(pageAction.getKeyword()), "userId", StringUtils.toLong(pageAction.getKeyword()))
//						.like("nickName", "%"+pageAction.getKeyword()+"%")
//						.like("realName", "%"+pageAction.getKeyword()+"%")
//						.like("userName", "%"+pageAction.getKeyword()+"%")
//						.build())
//				.build();
//		Page<UserInfoTbl> page = pcUserRepository.findAll(spec, pageable);
//		if(page.getContent().size()>0){
//			page.getContent().forEach(e->{
//				//用户银币余额
//				e.setSilverBalance(BigDecimal.ZERO);
//				//e.setSilverBalance(iAppMoneyInfoTblService.findUserIdTotal(e.getUserId(), 2).get("totalMoney"));
//				//用户金币余额
//				e.setGoldBalance(BigDecimal.ZERO);
//				//e.setGoldBalance(iAppMoneyInfoTblService.findUserIdTotal(e.getUserId(), 1).get("totalMoney"));
//				//用户佳士得钱包余额
//				e.setWallet(iAppMoneyInfoTblService.findUserIdTotal(e.getUserId(), 4).get("totalMoney"));
//				//用户佳士得绩效余额
//				e.setPerformance(iAppMoneyInfoTblService.findUserIdTotal(e.getUserId(), 5).get("totalMoney"));
//				//用户佳士得钻石余额
//				e.setDiamond(iAppMoneyInfoTblService.findUserIdTotal(e.getUserId(), 6).get("totalMoney"));
//				//用户佳士得积分余额
//				e.setIntegral(iAppMoneyInfoTblService.findUserIdTotal(e.getUserId(), 7).get("totalMoney"));
//				List<BankInfoTbl> banks = pcBankCarRepository.findAllByUserIdAndDeleteFlagFalse(e.getUserId());
//				e.setBank(banks.size()==0?null:banks.get(0));
//				if(e.getUserType()==1){
//					//店主营业额
//					e.setBalance(iAppMoneyInfoTblService.findUserIdTotal(e.getUserId(), 0).get("totalMoney"));
//				}
//			});
//		}
//
//		return page;
//	}
//
//
//	//根据用户id查询直推下级
//	@Override
//	public GeneralResult getSubUserPage(PageAction pageAction, Long pid) {
//		PubFun.check(pid);
//		LocalDateTime sTime = DateTimeUtils.parse(pageAction.getStartTime());
//		LocalDateTime eTime = DateTimeUtils.parse(pageAction.getEndTime());
//		UserInfoTbl user =SecurityUtils.getPrincipal();
//		Pageable pageable = PageUtil.getPageable(pageAction.getCurrentPage(), pageAction.getPageSize(), Sort.Direction.DESC, "userId");
//		Specification<UserInfoTbl> spec = Specifications.<UserInfoTbl>and()
//				.eq("deleteFlag",0)
//				.eq("pid",pid)
//				.ge("userType",0)
//				.between(Objects.nonNull(sTime) && Objects.nonNull(eTime),"addTime",sTime,eTime)
//				.predicate(!StringUtils.isEmpty(pageAction.getKeyword()), Specifications.or()
//						.eq(StringUtils.isNumeric(pageAction.getKeyword()), "userId", StringUtils.toLong(pageAction.getKeyword()))
//						.like("nickName", "%"+pageAction.getKeyword()+"%")
//						.like("userName", "%"+pageAction.getKeyword()+"%")
//						.build())
//				.build();
//		Page<UserInfoTbl> page = pcUserRepository.findAll(spec, pageable);
//		if(page.getContent().size()>0){
//			page.getContent().forEach(e->{
//				//查询直推统计
//				Specification<UserInfoTbl> spec1 = Specifications.<UserInfoTbl>and()
//						.eq("deleteFlag",0)
//						.eq("pid",e.getUserId())
//						.build();
//				Long count = pcUserRepository.count(spec1);
//				e.setSubUserNum(count.intValue());
//			});
//		}
//		pageAction.setTotalCount((int)page.getTotalElements());
//		return GeneralResult.success(page.getContent(),pageAction);
//	}
//
//
//
//	//修改会员状态
//	@Override
//	@Transactional
//	public Boolean updateUserStatus(Long userId, Integer status,String frozenExplain) {
//		UserInfoTbl user =SecurityUtils.getPrincipal();
//		UserInfoTbl oldUser = pcUserRepository.findById(userId).orElseThrow(()-> throwException("未查询到用户信息"));
//		oldUser.setStatus(status);
//		if(status==1){
//			oldUser.setFrozenExplain(frozenExplain);
//		}else{
//			oldUser.setFrozenExplain(null);
//		}
//		oldUser.setUpdateUserId(user.getUserId());
//		pcUserRepository.saveAndFlush(oldUser);
//		return true;
//	}
//
//	//根据用户id查询间接下级
//	@Override
//	public GeneralResult listMemberIndirect(PageAction pageAction, HashMap<String, Object> maps) {
//		GeneralResult generalResult = new GeneralResult();
//		Function<PageAction, GeneralResult> deal = parm -> {
//			StringBuffer totalSql = new StringBuffer();
//			HashMap<String, Object> map = new HashMap<>();
//			String wheres = "";
//			if(super.baseCheck(pageAction, param -> StringUtils.hasText(param.getKeyword()))){
//				wheres+=" and CONCAT(IFNULL(user_id,''),IFNULL(user_name,''),IFNULL(real_name,''),IFNULL(nick_name,''),IFNULL(tel,''),IFNULL(addr,'')) like CONCAT('%',:keyword,'%') ";
//				map.put("keyword", parm.getKeyword());
//			}
//			//根据开始时间
//			if(super.baseCheck(maps, paramMaps -> StringUtils.hasText(PubFun.ObjectStrongToString(paramMaps.get("startDate"))))) {
//				wheres+=" and add_time>=:startDate ";
//				map.put("startDate", PubFun.ObjectStrongToString(maps.get("startDate")));
//			}
//			//结束开始时间
//			if(super.baseCheck(maps, paramMaps -> StringUtils.hasText(PubFun.ObjectStrongToString(paramMaps.get("endDate"))))) {
//				wheres+=" and add_time<=:endDate ";
//				map.put("endDate", PubFun.ObjectStrongToString(maps.get("endDate")));
//			}
//			//用户等级
//			if(super.baseCheck(maps, paramMaps -> StringUtils.hasText(PubFun.ObjectStrongToString(paramMaps.get("userType"))))) {
//				wheres+=" and user_type =:userType ";
//				map.put("userType", PubFun.ObjectStrongToString(maps.get("userType")));
//			}
//			totalSql.append("select user_id from user_info_tbl where pid_str like CONCAT('%',:userIds,'%') and pid!=:userId and role_type = 0 "+wheres+" and delete_flag = 0");
//			map.put("userIds", ","+maps.get("userId")+",");
//			map.put("userId", maps.get("userId"));
//			Integer allPage = shCommonDaoImpl.getSQLRecordNumber(totalSql.toString(), map);
//			if(allPage>0) {
//				StringBuffer sql = new StringBuffer();
//				sql.append(" select a.*,b.role_name from (select * from user_info_tbl where pidStr like CONCAT('%',:userIds,'%') and pid!=:userId and role_type = 0 "+wheres+" and delete_flag = 0 order by add_time desc LIMIT :currentSize , :pageSize ) a left join level_role_tbl b on a.user_type = b.role_id ");
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
//
//	/**
//	 * @title: findMemberAudit
//	 * @description: 查询待审核实名
//	 * @author: Mao Qi
//	 * @date: 2020年4月3日下午5:51:14
//	 * @param pageAction
//	 * @param maps
//	 * @return
//	 */
//	@Override
//	public GeneralResult findMemberAudit(PageAction pageAction, HashMap<String, Object> maps) {
//		GeneralResult generalResult =new GeneralResult();
//		Function<PageAction, GeneralResult> deal = parm -> {
//			StringBuffer totalSql = new StringBuffer();
//			HashMap<String, Object> map = new HashMap<>();
//			String wheres = "";
//			if(super.baseCheck(pageAction, param -> StringUtils.hasText(param.getKeyword()))){
//				wheres+=" and CONCAT(IFNULL(user_id,''),IFNULL(user_name,''),IFNULL(real_name,''),IFNULL(nick_name,''),IFNULL(tel,''),IFNULL(addr,'')"
//						+ ",IFNULL(pid,''),IFNULL(p_user_name,'')) like CONCAT('%',:keyword,'%') ";
//				map.put("keyword", parm.getKeyword());
//			}
//			//根据开始时间
//			if(super.baseCheck(maps, paramMaps -> StringUtils.hasText(PubFun.ObjectStrongToString(paramMaps.get("startDate"))))) {
//				wheres+=" and add_time>=:startDate ";
//				map.put("startDate", PubFun.ObjectStrongToString(maps.get("startDate")));
//			}
//			//结束开始时间
//			if(super.baseCheck(maps, paramMaps -> StringUtils.hasText(PubFun.ObjectStrongToString(paramMaps.get("endDate"))))) {
//				wheres+=" and add_time<=:endDate ";
//				map.put("endDate", PubFun.ObjectStrongToString(maps.get("endDate")));
//			}
//			totalSql.append("select user_id from user_info_tbl where  audit>0 and role_type = 0 "+wheres+" and delete_flag = 0");
//			Integer allPage = shCommonDaoImpl.getSQLRecordNumber(totalSql.toString(), map);
//			if(allPage>0) {
//				StringBuffer sql = new StringBuffer();
//				sql.append(" select * from user_info_tbl where  audit>0 and role_type = 0 "+wheres+" and delete_flag = 0 order by add_time desc LIMIT :currentSize , :pageSize ");
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
//
//	// 添加用户
//	@Override
//	public Boolean insertUser(UserInfoTbl userCfg) {
//        UserInfoTbl principal = SecurityUtils.getPrincipal();
//		PubFun.check(userCfg,userCfg.getUserName(),userCfg.getNickName(),userCfg.getUserImg(),userCfg.getPassWord(),
//				userCfg.getUserType(),userCfg.getMemo1(), userCfg.getMemo2(),userCfg.getMemo3());
//		//较验手机号
//		List<UserInfoTbl> findByTel = pcUserRepository.findAllByUserNameAndRoleTypeAndDeleteFlagFalse(userCfg.getUserName(),0);
//		if(!findByTel.isEmpty()) throw new BaseException("此手机已经注册了!");
//		userCfg.setPid(0L);
//		userCfg.setLevelNum(1);
//		userCfg.setTel(userCfg.getUserName());
//		userCfg.setRoleType(0);
//		userCfg.setStatus(0);
//		userCfg.setShareType(0);
//		userCfg.setAudit(2);//审核状态：0默认，1待审核，2审核通过，3拒绝审核
//		String[] md5pwd = MD5Utils.encryption(userCfg.getPassWord());//md5加密
//		userCfg.setPassWord(md5pwd[0]);
//		userCfg.setSalt(md5pwd[1]);
//		userCfg.setSignNum(0);
//		userCfg.setProvinceId( PubFun.selectToLong(userCfg.getMemo1()));
//		AreaInfoTbl provinceArea = pcAreaRepository.findByDeleteFlagFalseAndAreaId(PubFun.selectToLong(userCfg.getMemo1()));
//		userCfg.setProvince(provinceArea.getAreaName());
//		userCfg.setCityId( PubFun.selectToLong(userCfg.getMemo2()));
//		AreaInfoTbl cityArea = pcAreaRepository.findByDeleteFlagFalseAndAreaId(PubFun.selectToLong(userCfg.getMemo2()));
//		userCfg.setCity(cityArea.getAreaName());
//		userCfg.setCountyId( PubFun.selectToLong(userCfg.getMemo3()));
//		AreaInfoTbl countyArea = pcAreaRepository.findByDeleteFlagFalseAndAreaId(PubFun.selectToLong(userCfg.getMemo3()));
//		userCfg.setCounty(countyArea.getAreaName());
//		userCfg.setAddr(provinceArea.getAreaName()+""+cityArea.getAreaName()+""+countyArea.getAreaName() );
//		userCfg.setSignMoney(BigDecimal.ZERO);
//		userCfg.setIsFirstLogin(0);
//		userCfg.setSubUserNum(0);
//		userCfg.setSubUserNumAll(0);
//		UserInfoTbl userInfoTbl = pcUserRepository.save(userCfg);
//		/*if(userCfg.getUserType().compareTo(2)==0){//经纪人
//			//TODO 生成小程序码  分享经纪人下载的小程序码
//			String src = wxAppService.getWxAppCodeUnlimit(userInfoTbl.getUserId().toString(), null, 430, false, null, false,3);
//			userInfoTbl.setShareImg(src);//经纪人分享小程序二维码
//			pcUserRepository.save(userInfoTbl);
//		}*/
//		return true;
//	}
//
//	// 查看用户信息
//	@Override
//	public UserInfoTbl getUserByUserId(Long userId) {
//		SecurityUtils.getPrincipal();
//		if (super.baseCheck(userId, Objects::isNull)) {
//			throw new BaseException(SystemConstant.DATA_ILLEGALITY_CODE);
//		}
//		Function<Long, UserInfoTbl> deal = parm -> pcUserRepository.findByDeleteFlagFalseAndUserId(parm);
//		return super.base(userId, deal);
//	}
//
//	@Override
//	@Transactional
//	public Boolean deleteMember(UserInfoTbl userCfg) {
//		UserInfoTbl user = SecurityUtils.getPrincipal();
//		if(super.baseCheck(userCfg, param -> Objects.isNull(param) || !StringUtils.hasText(param.getMemo1())) ){
//			throw new RuntimeException("参数为空");
//		}
//		Function<UserInfoTbl, Boolean> deal = param->{
//			String ids = param.getMemo1();
//			List<Integer> collect = Arrays.asList(ids.split(",")).stream().map(m->Integer.parseInt(m.trim())).collect(Collectors.toList());
//			StringBuilder sql = new StringBuilder();
//			HashMap<String, Object> map = new HashMap<>();
//			sql.append("update user_info_tbl set delete_flag=1,update_time = now(),update_name=:updateName where user_id in(:userId) ");
//			map.put("userId", collect);
//			map.put("updateName", user.getUserId());
//			shCommonDaoImpl.updateJDBC(sql.toString(), map);
//			return true;
//		};
//		return super.base(userCfg, deal);
//	}
//
//	//查询用户统计
//	@Override
//	public HashMap<String, Object> getStatistics(UserInfoTbl userCfg) {
//		if(super.baseCheck(userCfg.getUserId(), Objects::isNull)) {
//			throw new RuntimeException("未获取到会员ID");
//		}
//		Function<UserInfoTbl, HashMap<String, Object>> deal = param->{
//			HashMap<String, Object> map = new HashMap<>();
//			HashMap<String, Object> mapSql = new HashMap<>();
//			StringBuilder sql = new StringBuilder();
//			String userIdStr = ","+param.getUserId()+",";
//			//sql.append("select COUNT(*) as stock,(case when pid=:userId then count(user_id) else 0 end) as num1,(case when pid!=:userId then count(user_id) else 0 end) as num2 from user_info_tbl where pidStr like CONCAT('%',:userIdStr,'%')");
//			sql.append("SELECT COUNT(*) AS stock,ifnull(sum( case when pid=:userId then 1 else 0 end ),0) as num1,ifnull(sum( case when pid !=:userId then 1 else 0 end),0) as num2 FROM user_info_tbl where pid_str like CONCAT( '%',:userIdStr,'%' )");
//			mapSql.put("userId", param.getUserId());
//			mapSql.put("userIdStr", userIdStr);
//			List<Map<String,Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), mapSql);
//			//总人数
//			map.put("stock", PubFun.ObjectStrongToInt(list.get(0).get("stock")));
//			//直推人数
//			map.put("num1", PubFun.ObjectStrongToInt(list.get(0).get("num1")));
//			//间推
//			map.put("num2", PubFun.ObjectStrongToInt(list.get(0).get("num2")));
//			//累计余额，可提余额，未结算余额
//			Map<String,BigDecimal> maps1 = iAppMoneyInfoTblService.findUserIdTotal(param.getUserId(),0);//余额
//			map.putAll(maps1);
//			//累计余额，可提余额，未结算余额
//			Map<String,BigDecimal> maps2 = iAppMoneyInfoTblService.findUserIdTotal(param.getUserId(),1);//积分
//			map.put("cardTotal",maps2.get("totalMoney"));
//			//购买金额
//			Map<String,BigDecimal> findUserId2 = iAppMoneyInfoTblService.findUserIdTotal(param.getUserId(),2);//第三方
//			map.put("buyMoney", PubFun.ObjectStrongToDouble(findUserId2.get("reduceMoney")) + PubFun.ObjectStrongToDouble(maps1.get("reduceMoney")));
//			return map;
//		};
//		return super.base(userCfg, deal);
//	}
//
//	// 查看自定义参数配置
//	@Override
//	public GeneralResult getAllCfgs() {
//		return GeneralResult.success(systemCfgRepository.findAll());
//	}
//
//	// 查看所有自定义参数配置
//	@Override
//	public GeneralResult findParameter(PageAction pageAction) {
//		GeneralResult generalResult = new GeneralResult();
//		Function<PageAction, GeneralResult> deal = parm -> {
//			StringBuffer sql = new StringBuffer();
//			Map<String, Object> map = new HashMap<>();
//			sql.append(" SELECT key_id as keyId,key_name as keyName,key_value as keyValue,memo FROM system_cfg_tbl WHERE key_id > 100 ");
//			if(super.baseCheck(pageAction, param -> StringUtils.hasText(parm.getKeyword()))){
//				sql.append(" and CONCAT(IFNULL(key_id,''),IFNULL(key_name,''),IFNULL(key_value,''),IFNULL(memo,'')) like '%"+parm.getKeyword()+"%'");
//			}
//			return businessBaseUtil.basePcPageAction(sql, map, parm, generalResult, par -> par.append(" "));
//		};
//		return businessBaseUtil.baseCheckSessionAndPageAction(pageAction, deal);
//	}
//
//	// 修改自定义参数配置
//	@Transactional
//	@Override
//	public Integer updateCfg(SystemCfgTbl systemCfg) {
//		UserInfoTbl user =SecurityUtils.getPrincipal();
//		Boolean aBoolean = sysLoadUtil.updSysParam(systemCfg);
//		if(!aBoolean){
//			throw new BaseException("参数修改失败");
//		}
//		return 1;
//	}
//
//	@Transactional
//	@Override
//	public Integer updateCfgTwo( String keyValue,  String keyId, String memo,String keyName) throws Exception {
//		//UserInfoTbl user =SecurityUtils.getPrincipal();
//		//log.info("参数:"+keyValue+keyId);
//		PubFun.check(keyValue,keyId,memo,keyName);
//		StringBuffer sql = new StringBuffer();
//		Map<String, Object> map = new HashMap<>();
//		sql.append(" update system_cfg_tbl set key_value = :keyValue,memo =:memo where key_id = :keyId ");
//		map.put("keyValue", keyValue);
//		map.put("keyId", keyId);
//		map.put("memo", memo);
//		Integer result = shCommonDaoImpl.updateJDBCCheck(sql.toString(), map);
//
//		Class<?> clazz = Class.forName("com.duoqio.entity.custom.SystemConfig");
//		Object obj = clazz.newInstance();
//		List<Method> ms = Arrays.asList(clazz.getMethods());
//		ms.stream().filter(m -> m.getName().equalsIgnoreCase("set"+keyName)).forEach(m -> {
//			try {
//				m.invoke(obj, keyValue);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//		return result;
//	}
//
//
//	private RuntimeException throwException(String errorMessage){
//		return new RuntimeException(errorMessage);
//	}
//
//	@Override
//	public Map<String, Object> listArea() {
//		StringBuilder sql = new StringBuilder();
//		Map<String, Object> map = new HashMap<>();
//		sql.append("select * from area_info_tbl where delete_flag = 0");
//		List<Map<String, Object>> list = shCommonDaoImpl.getSqlList(sql.toString(), null);
//		if(!list.isEmpty()) {
//			List<Map<String, Object>> collect = list.stream().filter(f -> PubFun.ObjectStrongToInt(f.get("level_num"))==1).collect(Collectors.toList());
//			map.put("collect", collect);
//			List<Map<String, Object>> collect2 = list.stream().filter(f -> PubFun.ObjectStrongToInt(f.get("level_num"))==2).collect(Collectors.toList());
//			map.put("collect2", collect2);
//			List<Map<String, Object>> collect3 = list.stream().filter(f -> PubFun.ObjectStrongToInt(f.get("level_num"))==3).collect(Collectors.toList());
//			map.put("collect3", collect3);
//		}
//		return map;
//	}
//
//	/**
//	 * @title: updateAuditRejection
//	 * @description: 会员审核拒绝通过
//	 * @author: Mao Qi
//	 * @date: 2020年4月3日下午7:22:33
//	 * @param userId
//	 * @param auditExplain
//	 * @return
//	 */
//	@Transactional
//	@Override
//	public Integer updateAuditRejection(Long userId, String auditExplain) {
//		SecurityUtils.getPrincipal();
//		if (super.baseCheck(userId, Objects::isNull)) {
//			throw new BaseException(SystemConstant.DATA_ILLEGALITY_CODE);
//		}
//		Function<Long, Integer> deal = parm -> {
//			StringBuffer sql = new StringBuffer();
//			sql.append(" update user_info_tbl set audit = 3,audit_explain=:auditExplain where user_id = :userId ");
//			Map<String, Object> map = new HashMap<>();
//			map.put("userId", parm);
//			map.put("auditExplain", auditExplain);
//			return shCommonDaoImpl.updateJDBCCheck(sql.toString(), map);
//		};
//		return super.base(userId, deal);
//	}
//
//	//实名通过审核
//	@Transactional
//	@Override
//	public Integer updateAuditApproval(Long userId) {
//		SecurityUtils.getPrincipal();
//		if (super.baseCheck(userId, Objects::isNull)) {
//			throw new BaseException(SystemConstant.DATA_ILLEGALITY_CODE);
//		}
//		Function<Long, Integer> deal = parm -> {
//			StringBuffer sql = new StringBuffer();
//			sql.append(" update user_info_tbl set audit = 2,audit_explain=null where user_id = :userId ");
//			Map<String, Object> map = new HashMap<>();
//			map.put("userId", parm);
//			return shCommonDaoImpl.updateJDBCCheck(sql.toString(), map);
//		};
//		return super.base(userId, deal);
//	}
//
//	//后台操作变更资金
//	@Override
//	public Integer changeBalance(Long userId,Integer moneyType,Integer type, BigDecimal integral,String remark) {
//		UserInfoTbl pcUser = SecurityUtils.getPrincipal();
//		PubFun.check(userId,moneyType,type);
//		if(StringUtils.isEmpty(integral) || integral.compareTo(BigDecimal.ZERO)<=0) {
//			throw new BaseException("变更数量输入有误!");
//		}
//		UserInfoTbl user =pcUserRepository.findById(userId).orElseThrow(()-> PubFun.throwException("该用户不存在"));
//		if(type==1) {//增加
//			String transactionId = UidGenerator.createOrderXid();
//			String memo = "";
//			if(moneyType==0){
//				memo = "后台人工增加店铺收入";
//			}else if(moneyType == 1){
//				memo = "后台人工充值获得";
//			}else{
//				memo = "后台人工充值获得";
//			}
//			//添加余额
//			MoneyInfoTbl saveMoney = saveMoney(user.getUserId(), 1, 14, integral, 1, 1, 1, 0, memo, 0, pcUser.getUserId(), moneyType, memo, transactionId, null, remark);
//			iAppMoneyInfoTblService.addMoneyInfoTbl(saveMoney);
//			// addTotalPlatform(1, integral, saveMoney);//给平台加明细
//		}else if(type==2) {//减少
//			//查询该用户剩余余额数量
//			BigDecimal allIntegral = iAppMoneyInfoTblService.findUserIdTotal(user.getUserId(),moneyType).get("totalMoney");
//			if(allIntegral.compareTo(BigDecimal.ZERO)<1 ||  allIntegral.compareTo(integral)==-1) {
//				throw new BaseException("余额不足");
//			}
//			String transactionId = UidGenerator.createOrderXid();
//			String memo = "";
//			if(moneyType==0){
//				memo = "后台人工减少店铺收入";
//			}else if(moneyType == 1){
//				memo = "后台人工回收支出";
//			}else{
//				memo = "后台人工回收支出";
//			}
//			//扣除余额
//			MoneyInfoTbl saveMoney = saveMoney(user.getUserId(), -1, 14, integral, 1, 1, 1, 0, memo, 0, pcUser.getUserId(), moneyType, memo, transactionId, null, remark);
//			iAppMoneyInfoTblService.addMoneyInfoTbl(saveMoney);
//			//addTotalPlatform(-1, integral, saveMoney);//给平台加明细
//		}else {
//			throw new RuntimeException("变更余额类型有误");
//		}
//		return null;
//	}
//
//	//给总平台加明细
//	private void addTotalPlatform(Integer addType,BigDecimal integral, MoneyInfoTbl saveMoney) {
//		//iAppMoneyInfoTblService.addMoneyInfoTbl(saveMoney);
//		//添加总平台资金明细
//		MoneyInfoTbl data = new MoneyInfoTbl();
//		data.setUserId(PubFun.StringToLong(SystemConfig.getTotalPlatformId()==null?"1": SystemConfig.getTotalPlatformId()));
//		data.setStatus(1);//正常
//		data.setAddType(addType);//收入
//		data.setOptType(20);//自定义说明 20后台操作
//		data.setMoneyNum(integral);//金额
//		if(addType==1){
//			data.setMemo("后台添加余额补充值：" + integral);//备注
//		}else{
//			data.setMemo("后台减少余额减充值：" + integral);//备注
//		}
//		data.setTradeNo(null);
//		data.setPayType(-1);//后台添加
//		data.setMoneyType(0);//支付类型
//		data.setUseType(1);//用户购买商品
//		data.setResourceId(-1L);//父订单id
//		iAppMoneyInfoTblService.addMoneyInfoTbl(data);
//	}
//
//	//后台操作卡劵
//	@Override
//	public Integer changeCardVolume(Long userId,Integer type,BigDecimal cardVolumeTotal,String remark){
//		UserInfoTbl sessUser =  SecurityUtils.getPrincipal();
//		if(super.baseCheck(userId, Objects::isNull)) {
//			throw new RuntimeException("用户ID为空");
//		}
//		if(super.baseCheck(cardVolumeTotal,money-> !PubFun.isNumber(""+money))) {
//			throw new RuntimeException("数量有误");
//		}
//		UserInfoTbl user =pcUserRepository.findById(userId).orElseThrow(()-> PubFun.throwException("该用户不存在"));
//		if(type==1) {//增加
//			String transactionId = UidGenerator.createOrderXid();
//			//添加余额
//			MoneyInfoTbl saveMoney = saveMoney(user.getUserId(), 1, 14, cardVolumeTotal, 1, 2, 1, 0, "后台手动增加卡劵", 0, sessUser.getUserId(), 1, "管理员后台手动增加卡劵："+cardVolumeTotal, transactionId, null, remark);
//			iAppMoneyInfoTblService.addMoneyInfoTbl(saveMoney);
//		}else if(type==2) {//减少
//			//查询该用户剩余余额数量
//			BigDecimal allIntegral = new BigDecimal(""+ iAppMoneyInfoTblService.findUserIdTotal(user.getUserId(),1).get("totalMoney"));
//			if(allIntegral.compareTo(BigDecimal.ZERO)<1 ||  allIntegral.compareTo(cardVolumeTotal)==-1) {
//				throw new RuntimeException("卡劵不足");
//			}
//			String transactionId = UidGenerator.createOrderXid();
//			//扣除余额
//			MoneyInfoTbl saveMoney = saveMoney(user.getUserId(), -1, 14, cardVolumeTotal, 1, 2, 1, 0, "后台手动扣除积卡劵", 0, sessUser.getUserId(), 1, "管理员后台手动扣除卡劵："+cardVolumeTotal, transactionId, null, remark);
//			iAppMoneyInfoTblService.addMoneyInfoTbl(saveMoney);
//		}else {
//			throw new RuntimeException("变更卡劵类型有误");
//		}
//		return null;
//	};
//
//
//
//	/**
//	 * @Title: saveMoney
//	 * @Description: 创建财务对象
//	 * @author：Mao Qi
//	 * @date： 2019年7月12日下午4:13:06
//	 * @param userId 用户人id
//	 * @param addType 1:添加, -1:减少
//	 * @param optType 操作类型
//	 * @param moneyNum 金额
//	 * @param status 状态
//	 * @param useType 使用类型
//	 * @param userType 用户类型
//	 * @param type 类型
//	 * @param remarks 备注
//	 * @param payType 支付类型
//	 * @param oppositeUserId 创收人
//	 * @param moneyType 金额类型
//	 * @param memo 说明
//	 * @param transactionId 事务id
//	 * @param tradeNo 交易号
//	 * @return：MoneyInfoTbl
//	 */
//	private static MoneyInfoTbl saveMoney(Long userId, Integer addType, Integer optType, BigDecimal moneyNum
//			,Integer status, Integer useType, Integer userType, Integer type, String remarks, Integer payType
//			,Long oppositeUserId, Integer moneyType, String memo,String transactionId, String tradeNo, String memo1) {
//		MoneyInfoTbl money = new MoneyInfoTbl();
//		money.setUserId(userId);
//		money.setAddType(addType);
//		money.setOptType(optType);
//		money.setMoneyNum(moneyNum);
//		money.setStatus(status);
//		money.setUseType(useType);
//		money.setUserType(userType);
//		money.setType(type);
//		money.setRemarks(remarks);
//		money.setPayType(payType);
//		money.setOppositeUserId(oppositeUserId);
//		money.setMoneyType(moneyType);
//		money.setMemo(memo);
//		money.setTransactionId(transactionId);
//		money.setTradeNo(tradeNo);
//		money.setMemo1(memo1);
//		money.setResourceId(-1L);
//		return money;
//	}
//
//
//	//条件查询APP用户(APP用户管理)
//	@Override
//	public Page<UserInfoTbl> findAPPUser(String keyword, Pageable pageable) {
//		if("".equals(keyword)) {
//			return pcUserRepository.findByDeleteFlagFalseAndRoleType(pageable,0);
//		}else{
//			String c = "%"+keyword+"%";
//			Page<UserInfoTbl> data = pcUserRepository.findByUserLike(c,pageable);
//			return data;
//		}
//	}
//
//	//根据用户名查找用户
//	@Override
//	public UserInfoTbl findByDeleteFlagFalseAndUserName(String userName) {
//		return pcUserRepository.findByDeleteFlagFalseAndUserNameAndRoleType(userName,1);
//	}
//
//	//导出用户
//	@Override
//	public void exportUser(PageAction pageAction,Integer userType,String userIds, HttpServletResponse httpServletResponse) {
//		List<Long> userList = new ArrayList<>();
//		if(!StringUtils.isEmpty(userIds)){
//			userList = JSON.parseArray(userIds, Long.class);
//		}
//		/*StringBuffer sql = new StringBuffer();
//		Map<String, Object> map = new HashMap<>();
//		UserInfoTbl user = SecurityUtils.getPrincipal();
//		StringBuffer auth = new StringBuffer();
//		// 查询指定条件的用户
//		selUserSql(pageAction, sql, map,auth);
//		sql.append(" order by t1.add_time desc ");
//		List<Map<String, Object>> sqlList = shCommonDaoImpl.getSqlList(sql.toString(), map);*/
//		Page<UserInfoTbl> userPage = getUserPage(pageAction, userType, userList);
//
//		List<List<String>> excelData = new ArrayList<>();
//		List<String> head = new ArrayList<>();
//		head.add("用户ID");
//		head.add("账号");
//		head.add("昵称");
//		head.add("电话");
//		head.add("用户类型");
//		head.add("金币余额");
//		head.add("银币余额");
//		head.add("店铺收入");
//		head.add("注册时间");
//		// 添加头部
//		excelData.add(head);
//		for(UserInfoTbl user : userPage.getContent()){
//			List<String> data1 = new ArrayList<>();
//			data1.add(user.getUserId().toString());  //ID
//			data1.add(user.getUserName());  //账号
//			data1.add(Objects.toString(user.getNickName(), ""));  //昵称
//			data1.add(Objects.toString(user.getTel(), ""));  //手机号
//			data1.add(user.getUserType()==1?"店主":"用户");   //用户类型
//			data1.add(Objects.toString(user.getGoldBalance(), "0"));   //金币余额
//			data1.add(Objects.toString(user.getSilverBalance(), "0"));   //银币余额
//			data1.add(Objects.toString(user.getBalance(), "0"));   //店铺收入
//			data1.add(Objects.toString(DateTimeUtils.toString(user.getAddTime(),"yyyy-MM-dd HH:mm:ss"),""));
//			excelData.add(data1);
//		}
//		ExcelUtil.exportExcel(httpServletResponse,
//				excelData, "会员信息", "member.xls", 20);
//	}
//
//
//
//
//
//
//
//	/**
//	 * @Description 所有上级
//	 * @Title listParentUser
//	 * @Author Mao Qi
//	 * @Date 2019/10/24 21:35
//	 * @param userId
//	 * @param upLevel
//	 * @return	java.util.List<com.duoqio.boot.business.entity.UserInfoTbl>
//	 */
//	@Override
//	public List<UserInfoTbl> listParentUser(Long userId, Integer upLevel) {
//		if(super.baseCheck(userId, Objects::isNull)) {
//			throw new RuntimeException("用户ID为空");
//		}
//		if(super.baseCheck(upLevel, Objects::isNull)) {
//			throw new RuntimeException("等级为空");
//		}
//		List<UserInfoTbl> userList = new ArrayList<>();
//		UserInfoTbl user = pcUserRepository.findById(userId).orElseThrow(()-> PubFun.throwException("未查询到用户信息"));
//		String pidStr = user.getPidStr();
//		if(pidStr!=null && !"".equals(pidStr)) {
//			pidStr = pidStr.substring(1, pidStr.length()-1).replace(",,", ",");
//			List<Long> collect = null;
//			if(upLevel==-1){//查询所有上级
//				collect = Arrays.asList(pidStr.split(",")).stream().map(m->Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//			}else{//查询限制级别的所有上级
//				collect = Arrays.asList(pidStr.split(",")).stream().map(m->Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).limit(upLevel).collect(Collectors.toList());
//			}
//			if(!collect.isEmpty()) {
//				userList = pcUserRepository.findAllByUserIdInOrderByLevelNumDesc(collect);
//			}
//		}
//		return userList;
//	}
//
//	//根据用户id查询用户信息
//	@Override
//	public UserInfoTbl findUserById(Long userId) {
//		return pcUserRepository.findById(userId).orElseThrow(()->new BaseException("用户不存在!"));
//	}
//
//
//	//注册统计
//	@Override
//	public GeneralResult statistics(){
//		//获取当前用户信息
//		UserInfoTbl user = SecurityUtils.getPrincipal();
//		Map<String, Object> map = new HashMap<>();
//		//今年
//		Integer year = LocalDateTime.now().getYear();
//		//最近一个月
//		LocalDateTime now = DateTimeUtils.now();
//		LocalDateTime lastMonth = now.minusMonths(1);
//		//昨天
//		LocalDateTime recentDay = DateUtil.calculatingTime(new Date(), CalculateTypeEnum.SUBTRACT, 1, TimeTypeEnum.DAY);
//		Specification<UserInfoTbl> spec = Specifications.<UserInfoTbl>and()
//				.ge("userType",0)
//				.build();
//		List<UserInfoTbl> list = pcUserRepository.findAll(spec);
//		//今年注册
//		double incomeYear = list.stream().filter(e -> e.getAddTime().getYear()==year).count();
//		//最近一个月注册
//		double incomeMonth = list.stream().filter(e -> e.getAddTime().isAfter(lastMonth)).count();
//		//日注册
//		double incomeDay = list.stream().filter(e -> e.getAddTime().isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))).count();
//		//昨日注册
//		double yesterdayDay = list.stream().filter(e -> e.getAddTime().toLocalDate().isEqual(recentDay.toLocalDate())).count();
//		//总注册
//		double income = list.size();
//		//LocalDateTime time = DateUtil.stringToDate("2021-11-27 00:00:00", "yyyy-MM-dd HH:mm:ss");
//		//double income = list.stream().filter(e -> e.getAddTime().isAfter(time)).count();
//		//总实名用户
//		double realNameAuth = list.stream().filter(e -> ObjectUtil.isNotEmpty(e.getCardNumber())).count();
//		Integer newUserDay = SystemConfig.getNewUserDay() == null ? 30 : Integer.valueOf(SystemConfig.getNewUserDay());
//		//新用户
//		double newUser = list.stream().filter(e -> Duration.between(e.getAddTime(),now).toDays()>0 && Duration.between(e.getAddTime(),now).toDays()<=newUserDay).count();
//
//		map.put("incomeYear", incomeYear);
//		map.put("incomeMonth", incomeMonth);
//		map.put("incomeDay", incomeDay);
//		map.put("yesterdayDay", yesterdayDay);
//		map.put("income", income);
//		map.put("realNameAuth", realNameAuth);
//		map.put("newUser", newUser);
//		return GeneralResult.success(map);
//	}
//
//	//注册K线统计
//	@Override
//	public GeneralResult registerKLine(){
//		//获取当前用户信息
//		UserInfoTbl user = SecurityUtils.getPrincipal();
//		List<Long> resList = new ArrayList<>(7);
//		//获取今天周几
//		Integer dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
//		LocalDate startTime = LocalDate.now().minusDays(dayOfWeek - 1);
//		Specification<UserInfoTbl> spec = Specifications.<UserInfoTbl>and()
//				.ge("userType",0)
//				.ge("addTime",LocalDateTime.of(startTime, LocalTime.MIN))
//				.build();
//		List<UserInfoTbl> list = pcUserRepository.findAll(spec);
//		resList.add(list.stream().filter(u -> u.getAddTime().getDayOfWeek().getValue() == 0).count());
//		resList.add(list.stream().filter(u -> u.getAddTime().getDayOfWeek().getValue() == 1).count());
//		resList.add(list.stream().filter(u -> u.getAddTime().getDayOfWeek().getValue() == 2).count());
//		resList.add(list.stream().filter(u -> u.getAddTime().getDayOfWeek().getValue() == 3).count());
//		resList.add(list.stream().filter(u -> u.getAddTime().getDayOfWeek().getValue() == 4).count());
//		resList.add(list.stream().filter(u -> u.getAddTime().getDayOfWeek().getValue() == 5).count());
//		resList.add(list.stream().filter(u -> u.getAddTime().getDayOfWeek().getValue() == 6).count());
//		return  GeneralResult.success(resList);
//	}
//
//	//PC修改个人头像和密码
//	@Override
//	@Transactional
//	public GeneralResult updatePassOrImg(String userImg, String oldPass, String newPass) {
//		PubFun.check(userImg,oldPass,newPass);
//		UserInfoTbl user = SecurityUtils.getPrincipal();
//		UserInfoTbl pcUser = pcUserRepository.findById(user.getUserId()).orElseThrow(() -> new BaseException("该用户已经不存在"));
//		//校验旧密码
//		String md5 = MD5Utils.isEncryption(oldPass,pcUser.getPcSalt());
//		if(!pcUser.getPcPassword().equals(md5)) {
//			throw new BaseException("您输入的旧密码错误,请重新输入");
//		}
//		String[] encryption = MD5Utils.encryption(newPass);
//		pcUser.setPcPassword(encryption[0]);
//		pcUser.setPcSalt(encryption[1]);
//		pcUser.setUpdateUserId(pcUser.getUserId());
//		pcUser.setUpdateTime(LocalDateTime.now());
//		pcUser.setUserImg(userImg);
//		pcUserRepository.saveAndFlush(pcUser);
//		return GeneralResult.success();
//	}
//
//	//修改用户现金每场竞拍次数
//	@Transactional
//	@Override
//	public GeneralResult updRobOrderLimit(Long userId, Integer remarks) {
//		PubFun.check(userId, remarks);
//		UserInfoTbl pUser = SecurityUtils.getPrincipal();
//		if(remarks<=0) throw new BaseException("请输入大于零的数");
//		UserInfoTbl user = pcUserRepository.findByDeleteFlagFalseAndUserId(userId);
//		user.setRobOrderLimit(remarks);
//		user.setUpdateTime(LocalDateTime.now());
//		user.setUpdateUserId(pUser.getUserId());
//		pcUserRepository.saveAndFlush(user);
//		return GeneralResult.success();
//	}
//
//	//修改用户钻石每场竞拍次数
//	@Transactional
//	@Override
//	public GeneralResult updDiamondOrderLimit(Long userId, Integer remarks) {
//		PubFun.check(userId, remarks);
//		UserInfoTbl pUser = SecurityUtils.getPrincipal();
//		if(remarks<=0) throw new BaseException("请输入大于零的数");
//		UserInfoTbl user = pcUserRepository.findByDeleteFlagFalseAndUserId(userId);
//		user.setDiamondOrderLimit(remarks);
//		user.setUpdateTime(LocalDateTime.now());
//		user.setUpdateUserId(pUser.getUserId());
//		pcUserRepository.saveAndFlush(user);
//		return GeneralResult.success();
//	}
//
//	//修改用户提现额度
//	@Transactional
//	@Override
//	public GeneralResult updWithdrawalLimit(Long userId, BigDecimal remarks) {
//		PubFun.check(userId, remarks);
//		UserInfoTbl pUser = SecurityUtils.getPrincipal();
//		if(remarks.compareTo(BigDecimal.ZERO)<=0) throw new BaseException("请输入大于零的数");
//		UserInfoTbl user = pcUserRepository.findByDeleteFlagFalseAndUserId(userId);
//		user.setWithdrawalLimit(remarks);
//		user.setUpdateTime(LocalDateTime.now());
//		user.setUpdateUserId(pUser.getUserId());
//		pcUserRepository.saveAndFlush(user);
//		return GeneralResult.success();
//	}
//
//	//修改用户VIP类型
//	@Transactional
//	@Override
//	public GeneralResult updVipType(Long userId, Integer vipType, Long tagId) {
//		PubFun.check(userId, vipType);
//		UserInfoTbl pUser = SecurityUtils.getPrincipal();
//		UserInfoTbl user = pcUserRepository.findByDeleteFlagFalseAndUserId(userId);
//		if((user.getVipType()==0 || user.getVipType()==1) && vipType.compareTo(2)==0){//设置团长必须设置分馆馆长
//			PubFun.check(userId, tagId);
//			if(tagId<=0) throw new BaseException("请选择分馆");
//			if(user.getTagId().compareTo(tagId)==0) throw new BaseException("该用户已经属于该馆成员了");
//			if(user.getTagId().compareTo(tagId)!=0 && user.getTagId()>0) throw new BaseException("该用户已经属于其他馆成员了");
//			//判断这个馆是否已经有馆长了
//			Long count = pcUserRepository.countAllByDeleteFlagFalseAndTagIdAndVipType(tagId, 2);
//			if(count>0) throw new BaseException("该分馆已经有馆长了");
//			user.setTagId(tagId);
//			//所有下级团队都可以进入
//			pcUserRepository.updUserChildByPidStr(tagId,user.getUserId(),pUser.getUserId());
//			//附带的产品同时进入相应的分馆
//			pcGoodsRepository.updGoodsTagIdByPidStr(tagId,user.getUserId(),pUser.getUserId());
//		}else if(user.getVipType()==2 && ( vipType.compareTo(0)==0|| vipType.compareTo(1)==0)){//取消馆长
//			user.setTagId(0L);
//			//取消用户分馆归属
//			pcUserRepository.updUserChildByPidStr(0L,user.getUserId(),pUser.getUserId());
//			//附带的产品同时进入相应的分馆
//			pcGoodsRepository.updGoodsTagIdByPidStr(0L,user.getUserId(),pUser.getUserId());
//		}
//		user.setVipType(vipType);
//		user.setUpdateTime(LocalDateTime.now());
//		user.setUpdateUserId(pUser.getUserId());
//		pcUserRepository.saveAndFlush(user);
//		return GeneralResult.success();
//	}
//}
//
