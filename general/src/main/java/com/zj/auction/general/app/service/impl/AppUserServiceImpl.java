package com.zj.auction.general.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zj.auction.common.base.BaseServiceImpl;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.constant.SystemConfig;
import com.zj.auction.common.constant.SystemConstant;
import com.zj.auction.common.date.DateUtil;
import com.zj.auction.common.dto.PassWordDTO;
import com.zj.auction.common.dto.UserDTO;
import com.zj.auction.common.exception.ServiceException;
import com.zj.auction.common.mapper.*;
import com.zj.auction.common.model.Address;
import com.zj.auction.common.model.Role;
import com.zj.auction.common.model.User;
import com.zj.auction.common.model.UserConfig;
import com.zj.auction.common.oss.OssUpload;
import com.zj.auction.common.oss.SendMessage;
import com.zj.auction.common.util.*;
import com.zj.auction.common.vo.LoginResp;
import com.zj.auction.common.vo.PageAction;
import com.zj.auction.general.app.service.AppUserService;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.common.vo.UserVO;
import com.zj.auction.general.auth.AppTokenUtils;
import com.zj.auction.general.auth.AuthToken;
import com.zj.auction.general.shiro.JwtToken;
import com.zj.auction.general.shiro.JwtUtil;
import com.zj.auction.general.shiro.PwdTool;
import com.zj.auction.general.shiro.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * app用户服务impl
 *
 * @author 胖胖不胖
 * @date 2022/06/16
 */
@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
//@RequiredArgsConstructor(onConstructor_={@Autowired})
public class AppUserServiceImpl extends BaseServiceImpl implements AppUserService {
    // 确认收款
    private static final String REGISTER_ONLY_CACHE_KEY = "REGISTER_ONLY_CACHE_KEY";
    private static final long TMP_TOKEN_EXPIRE_TIME = 5 * 60 * 1000L; //5分钟

    private final UserMapper userMapper;
    private final UserConfigMapper userConfigMapper;
    private final AddressMapper addressMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public AppUserServiceImpl(UserMapper userMapper, UserConfigMapper userConfigMapper, AddressMapper addressMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userConfigMapper = userConfigMapper;
        this.addressMapper = addressMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }


    //查询我服务的客服
    @Override
    public Integer customerTotal() {
        AuthToken user = AppTokenUtils.getAuthToken();
        return userMapper.customerAllPage(user.getUserId());
    }


    //实名认证
    @Override
    public Boolean authIdentity(UserDTO dto) {
        System.out.println(">>>>>>>>>>>>>>>>>>>"+dto);
//        AuthToken appToken = AppTokenUtils.getAuthToken();
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        //User user = userMapper.selectByPrimaryKey(appToken.getUserId());
        user.setRealName(dto.getRealName());//真实姓名
        user.setCardNumber(dto.getCardNum());//身份证号
        UserConfig u = userConfigMapper.selectAllByUserId(user.getUserId());
        u.setFrontImage(dto.getFrontImage());//正面
        u.setReverseImage(dto.getReverseImage());//反面
        System.out.println(">>>>>>>>>>>>>"+u);
        user.setAudit(1);//：0默认，1待审核，2审核通过，3拒绝审核
        u.setApplyTime(LocalDateTime.now());//申请时间
        userMapper.updateByPrimaryKeySelective(user);
        userConfigMapper.updateByPrimaryKey(u);
        return true;
    }


    /**
     * 注册
     *
     * @param dto dto
     * @return {@link User}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User register(UserDTO dto) {
        try {
            PubFun.check(dto);
            messagesCheck(dto.getTel(), dto.getCode());// 验证码校验
            telCheck(dto.getTel());// 判断是否注册
            //查询手机号是否已经注册
            User oldUser = userMapper.findByUserName(dto.getTel());
            if (Objects.nonNull(oldUser)) {
                throw new ServiceException(514, "此手机号已经注册,请更换手机号!");
            }
            User user = new User();// 创建用户
            //String[] md5 = MD5Utils.encryption(dto.getPassWord());//密码处理
            //String salt = dto.getUserName();
            String salt = PwdTool.getRandomSalt();
            Md5Hash md5Hash = new Md5Hash(dto.getPassWord(), salt, 1024);
            user.setPassWord(md5Hash.toString());
            user.setSalt(salt);

            if (Objects.isNull(dto.getPid())) {
                PubFun.check(dto.getPUserName());
                User pidUser = userMapper.findByUserName(dto.getPUserName());
                if (Objects.isNull(pidUser)) {
                    throw new ServiceException(515, "注册失败 ,推荐人不存在");
                }
                String pidStr = "";
                if (StringUtils.isEmpty(pidUser.getPidStr())) {
                    pidStr = "," + pidUser.getUserId() + ",";
                } else {
                    pidStr = pidUser.getPidStr() + "," + pidUser.getUserId() + ",";
                }
                user.setPid(pidUser.getUserId());
                user.setpUserName(pidUser.getUserName());
                user.setLevelNum(pidUser.getLevelNum() + 1);
                user.setPidStr(pidStr);
            } else {
                User pidUser = userMapper.selectByPrimaryKey(dto.getPid());//查询该父级用户
                if (Objects.isNull(pidUser)) {
                    String totalPlatformId = SystemConfig.getTotalPlatformId() == null ? "31" : SystemConfig.getTotalPlatformId();
                    User totalPlatformUser = Optional.ofNullable(userMapper.selectByPrimaryKey(Long.parseLong(totalPlatformId))).get();//查询该父级用户
                    user.setPid(totalPlatformUser.getUserId());
                    user.setpUserName(totalPlatformUser.getUserName());
                    user.setLevelNum(2);
                    user.setPidStr("," + totalPlatformUser.getUserId() + ",");
                } else {
                    String pidStr = "";
                    if (StringUtils.isEmpty(pidUser.getPidStr())) {
                        pidStr = "," + pidUser.getUserId() + ",";
                    } else {
                        pidStr = pidUser.getPidStr() + "," + pidUser.getUserId() + ",";
                    }
                    user.setPid(pidUser.getUserId());
                    user.setpUserName(pidUser.getUserName());
                    user.setLevelNum(pidUser.getLevelNum() + 1);
                    user.setPidStr(pidStr);
                }
            }
            user.setUserName(dto.getTel());
            user.setTel(dto.getTel());
            user.setNickName("未设置昵称");
            user.setAddTime(LocalDateTime.now());
            // 保存最近一次登入时间
            user.setLoginTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            user.setUserImg(SystemConstant.USER_IMG);
            user.setStatus(0);
            user.setShareType(0);
            user.setUserType(0);//0.普通用户
            user.setAudit(2);
            user.setIsFirstLogin(1);
            user.setSubUserNum(0);
            user.setSubUserNumAll(0);
            user.setRoleShopId(0L);
            BigDecimal decimal = SystemConfig.getRegisterWithdrawalLimit() == null ? BigDecimal.valueOf(500) : new BigDecimal(SystemConfig.getRegisterWithdrawalLimit());
            user.setWithdrawalLimit(decimal);//注册送提现额度
            UserConfig config = new UserConfig();
            config.setUserId(user.getUserId());
            config.setCreateTime(new Date());
            userConfigMapper.insert(config);
            userMapper.insertSelective(user);
            User u = userMapper.findByUserName(user.getUserName());
            String codePath = getAppQrCode(u.getUserId());// 生成app二维码
            u.setShareImg(codePath);
            userMapper.updateByPrimaryKeySelective(u);
            redisTemplate.delete(AppTokenUtils.CODE_FILE + dto.getTel());
            return BeanUtils.copy(u);
        } finally {

        }
    }

    //设置/忘记密码
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean forgetPassword(PassWordDTO dto) {
        PubFun.check(dto.getTel(), dto.getCode(), dto.getPassword());
        messagesCheck(dto.getTel(), dto.getCode());
        if (dto.getPassword().length() < 6) {
            throw new RuntimeException("密码必须六位以上!");
        }
        String regex = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";
        if (!dto.getPassword().matches(regex)) {
            throw new RuntimeException("密码必须又数字和字母组成!");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getUserName, dto.getTel()).eq(User::getDeleteFlag, 0).eq(User::getUserType, 0);
        User user = userMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(user)) {
            throw new RuntimeException("该手机号还未注册");
        }
        String[] encryption = MD5Utils.encryption(dto.getPassword());
        user.setPassWord(encryption[0]);
        user.setSalt(encryption[1]);
        user.setUpdateUserId(user.getUserId());
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }

    /**
     * @param userCfg
     * @return com.duoqio.boot.business.entity.User
     * @Description 通过二维码注册会员
     * @Title registerShare
     * @Author Mao Qi
     * @Date 2019/9/22 18:30
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean registerShare(User userCfg) {
        //校验电话号码，验证码，父级id
        PubFun.check(userCfg, userCfg.getTel(), userCfg.getPassWord(), userCfg.getMemo1(), userCfg.getPid());
        //查询当前注册用户是否已注册
        this.isRegisterUser(userCfg.getTel());
        messagesCheck(userCfg.getTel(), userCfg.getMemo1());// 验证码校验
        User user = userMapper.selectByPrimaryKey(userCfg.getPid());//查询该父级用户
        if (Objects.isNull(user) || Objects.isNull(user.getUserId())) {
            //可以做成普通注册
            throw new RuntimeException("该分享人账户不存在或已删除");
        }
        //拼接所有父级                标记最多记录向上多少级
        String pidStr = "";
        if (user.getPidStr() == null) {
            pidStr = "," + user.getUserId() + ",";
        } else {
            pidStr = user.getPidStr() + "," + user.getUserId() + ",";
        }
        //密码处理
        String[] md5 = MD5Utils.encryption(userCfg.getPassWord());
        //等级+1    标记   最多多少级
        Integer levelNum = user.getLevelNum() + 1;
        //生成分享二维码
        User userCfgEntity = this.userEntity(user.getUserId(), user.getUserName()
                , pidStr, levelNum, userCfg.getTel(), md5[0], md5[1], SystemConstant.USER_IMG, SystemConstant.BACKGROUD_IMG);
        userMapper.updateByPrimaryKeySelective(userCfgEntity);
        userCfgEntity = userMapper.selectByPrimaryKey(user.getUserId());
        //获取分享二维码并保存
        userCfgEntity.setShareImg(getAppQrCode(userCfgEntity.getUserId()));
        userMapper.updateByPrimaryKeySelective(userCfgEntity);
        redisTemplate.delete(AppTokenUtils.CODE_FILE + userCfgEntity.getTel());//删除验证码
        return true;
    }

    /**
     * @param userId 用户id
     * @title: getAppQrCode
     * @description: 生成app分享二维码
     * @author: Mao Qi
     * @date: 2019年8月13日下午5:52:30
     * @return: String
     */
    private String getAppQrCode(Long userId) {
        String sharImg = "";
        // 生成二维码(内嵌LOGO)
        try {
            BufferedImage encodeLogo = QrCodeUtils.encodeLogo(
                    SystemConfig.getHostDomain() + "/PcWeb/reg?pid=" + userId,
                    "https://duoqio20180105.oss-cn-beijing.aliyuncs.com/fy/2022/04/28/14/29/b85f72ca-0b65-4ee9-8a20-fdcdf4887fef.png",
                    true);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(encodeLogo, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            Map<String, Object> ossUploadBack = ossUploadBack(UUID.randomUUID().toString() + ".png", is);
            if (ossUploadBack.get("url").toString().trim().length() > 0) {
                sharImg = ossUploadBack.get("url").toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("分享二维码生成失败");
        }
        return sharImg;
    }

    public static Map<String, Object> ossUploadBack(String newFileName, InputStream input) throws Exception {
        return OssUpload.putObject(1752718375L, newFileName, input, (long) input.available());
    }

    /**
     * @param userName
     * @return void
     * @Description 校验账号是否注册
     * @Title isRegisterUser
     * @Author Mao Qi
     * @Date 2019/10/23 22:46
     */
    private void isRegisterUser(String userName) {
        User user = userMapper.findByUserName(userName);
        if (!Objects.isNull(user)) {
            throw new RuntimeException("该账号已注册");
        }
    }

    /**
     * @param tel
     * @param messages
     * @return void
     * @Description 校验验证码
     * @Title messagesCheck
     * @Author Mao Qi
     * @Date 2020/4/13 20:04
     */
    private void messagesCheck(String tel, String messages) {
        // 验证码校验
        String messagesCheck = (String) redisTemplate.opsForValue().get(AppTokenUtils.CODE_FILE + tel);// 验证码校验

        if (Objects.isNull(messagesCheck) || Objects.isNull(messages)) {
            System.out.println("====================" + messagesCheck);
            throw new ServiceException(512, "验证码失效!");
        }
        if (!messages.equals(messagesCheck)) {
            throw new ServiceException(SystemConstant.ERROR_MESSAGE_CODE, SystemConstant.ERROR_MESSAGE);
        }
    }


    /**
     * @param userId 当前
     * @param pid    上级
     * @return java.lang.Boolean
     * @Description 分享下单时绑定推荐人关系
     * @Title bindRelationShare
     * @Author Mao Qi
     * @Date 2019/10/24 21:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindRelationShare(Long userId, Long pid) {
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("用户ID为空");
        }
        if (super.baseCheck(pid, Objects::isNull)) {
            throw new RuntimeException("分享人ID为空");
        }
        //查询该父级用户
        User puser = userMapper.selectByPrimaryKey(pid);
        if (Objects.isNull(puser) || Objects.isNull(puser.getUserId())) {
            throw new RuntimeException("该分享人账户不存在或已删除");
        }

        //查询当前用户
        User oldUser = userMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(oldUser) || Objects.isNull(oldUser.getUserId())) {
            throw new RuntimeException("当前用户不存在或已删除");
        }
        //绑定关系
        oldUser.setPid(puser.getUserId());
        oldUser.setpUserName(puser.getUserName());
        if (puser.getPidStr() == null) {
            oldUser.setPidStr("," + puser.getUserId() + ",");
        } else {
            oldUser.setPidStr(puser.getPidStr() + "," + puser.getUserId() + ",");
        }
        oldUser.setShareType(1);//推荐方式，默认0,1通过二维码分享推荐
        oldUser.setLevelNum(puser.getLevelNum() + 1);
        userMapper.updateByPrimaryKeySelective(oldUser);
        System.out.println("进入绑定上下级关系userid=" + userId + "  pid=" + pid);
        return true;
    }

    //手机号校验
    @Override
    public void telCheck(String tel) {
        if (!super.baseCheck(tel, StringUtils::hasText)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, "数据非法");
        }
        List<User> findByTel = userMapper.findByTel(tel);
        if (!findByTel.isEmpty()) {
            throw new ServiceException(SystemConstant.ALREADY_REGISTERED_CODE, SystemConstant.ALREADY_REGISTERED);
        }
    }

    /**

     * @Description 登录
     * @Title login
     */
    @Override
    public LoginResp login(UserDTO dto) {
        LoginResp data = new LoginResp();
        PubFun.check(dto.getUserName());
        User user = userMapper.findByUserName(dto.getUserName());//查询用户
        if (Objects.isNull(user)) {//不存在
            throw new ServiceException(516, "手机号未注册,请先注册!");
        } else {
            if (StringUtils.isEmpty(dto.getPassWord())) {//验证码登录
                PubFun.check(dto.getCode());//数据校验
                //校验手机验证
                messagesCheck(dto.getUserName(), dto.getCode());
            } else {//密码登录
                PubFun.check(dto.getPassWord());//数据校验
                //校验密码
                String salt = user.getSalt();
                Md5Hash md5Hash = new Md5Hash(dto.getPassWord(), salt, 1024);

                System.out.println("md5Hash---->" + md5Hash);
                String userId = String.valueOf(user.getUserId());
                long expressTime = System.currentTimeMillis() + TMP_TOKEN_EXPIRE_TIME;
                String md5 = MD5Utils.isEncryption(userId, String.valueOf(expressTime));
                String accessToken = JwtUtil.getTmpJwtToken(userId, md5, expressTime);
                //生成token字符串
                String token = JwtUtil.getJwtToken(dto.getUserName(), md5Hash.toHex());   //toHex转换成16进制，32为字符
                //toHex转换成16进制，32为字符
                JwtToken jwtToken = new JwtToken(token);
                data.setToken(token);
                data.setUserId(user.getUserId());
                data.setUserInfo(user);
                data.setAccessToken(accessToken);
                data.setMsg("成功!");
                //拿到Subject对象
                Subject subject = SecurityUtils.getSubject();
                //进行认证
                try {
                    subject.login(jwtToken);
                    // return new ResultTemplate().Ok("200","成功","");
                    System.out.println("成功");
                } catch (UnknownAccountException e) {
                    // return new ResultTemplate().Ok("500","无效用户，用户不存在","");
                    System.out.println("无效用户，用户不存在");
                    e.printStackTrace();
                } catch (IncorrectCredentialsException e) {
                    // return new ResultTemplate().Ok("500","密码错误","");
                    System.out.println("密码错误");
                    e.printStackTrace();
                } catch (ExpiredCredentialsException e) {
                    //return new ResultTemplate().Ok("500","token过期","");
                    System.out.println("token过期");
                    e.printStackTrace();
                } finally {

                }

                if (Objects.isNull(user.getPassWord())) {
                    throw new ServiceException(517, "您未设置密码,请用短信验证码登录!");
                }
                if (!user.getPassWord().equals(md5Hash.toString())) {
                    throw new ServiceException(518, "您输入的密码错误,请重新输入!");
                }
            }
            if (user.getStatus() == 1) {
                throw new ServiceException(519, "用户已被冻结,请联系管理员!");
            }
            if (user.getAudit() == 1) {
                throw new ServiceException(521, "该账号还在审核中!");
            }
            if (user.getAudit() == 3) {
                throw new ServiceException(522, "该账号未通过审核," + user.getAuditExplain());
            }
            // 保存最近一次登入时间
            user.setLoginTime(LocalDateTime.now());
            userMapper.updateByPrimaryKeySelective(user);
        }
        //redisTemplate.delete(AppTokenUtils.CODE_FILE + userName);
        // return getAppLoginResp(user.getUserId(), user);
        return data;
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.findByUserName(name);
    }


//    //生成包括token的返回登录数据
//    private LoginResp getAppLoginResp(Long userId, User userInfo) {
//        //生成请求token
//        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
//        json.put("userId", userId);
//        //生成token
//        String subject = json.toJSONString();
//        String token = AppTokenUtils.createToken(userId.toString().trim(), subject, 30 * 24 * 60 * 60 * 1000L);
//        //设置登录信息
//        LoginResp data = new LoginResp();
//        data.setUserId(userId);
//        data.setToken(token);
//        data.setUserInfo(BeanUtils.copy(userInfo));
//        //敏感信息不返回给前端，加入缓存2天方便解密时使用
//        AuthToken appToken = new AuthToken(userId, token);
//        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, userId), JSON.toJSONString(appToken), 60 * 60 * 24 * 30L);
//        return data;
//    }

    /**
     * @param
     * @return java.lang.Boolean
     * @Description 发送短信验证码
     * @Title sendMessages
     * @Author Mao Qi
     * @Date 2019/9/8 15:35
     */
    @Override
    public Map<String, Object> sendMessages(HttpServletRequest request,String tel) {
        if (!StringUtils.hasText(tel)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, SystemConstant.DATA_ILLEGALITY);
        }
        Function<String, Map<String, Object>> deal = param -> {
            Map<String, Object> map = sendMessages("1755231367", tel, PubFun.generateRandomNumbersMax10(4), request);
            return map;
        };
        return super.base(tel, deal);
    }

    public Map<String, Object> sendMessages(String template, String phone, String code, HttpServletRequest request) {
        Map<String, Object> sendMessage = SendMessage.sendMessage(template, phone, code, IPUtils.getRemoteAddr(request));
        redisTemplate.opsForValue().set(AppTokenUtils.CODE_FILE + phone, sendMessage.get("code").toString().trim(), 5 * 60, TimeUnit.SECONDS);
        return sendMessage;
    }


    /**
     * @param userCfg 用户信息
     * @return com.duoqio.entity.custom.UserResp
     * @Description 修改昵称
     * @Title updateNickName
     * @Author Mao Qi
     * @Date 2019/9/22 18:50
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User updateNickName(User userCfg) {
        AuthToken appToken = AppTokenUtils.getAuthToken();
        PubFun.check(userCfg.getNickName());
        if (userCfg.getNickName().length() > 16) {
            throw new ServiceException(522, "昵称名过长,请重新设置!");
        }
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(appToken.getUserId())).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
        user.setNickName(userCfg.getNickName());
        user.setUpdateUserId(appToken.getUserId());
        userMapper.updateByPrimaryKeySelective(user);
        appToken.setUser(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(appToken));
        return BeanUtils.copy(user);
    }

    //修改常用手机号
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User updateCommonTel(String commonTel,String commonName) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PubFun.check(commonTel, commonName);
        if (commonTel.trim().length() != 11) {
            throw new ServiceException(523, "请输入正确手机号");
        }
        user.setCommonTel(commonTel);
        user.setCommonName(commonName);
        user.setUpdateUserId(user.getUserId());
        userMapper.updateByPrimaryKeySelective(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(user));
        return BeanUtils.copy(user);
    }

    public User selectUserGiveCurrService(Long userId) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(userId)).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
    }

    /**
     * @param userCfg 用户信息
     * @return java.lang.Boolean
     * @Description 修改昵称
     * @Title updateRealName
     * @Author Mao Qi
     * @Date 2019/10/24 21:31
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateRealName(User userCfg) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (super.baseCheck(userCfg, param -> Objects.isNull(param) || !StringUtils.hasText(param.getRealName()))) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, SystemConstant.DATA_ILLEGALITY);
        }
        user.setRealName(userCfg.getRealName());
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }

    /**
     * @param
     * @return java.util.List<com.duoqio.boot.business.entity.AddrInfoTbl>
     * @Description 查询所有收获地址
     * @Title listAddr
     * @Author Mao Qi
     * @Date 2019/10/24 21:31
     */
    @Override
    public List<Address> listAddr() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return addressMapper.findByUserId(user.getUserId());
    }

    /**
     * @param
     * @return com.duoqio.boot.business.entity.AddrInfoTbl
     * @Description 获取用户默认收获地址
     * @Title getDefaultAddrByUserId
     * @Author Mao Qi
     * @Date 2019/10/24 21:31
     */
    @Override
    public Address getDefaultAddrByUserId() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return addressMapper.findByDefault(user.getUserId());
    }


    /**
     * 通过id获取addr
     *
     * @return {@link Address}
     */
    @Override
    public Address getAddrById(UserDTO dto) {
        if (super.baseCheck(dto.getAddrId(), Objects::isNull)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, SystemConstant.DATA_ILLEGALITY);
        }
        return Optional.ofNullable(addressMapper.selectByPrimaryKey(dto.getAddrId())).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
    }


    /**
     * @return java.lang.Boolean
     * @Description 修改默认地址
     * @Title updateDefaultAddrById
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateDefaultAddrById(Long addrId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PubFun.check(addrId);
        Address oldAddr = Optional.ofNullable(addressMapper.selectByPrimaryKey(addrId)).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
        oldAddr.setDefaultFlag(1);
        addressMapper.updateByPrimaryKey(oldAddr);
        //	将非当前地址id默认的取消
        addressMapper.updateAddrDefaultByIdNot(user.getUserId(), oldAddr.getAddrId());
        return true;
    }

    /**

     * @Description 删除收获地址
     * @Title deleteAddr
     */
    @Transactional
    @Override
    public Boolean deleteAddr(Long addrId) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        PubFun.check(addrId);
        Address oldAddr = Optional.ofNullable(addressMapper.selectByPrimaryKey(addrId)).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
        oldAddr.setDeleteFlag(1);
        addressMapper.updateByPrimaryKey(oldAddr);
        return true;
    }

    //添加收获地址
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addAddr(String name, String tel1, String addr2, String city, String district, String province,
                           String cityId, String districtId, String provinceId, Integer defaultFlag) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        PubFun.check(name, tel1, addr2, defaultFlag);
        Address addr = new Address();
        List<Address> addrs = addressMapper.findByUserId(user.getUserId());
        if (addrs.size() == 0) {
            defaultFlag = 1;
        }
        if (defaultFlag == 1) {//设置为默认
            //	将当前用户其它设置的默认取消
            addressMapper.updateAddrDefaultFlag(user.getUserId());
            addr.setDefaultFlag(1);
        }
		/*
		String add = AddressUntils.getAdd(longitude, latitude); //更具经纬度获取
		JSONObject jsonObject = JSONObject.fromObject(add);
		JSONObject jsonArray1 = JSONObject.fromObject(jsonObject.getString("result"));
		JSONObject jsonArray2 = JSONObject.fromObject(jsonArray1.getString("addressComponent"));
		String province = jsonArray2.getString("province");
		String city = jsonArray2.getString("city");
		String district = jsonArray2.getString("district");
		String adcode = jsonArray2.getString("adcode") .trim().equalsIgnoreCase("0")?"000000":jsonArray2.getString("adcode") .trim();
		String provinceId = adcode.substring(0, 2).trim()+"0000";
		String cityId = adcode.substring(0, 4).trim()+"00";*/
        addr.setCity(city);
        addr.setCounty(district);
        addr.setProvince(province);
        addr.setUserId(user.getUserId());
        //addr.setAddr1(province + "," + city + "," + district);
        addr.setAddr1(province + city + district);
        addr.setAddr2(addr2);
        //addr.setAddr3(addr3);
        addr.setProvinceId(Integer.parseInt(provinceId.trim()));
        addr.setCityId(Integer.parseInt(cityId.trim()));
        addr.setCountyId(Integer.parseInt(districtId.trim()));
        addr.setName(name);
        addr.setTel1(tel1);
        addr.setStatus(0);
        addr.setAddUserid(user.getUserId());
        addressMapper.insert(addr);
        return true;
    }

    //修改收获地址
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Boolean updateAddr(Long addrId, String name, String tel1, String addr2, String city, String district, String province,
                              String cityId, String districtId, String provinceId, Integer defaultFlag) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        PubFun.check(addrId, name, tel1, addr2, defaultFlag);
        Address oldAddr = Optional.ofNullable(addressMapper.selectByPrimaryKey(addrId)).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
        if (oldAddr.getDefaultFlag() != null && oldAddr.getDefaultFlag() == 1) {//	原本是默认
            if (defaultFlag == 0) {//	现在取消默认
                oldAddr.setDefaultFlag(0);
            }
        } else {// 原本不是默认
            if (defaultFlag == 1) {//	现在默认
                //	将其它改为非默认
                addressMapper.updateAddrDefaultFlag(user.getUserId());
                oldAddr.setDefaultFlag(1);
            }
        }
        oldAddr.setCity(city);
        oldAddr.setCounty(district);
        oldAddr.setProvince(province);
        oldAddr.setAddr1(province + city + district);
        oldAddr.setAddr2(addr2);
        oldAddr.setProvinceId(Integer.parseInt(provinceId.trim()));
        oldAddr.setCityId(Integer.parseInt(cityId.trim()));
        oldAddr.setCountyId(Integer.parseInt(districtId.trim()));
        oldAddr.setName(name);
        oldAddr.setTel1(tel1);
        oldAddr.setUpdateTime(LocalDateTime.now());
        addressMapper.updateByPrimaryKey(oldAddr);
        return true;
    }

    /**
     * @return java.lang.Boolean
     * @Description 修改密码
     * @Title updatePassWord
     * @Author Mao Qi
     * @Date 2019/9/22 18:52
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updatePassWord(PassWordDTO dto) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (!StringUtils.hasText(dto.getOldPassWord()) || !StringUtils.hasText(dto.getNewPassWord())) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE, SystemConstant.DATA_ILLEGALITY);
        }
        // 严格验证登录密码
        User old = Optional.ofNullable(userMapper.selectByPrimaryKey(user.getUserId())).get();
        //密码处理
        String md5 = MD5Utils.isEncryption(dto.getOldPassWord(), old.getSalt());
        if (!old.getPassWord().equals(md5)) {
            throw new ServiceException(524, "您输入的原密码错误,请重新输入!");
        }
        String[] encryption = MD5Utils.encryption(dto.getNewPassWord());
        old.setPassWord(encryption[0]);
        userMapper.updateByPrimaryKeySelective(old);
        return true;
    }

    //    //修改手机号/用户名
//    @Override
//    @Transactional
//    public User updateUserName(String userName, String code) {
//        AuthToken authToken = AppTokenUtils.getAuthToken();
//        // 数据校验
//        PubFun.check(userName, code);
//        //校验手机验证
//        messagesCheck(userName, code);
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
//        wrapper.eq(User::getUserName,userName).eq(User::getUserType,0).eq(User::getDeleteFlag,0);
//        Long count = userMapper.selectCount(wrapper);
//        User currentUser = userMapper.selectByPrimaryKey(authToken.getUserId());
//        if (count > 0) {//存在
//            throw new ServiceException(525,"手机号已注册,请更换手机号!");
//        } else {
//            currentUser.setUserName(userName);
//            currentUser.setTel(userName);
//            userMapper.updateByPrimaryKeySelective(currentUser);
//        }
//        redisTemplate.delete(AppTokenUtils.CODE_FILE + userName);//删除验证码
//        authToken.setUser(currentUser);
//        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, authToken.getUserId()), JSON.toJSONString(authToken));
//        return BeanUtils.copy(currentUser);
//    }
    //修改手机号/用户名
    @Override
    @Transactional
    public User updateUserName(UserDTO dto) {
//        AuthToken authToken = AppTokenUtils.getAuthToken();
        // 数据校验
        PubFun.check(dto.getUserName(), dto.getCode());
        //校验手机验证
        messagesCheck(dto.getUserName(), dto.getCode());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getUserName, dto.getNewUserName()).eq(User::getUserType, 0).eq(User::getDeleteFlag, 0);
        Long count = userMapper.selectCount(wrapper);
        User currentUser = userMapper.findByUserName(dto.getUserName());
        if (count > 0) {//存在
            throw new ServiceException(525, "手机号已注册,请更换手机号!");
        } else {
            currentUser.setUserName(dto.getNewUserName());
            currentUser.setTel(dto.getNewUserName());
            userMapper.updateByPrimaryKeySelective(currentUser);
        }
        redisTemplate.delete(AppTokenUtils.CODE_FILE + dto.getUserName());//删除验证码
//        authToken.setUser(currentUser);
//        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, currentUser.getUserId()));
        return BeanUtils.copy(currentUser);
    }

    //设置/忘记密码
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addPassword(PassWordDTO dto) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        PubFun.check(user.getTel(), dto.getCode(), dto.getPassword());
        messagesCheck(user.getTel(), dto.getCode());
        if (dto.getPassword().length() < 8) {
            throw new ServiceException(526, "密码必须八位以上!");
        }
        String regex = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";
        if (!dto.getPassword().matches(regex)) {
            throw new ServiceException(527, "密码必须又数字和字母组成!");
        }
        String[] encryption = MD5Utils.encryption(dto.getPassword());
        user.setPassWord(encryption[0]);
        user.setSalt(encryption[1]);
        userMapper.updateByPrimaryKeySelective(user);
//        if (Objects.nonNull(authToken))
//            authToken.setUser(user);
//        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(authToken));
        return true;
    }


    //是否有交易密码
    @Override
    public boolean hasPayPassword() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        User data = userMapper.findByUserName(user.getUserName());
        if (null == data) {
            return false;
        }
        return data.getPayPassword() != null;
    }

    //交易密码验证
    @Override
    public boolean isPayPassword(String payPassword ) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        return user.getPayPassword().equals(MD5Utils.isEncryption(payPassword, user.getUserId().toString()));
    }

    //设置支付密码
    @Override
    public boolean addPayPassword(String payPassword) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user.setPayPassword(MD5Utils.isEncryption(payPassword, user.getUserId().toString()));
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }


    /**
     * @param
     * @return com.duoqio.boot.business.entity.User
     * @Description 获取用户信息
     * @Title findByUserCfg
     * @Author Mao Qi
     * @Date 2019/10/24 21:34
     */
    @Override
    public User findByUserCfg() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        return BeanUtils.copy(user);
    }

    /**
     * @param
     * @return java.lang.Boolean
     * @Description 退出登录
     * @Title exit
     * @Author Mao Qi
     * @Date 2019/9/22 18:53
     */
    @Override
    public Boolean exit() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (!Objects.isNull(user.getUserId())) {
            SecurityUtils.logout();
            redisTemplate.delete(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()));
        }
        return true;
    }

    /**
     * @param userImg
     * @return com.duoqio.boot.business.entity.User
     * @Description 修改用户头像
     * @Title updateUserImg
     * @Author Mao Qi
     * @Date 2019/9/11 16:58
     */
    @Override
    @Transactional
    public User updateUserImg(String userImg) {
        PubFun.check(userImg);
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        user.setShareImg(getAppQrCode(user.getUserId()));
        user.setUserImg(userImg);
        userMapper.updateByPrimaryKeySelective(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(user));
        return BeanUtils.copy(user);
    }

    /**
     * @param userCfg
     * @return com.duoqio.boot.business.entity.User
     * @Description 根据用户名查找用户
     * @Title findByUserName
     * @Author Mao Qi
     * @Date 2019/10/24 21:34
     */
    @Override
    public User findByUserName(User userCfg) {
        if (super.baseCheck(userCfg, param -> Objects.isNull(param) || Objects.isNull(param.getUserName()))) {
            throw new RuntimeException("未获取到账号信息");
        }
        User user = userMapper.findByUserName(userCfg.getUserName());
        if (Objects.isNull(user) || Objects.isNull(user.getUserId())) {
            throw new RuntimeException("未查询到用户信息");
        }
		/*if (!authToken.getUserId().equals(user.getPid())) {
			throw new RuntimeException("该账号不是您的下级");
		}*/
        return BeanUtils.copy(user);
    }

    /**
     * @Description 分页查询下级用户
     * @Title findCustomerByUserId
     */
    @Override
    public PageInfo<User> findCustomerByUserId(PageAction pageAction) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();

        System.out.println("user----->"+user);
        PageHelper.startPage(pageAction.getCurrentPage(), pageAction.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getPid, user.getUserId()).eq(User::getDeleteFlag, 0).eq(User::getUserType, 0);
        List<User> pidUserList = userMapper.selectList(wrapper);
        return new PageInfo<>(pidUserList);
    }

    //查询直接上级
    @Override
    public GeneralResult parentUser() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        User pUser = userMapper.selectByPrimaryKey(user.getPid());
        return GeneralResult.success(pUser == null ? null : BeanUtils.copy(pUser));
    }

    //注销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        System.out.println("delUser--->"+user);

        user.setDeleteFlag(1);
        user.setUpdateUserId(user.getUserId());
        userMapper.updateByPrimaryKeySelective(user);
        //删除用户评论
        //清空店铺管理员
        return true;
    }


    //添加或者修改支付宝账户
    @Override
    @Transactional
    public User addOrUpdateAliNum(UserDTO dto) {
        PubFun.check(dto.getRealName(), dto.getAlipayNum());
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        user.setAlipayNum(dto.getAlipayNum());//支付宝账户
        user.setRealName(dto.getRealName());//真实姓名
        user.setUpdateUserId(user.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateByPrimaryKeySelective(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(user));
        return BeanUtils.copy(user);
    }


    /**
     * @param userId
     * @param upLevel
     * @return java.util.List<com.duoqio.boot.business.entity.User>
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
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(userId)).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
        String pidStr = user.getPidStr();
        if (pidStr != null && !"".equals(pidStr)) {
            pidStr = pidStr.substring(1, pidStr.length() - 1).replace(",,", ",");
            List<Long> collect = null;
            if (upLevel == -1) {//查询所有上级
                collect = Arrays.asList(pidStr.split(",")).stream().map(m -> Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            } else {//查询限制级别的所有上级
                collect = Arrays.asList(pidStr.split(",")).stream().map(m -> Long.parseLong(m.trim())).sorted(Comparator.reverseOrder()).limit(upLevel).collect(Collectors.toList());
            }
            if (!collect.isEmpty()) {
                LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
                wrapper.in(User::getUserId, collect).orderByDesc(User::getLevelNum);
            }
        }
        return userList;
    }

    /**
     * @param userId
     * @return java.lang.Boolean
     * @Description 校验用户
     * @Title checkUserByChildren
     * @Author Mao Qi
     * @Date 2019/10/24 21:35
     */
    @Override
    public Boolean checkUserByChildren(Long userId) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("未获取到用户ID");
        }
        if (!(user.getUserId()).equals(user.getPid())) {
            throw new RuntimeException("该账号不是您的下级");
        }
        return true;
    }

    /**
     * @param userId
     * @return java.lang.Boolean
     * @Description 修改用户等级
     * @Title updateUserType
     * @Author Mao Qi
     * @Date 2019/10/24 21:36
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateUserType(Long userId) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("未获取到用户ID");
        }
        user.setUserType(2);//将用户等级修改为店主
        user.setAudit(2);//将审核状态改为审核通过
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }

    /**
     * @param list
     * @param resultMap
     * @param pid
     * @param pidStr
     * @param level
     * @return java.util.HashMap<java.lang.String, java.lang.Object>
     * @Description 查询父级id
     * @Title findParentId
     * @Author Mao Qi
     * @Date 2019/10/24 21:36
     */
    @Override
    public HashMap<String, Object> findParentId(List<Map<String, Object>> list, HashMap<String, Object> resultMap, Long pid, String pidStr, Integer level) {
        //根据pid查询父级用户
        Optional<Map<String, Object>> collect = list.parallelStream().filter(f -> pid.equals(f.get("Id"))).findAny();
        if (collect.isPresent()) {
            Map<String, Object> map = collect.get();
            if (StringUtils.hasText(pidStr)) {
                pidStr = "," + pid + "," + pidStr;
            } else {
                pidStr = "," + pid + ",";
            }
            //等级+1
            level++;
            resultMap.put("pidStr", pidStr);
            resultMap.put("level", level);
            //当前用户是否有父级
            Long ppuid = Long.parseLong(map.get("UserId1").toString().trim());
            if (ppuid != null && ppuid != 0) {
                findParentId(list, resultMap, ppuid, pidStr, level);
            }
        }
        return resultMap;
    }




    /*----------------------------------微信------------------------------------*/

    //根据微信唯一表示查找用户
    @Override
    public User findByWxUnionId(String wxUnionId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getWxUnionId, wxUnionId).eq(User::getDeleteFlag, 0);
        return userMapper.selectOne(wrapper);
    }


    //添加或者修改支付宝或者微信收款账户  type 1 支付宝 2 微信
    @Transactional
    @Override
    public User addOrUpdateAliOrWx(String tel, String name, String account, Integer type) {
        PubFun.check(account, type);
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (type == 1) {//支付宝
            PubFun.check(account, name);
            user.setAliName(name);
            user.setAliAccount(account);
        } else {
            PubFun.check(account, tel);
            user.setWxTel(tel);
            user.setWxAccount(account);
        }
        userMapper.updateByPrimaryKeySelective(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(user));
        return BeanUtils.copy(user);
    }


    //分页查询我的直接和间接下级以及统计
    @Override
    public GeneralResult findCustomerAndStatistics(PageRequest pageRequest) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> map = new HashMap<>();
//		List<UserResp> res = new ArrayList<>();
//		List<UserResp> res1 = new ArrayList<>();
//		List<UserResp> res2 = new ArrayList<>();
        //所有一级二级
        Page<User> customerList = userMapper.findFirstAndSecondByUserId(user.getUserId(), user.getLevelNum() + 1, user.getLevelNum() + 2, pageRequest);
        if (customerList.getContent().size() > 0) {
            for (User s : customerList.getContent()) {
                Long aLong = userMapper.countUserChildByUserId(s.getUserId());
                s.setPassWord("");
                s.setSalt("");
                s.setSubordinateNum(aLong);//下一级数量
                s.setSubordinateLevel(s.getLevelNum() - user.getLevelNum());//下级等级
            }
        }
        map.put("page", customerList);
        map.put("total", userMapper.countUserChildByUserId(user.getUserId()));
        return GeneralResult.success(map);
    }

    /**
     * 循环截取某页列表进行分页
     *
     * @param dataList   分页数据
     * @param pageSize   页面大小
     * @param pageNumber 当前页面
     */
    public static List<UserVO> page(List<UserVO> dataList, int pageSize, int pageNumber) {
        List<UserVO> currentPageList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < dataList.size() - currIdx; i++) {
                UserVO data = dataList.get(currIdx + i);
                currentPageList.add(data);
            }
        }
        return currentPageList;
    }


    /**
     * 查询实名认证信息
     */
    @Override
    public GeneralResult findRealAutheInfo() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        return GeneralResult.success(user);
    }

    /**
     * 执行 - 实名信息认证
     */
    @Override
    public GeneralResult runRealAutheInfo(UserDTO dto) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        JSONObject jsonObject = CardCheckUtils.checkUserCard(dto.getRealName(), dto.getCardNum());
        if (jsonObject != null) {
            Boolean isok = jsonObject.getBoolean("isok");
            if (isok) {
                User one = userMapper.selectByPrimaryKey(user.getUserId());
                one.setRealName(dto.getRealName());
                one.setCardNumber(dto.getCardNum());
                userMapper.updateByPrimaryKey(one);
                return GeneralResult.success(one);
            }

        }
        return GeneralResult.failure(-1, "身份认证失败");
    }

    //根据是否是新用户提前五秒进入
    @Override
    public Boolean whetherNewUser(String time) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        LocalTime scheduleTime = DateUtil.stringToLocalTime(time, "HH:mm");//规定的开始时间
        int newUserDay = SystemConfig.getNewUserDay() == null ? 30 : Integer.parseInt(SystemConfig.getNewUserDay());
        LocalDateTime addTime = user.getAddTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(addTime, now);
        if (duration.toDays() >= 0 && duration.toDays() <= newUserDay) {//如果是新用户
            //判断是否五秒
            int userEnterInAdvance = SystemConfig.getUserEnterInAdvance() == null ? 5 : Integer.parseInt(SystemConfig.getUserEnterInAdvance());
            LocalTime localTime = now.toLocalTime();
            long millis = Duration.between(localTime, scheduleTime).toMillis();//毫秒
            return millis <= userEnterInAdvance * 1000;
        } else {
            return false;
        }
    }

    @Override
    public LoginResp refreshToken() {
        return null;
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> geRole() {
        return roleMapper.selectList(new QueryWrapper<Role>());
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Override
    public int addRole(Role role) {
        return roleMapper.insert(role);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 查询当前用户角色
     * @return
     */
    @Override
    public List<String> getUserRole() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        return userRoleMapper.selectRolesByUserId(user.getUserId());
    }

    /**
     * 根据用户查询角色
     * @return
     */
    @Override
    public List<String> getRoleByUser(Long userId) {
        return userRoleMapper.selectRolesByUserId(userId);
    }


}
