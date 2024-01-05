package com.zj.auction.general.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zj.auction.common.base.BaseServiceImpl;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.constant.SystemConfig;
import com.zj.auction.common.constant.SystemConstant;
import com.zj.auction.common.date.DateUtil;
import com.zj.auction.common.exception.ServiceException;
import com.zj.auction.common.mapper.AddressMapper;
import com.zj.auction.common.mapper.UserConfigMapper;
import com.zj.auction.common.mapper.UserMapper;
import com.zj.auction.common.model.Address;
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
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
//@RequiredArgsConstructor(onConstructor_={@Autowired})
public class AppUserServiceImpl extends BaseServiceImpl implements AppUserService {
    // 确认收款
    private static final String REGISTER_ONLY_CACHE_KEY = "REGISTER_ONLY_CACHE_KEY";

    private final UserMapper userMapper;
    private final UserConfigMapper userConfigMapper;
    private final AddressMapper addressMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public AppUserServiceImpl(UserMapper userMapper, UserConfigMapper userConfigMapper, AddressMapper addressMapper) {
        this.userMapper = userMapper;
        this.userConfigMapper = userConfigMapper;
        this.addressMapper = addressMapper;
    }


    //查询我服务的客服
    @Override
    public Integer customerTotal() {
        AuthToken user = AppTokenUtils.getAuthToken();
        return userMapper.customerAllPage(user.getUserId());
    }


    //实名认证
    public Boolean authIdentity(String realName, String cardNum, String frontImage, String reverseImage) {
        AuthToken appToken = AppTokenUtils.getAuthToken();
        User user = userMapper.selectByPrimaryKey(appToken.getUserId());
        user.setRealName(realName);//真实姓名
        user.setCardNumber(cardNum);//身份证号
        UserConfig u = userConfigMapper.selectAllByUserId(user.getUserId());
        u.setFrontImage(frontImage);//正面
        u.setReverseImage(reverseImage);//反面
        user.setAudit(1);//：0默认，1待审核，2审核通过，3拒绝审核
        u.setApplyTime(LocalDateTime.now());//申请时间
        userMapper.updateById(user);
        userConfigMapper.updateById(u);
        return true;
    }


    /**
     * @param tel  电话
     * @param password 密码
     * @param code 短信验证码
     * @return com.duoqio.boot.business.entity.User
     * @Description 用户注册
     * @Title register
     * @Author Mao Qi
     * @Date 2019/9/11 13:14
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User register(String tel, String password, String code, Long pid, String pUsername) {
        try {
            PubFun.check(tel, code, password);
            messagesCheck(tel, code);// 验证码校验
            telCheck(tel);// 判断是否注册
            //查询手机号是否已经注册
            User oldUser = userMapper.findByUserName(tel);
            if (Objects.nonNull(oldUser)) throw new ServiceException(514, "此手机号已经注册,请更换手机号!");
            User user = new User();// 创建用户
            String[] md5 = MD5Utils.encryption(password);//密码处理
            user.setPassWord(md5[0]);
            user.setSalt(md5[1]);

            if (Objects.isNull(pid)) {
                PubFun.check(pUsername);
                User pidUser = userMapper.findByUserName(pUsername);
                if (Objects.isNull(pidUser)) throw new ServiceException(515, "注册失败 ,推荐人不存在");
                String pidStr = "";
                if (StringUtils.isEmpty(pidUser.getPidStr())) {
                    pidStr = "," + pidUser.getUserId() + ",";
                } else {
                    pidStr = pidUser.getPidStr() + "," + pidUser.getUserId() + ",";
                }
                user.setPid(pidUser.getUserId());
                user.setPUserName(pidUser.getUserName());
                user.setLevelNum(pidUser.getLevelNum() + 1);
                user.setPidStr(pidStr);
            } else {
                User pidUser = userMapper.selectByPrimaryKey(pid);//查询该父级用户
                if (Objects.isNull(pidUser)) {
                    String totalPlatformId = SystemConfig.getTotalPlatformId() == null ? "31" : SystemConfig.getTotalPlatformId();
                    User totalPlatformUser = Optional.ofNullable(userMapper.selectByPrimaryKey(Long.parseLong(totalPlatformId))).get();//查询该父级用户
                    user.setPid(totalPlatformUser.getUserId());
                    user.setPUserName(totalPlatformUser.getUserName());
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
                    user.setPUserName(pidUser.getUserName());
                    user.setLevelNum(pidUser.getLevelNum() + 1);
                    user.setPidStr(pidStr);
                }
            }
            user.setUserName(tel);
            user.setTel(tel);
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
            userMapper.insertSelective(user);
            User u = userMapper.findByUserName(user.getUserName());
            String codePath = getAppQrCode(u.getUserId());// 生成app二维码
            u.setShareImg(codePath);
            userMapper.updateByPrimaryKeySelective(u);
            redisTemplate.delete(AppTokenUtils.CODE_FILE + tel);
            return BeanUtils.copy(u);
        } finally {

        }
    }

    //设置/忘记密码
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean forgetPassword(String tel, String code, String password ) {
        PubFun.check(tel, code, password);
        messagesCheck(tel, code);
        if (password.length() < 6) throw new RuntimeException("密码必须六位以上!");
        String regex = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";
        if (!password.matches(regex)) throw new RuntimeException("密码必须又数字和字母组成!");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getUserName,tel).eq(User::getDeleteFlag,0).eq(User::getUserType,0);
        User user = userMapper.selectOne(wrapper);
        if(ObjectUtils.isEmpty(user)) throw new RuntimeException("该手机号还未注册");
        String[] encryption = MD5Utils.encryption(password);
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
    private  void messagesCheck(String tel, String messages) {
        // 验证码校验
        String messagesCheck = (String) redisTemplate.opsForValue().get(AppTokenUtils.CODE_FILE + tel);// 验证码校验

        if (Objects.isNull(messagesCheck) || Objects.isNull(messages)) {
            throw new ServiceException(512,"验证码失效!");
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
        oldUser.setPUserName(puser.getUserName());
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
        if (!super.baseCheck(tel, StringUtils::hasText)) throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,"数据非法");
        List<User> findByTel = userMapper.findByTel(tel);
        if (!findByTel.isEmpty())
            throw new ServiceException(SystemConstant.ALREADY_REGISTERED_CODE, SystemConstant.ALREADY_REGISTERED);
    }

    /**
     * @param userName 用户名/电话
     * @param password 密码
     * @param code     手机短信码
     * @return com.duoqio.boot.business.entity.User
     * @Description 登录
     * @Title login
     * @Author Mao Qi
     * @Date 2019/9/8 11:59
     */
    @Override
    public LoginResp login(String userName, String password, String code) {
        PubFun.check(userName);
        User user = userMapper.findByUserName(userName);//查询用户
        if (Objects.isNull(user)) {//不存在
            throw new ServiceException(516,"手机号未注册,请先注册!");
        } else {
            if (StringUtils.isEmpty(password)) {//验证码登录
                PubFun.check(code);//数据校验
                //校验手机验证
                messagesCheck(userName, code);
            } else {//密码登录
                PubFun.check(password);//数据校验
                //校验密码
                String md5 = MD5Utils.isEncryption(password, user.getSalt());
                if (Objects.isNull(user.getPassWord())) throw new ServiceException(517,"您未设置密码,请用短信验证码登录!");
                if (!user.getPassWord().equals(md5)) {
                    throw new ServiceException(518,"您输入的密码错误,请重新输入!");
                }
            }
            if (user.getStatus() == 1) {
                throw new ServiceException(519,"用户已被冻结,请联系管理员!");
            }
            if (user.getAudit() == 1) {
                throw new ServiceException(521,"该账号还在审核中!");
            }
            if (user.getAudit() == 3) {
                throw new ServiceException(522,"该账号未通过审核," + user.getAuditExplain());
            }
            // 保存最近一次登入时间
            user.setLoginTime(LocalDateTime.now());
            userMapper.updateByPrimaryKeySelective(user);
        }
        redisTemplate.delete(AppTokenUtils.CODE_FILE + userName);
        return getAppLoginResp(user.getUserId(), user);
    }


    //生成包括token的返回登录数据
    private LoginResp getAppLoginResp(Long userId, User userInfo) {
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
        data.setUserInfo(BeanUtils.copy(userInfo));
        //敏感信息不返回给前端，加入缓存2天方便解密时使用
        AuthToken appToken = new AuthToken(userId, token);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, userId), JSON.toJSONString(appToken), 60 * 60 * 24 * 30L);
        return data;
    }

    /**
     * @param tel
     * @return java.lang.Boolean
     * @Description 发送短信验证码
     * @Title sendMessages
     * @Author Mao Qi
     * @Date 2019/9/8 15:35
     */
    @Override
    public Map<String, Object> sendMessages(HttpServletRequest request, String tel) {
        if (!StringUtils.hasText(tel)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
        }
        Function<String, Map<String, Object>> deal = param -> {
            Map<String, Object> map = sendMessages("1755231367", tel, PubFun.generateRandomNumbersMax10(4), request);
            return map;
        };
        return super.base(tel, deal);
    }

    public  Map<String, Object> sendMessages(String template, String phone, String code, HttpServletRequest request) {
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
            throw new ServiceException(522,"昵称名过长,请重新设置!");
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
    public User  updateCommonTel(String commonTel,String commonName) {
        AuthToken appToken = AppTokenUtils.getAuthToken();
        PubFun.check(commonTel,commonName);
        if (commonTel.trim().length() != 11 ) {
            throw new ServiceException(523,"请输入正确手机号");
        }
        User user = selectUserGiveCurrService(appToken.getUserId());
        user.setCommonTel(commonTel);
        user.setCommonName(commonName);
        user.setUpdateUserId(appToken.getUserId());
        userMapper.updateByPrimaryKeySelective(user);
        appToken.setUser(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(appToken));
        return BeanUtils.copy(user);
    }
    public User selectUserGiveCurrService(Long userId){
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
        AuthToken appToken = AppTokenUtils.getAuthToken();
        if (super.baseCheck(userCfg, param -> Objects.isNull(param) || !StringUtils.hasText(param.getRealName()))) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
        }
        User user = selectUserGiveCurrService(appToken.getUserId());
        user.setRealName(userCfg.getRealName());
        userMapper.updateByPrimaryKeySelective(user);
        appToken.setUser(user);
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
        AuthToken appToken = AppTokenUtils.getAuthToken();
        return addressMapper.findByUserId(appToken.getUserId());
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
        AuthToken appToken = AppTokenUtils.getAuthToken();
        return addressMapper.findByDefault(appToken.getUserId());
    }




    /**
     * 通过id获取addr
     *
     * @return {@link Address}
     */
    @Override
    public Address getAddrById(Long addId) {
        if (super.baseCheck(addId, Objects::isNull)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
        }
        return Optional.ofNullable(addressMapper.selectByPrimaryKey(addId)).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
    }



    /**
     * @param addrId 地址id
     * @return java.lang.Boolean
     * @Description 修改默认地址
     * @Title updateDefaultAddrById
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateDefaultAddrById(Long addrId) {
        AuthToken appToken = AppTokenUtils.getAuthToken();
        PubFun.check(addrId);
        Address oldAddr = Optional.ofNullable(addressMapper.selectByPrimaryKey(addrId)).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
        oldAddr.setDefaultFlag(1);
        addressMapper.updateByPrimaryKey(oldAddr);
        //	将非当前地址id默认的取消
        addressMapper.updateAddrDefaultByIdNot(appToken.getUserId(), oldAddr.getAddrId());
        return true;
    }

    /**
     * @param addrId
     * @return java.lang.Boolean
     * @Description 删除收获地址
     * @Title deleteAddr
     * @Author Mao Qi
     * @Date 2019/9/22 18:52
     */
    @Transactional
    @Override
    public Boolean deleteAddr(Long addrId) {
        AuthToken appToken = AppTokenUtils.getAuthToken();
        PubFun.check(addrId);
        Address oldAddr = Optional.ofNullable(addressMapper.selectByPrimaryKey(addrId)).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
        oldAddr.setDeleteFlag(1);
        addressMapper.updateByPrimaryKey(oldAddr);
        return true;
    }

    //添加收获地址
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addAddr(String name, String tel1, String addr2,  String city, String district, String province,
                           String cityId, String districtId, String provinceId, Integer defaultFlag) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        PubFun.check(name, tel1, addr2, defaultFlag);
        Address addr = new Address();
        List<Address> addrs = addressMapper.findByUserId(authToken.getUserId());
        if(addrs.size()==0){
            defaultFlag = 1;
        }
        if (defaultFlag == 1) {//设置为默认
            //	将当前用户其它设置的默认取消
            addressMapper.updateAddrDefaultFlag(authToken.getUserId());
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
        addr.setUserId(authToken.getUserId());
        //addr.setAddr1(province + "," + city + "," + district);
        addr.setAddr1(province + city  + district);
        addr.setAddr2(addr2);
        //addr.setAddr3(addr3);
        addr.setProvinceId(Integer.parseInt(provinceId.trim()));
        addr.setCityId(Integer.parseInt(cityId.trim()));
        addr.setCountyId(Integer.parseInt(districtId.trim()));
        addr.setName(name);
        addr.setTel1(tel1);
        addr.setStatus(0);
        addr.setAddUserId(authToken.getUserId());
        addressMapper.updateByPrimaryKey(addr);
        return true;
    }

    //修改收获地址
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Boolean updateAddr(Long addrId, String name, String tel1, String addr2, String city, String district, String province,
                              String cityId, String districtId, String provinceId, Integer defaultFlag) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        PubFun.check(addrId, name, tel1, addr2, defaultFlag);
        Address oldAddr = Optional.ofNullable(addressMapper.selectByPrimaryKey(addrId)).orElseThrow(() -> PubFun.throwException("未查询到地址信息"));
        if (oldAddr.getDefaultFlag() != null && oldAddr.getDefaultFlag() == 1) {//	原本是默认
            if (defaultFlag == 0) {//	现在取消默认
                oldAddr.setDefaultFlag(0);
            }
        } else {// 原本不是默认
            if (defaultFlag == 1) {//	现在默认
                //	将其它改为非默认
                addressMapper.updateAddrDefaultFlag(authToken.getUserId());
                oldAddr.setDefaultFlag(1);
            }
        }
        oldAddr.setCity(city);
        oldAddr.setCounty(district);
        oldAddr.setProvince(province);
        oldAddr.setAddr1(province  + city  + district);
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
     * @param oldPassWord
     * @param newPassWord
     * @return java.lang.Boolean
     * @Description 修改密码
     * @Title updatePassWord
     * @Author Mao Qi
     * @Date 2019/9/22 18:52
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updatePassWord(String oldPassWord, String newPassWord) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        if (!StringUtils.hasText(oldPassWord) || !StringUtils.hasText(newPassWord)) {
            throw new ServiceException(SystemConstant.DATA_ILLEGALITY_CODE,SystemConstant.DATA_ILLEGALITY);
        }
        // 严格验证登录密码
        User old = Optional.ofNullable(userMapper.selectByPrimaryKey(authToken.getUserId())).get();
        //密码处理
        String md5 = MD5Utils.isEncryption(oldPassWord, old.getSalt());
        if (!old.getPassWord().equals(md5)) {
            throw new ServiceException(524,"您输入的原密码错误,请重新输入!");
        }
        String[] encryption = MD5Utils.encryption(newPassWord);
        old.setPassWord(encryption[0]);
        userMapper.updateByPrimaryKeySelective(old);
        return true;
    }

    //修改手机号/用户名
    @Override
    @Transactional
    public User updateUserName(String userName, String code) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        // 数据校验
        PubFun.check(userName, code);
        //校验手机验证
        messagesCheck(userName, code);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getUserName,userName).eq(User::getUserType,0).eq(User::getDeleteFlag,0);
        Long count = userMapper.selectCount(wrapper);
        User currentUser = userMapper.selectByPrimaryKey(authToken.getUserId());
        if (count > 0) {//存在
            throw new ServiceException(525,"手机号已注册,请更换手机号!");
        } else {
            currentUser.setUserName(userName);
            currentUser.setTel(userName);
            userMapper.updateByPrimaryKeySelective(currentUser);
        }
        redisTemplate.delete(AppTokenUtils.CODE_FILE + userName);//删除验证码
        authToken.setUser(currentUser);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, authToken.getUserId()), JSON.toJSONString(authToken));
        return BeanUtils.copy(currentUser);
    }

    //设置/忘记密码
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addPassword(String tel, String code, String password) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        PubFun.check(tel, code, password);
        messagesCheck(tel, code);
        if (password.length() < 8) throw new ServiceException(526,"密码必须八位以上!");
        String regex = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";
        if (!password.matches(regex)) throw new ServiceException(527,"密码必须又数字和字母组成!");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getUserName,tel).eq(User::getDeleteFlag,0).eq(User::getUserType,0);
        User user = userMapper.selectOne(wrapper);
        String[] encryption = MD5Utils.encryption(password);
        user.setPassWord(encryption[0]);
        user.setSalt(encryption[1]);
        userMapper.updateByPrimaryKeySelective(user);
        if (Objects.nonNull(authToken))
            authToken.setUser(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, user.getUserId()), JSON.toJSONString(authToken));
        return true;
    }




    //是否有交易密码
    @Override
    public boolean hasPayPassword() {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User data = userMapper.selectByPrimaryKey(authToken.getUserId());
        if (null == data) {
            return false;
        }
        return data.getPayPassword() != null;
    }

    //交易密码验证
    @Override
    public boolean isPayPassword(String payPassword) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User data = userMapper.selectByPrimaryKey(authToken.getUserId());
        if (null == data) {
            return false;
        }
        return data.getPayPassword().equals(MD5Utils.isEncryption(payPassword, data.getUserId().toString()));
    }

    //设置支付密码
    @Override
    public boolean addPayPassword(String payPassword) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User user = userMapper.selectByPrimaryKey(authToken.getUserId());
        if (Objects.isNull(user)) throw new RuntimeException("用户已经不存在,请联系管理员");
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
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User user = userMapper.selectByPrimaryKey(authToken.getUserId());
        //金币余额
        //BigDecimal goldBalance = appMoneyInfoTblService.findUserIdTotal(authToken.getUserId(),1).get("totalMoney");// 1.获取用户余额，查询余额是否足够
        //银币余额
        //BigDecimal silverBalance = appMoneyInfoTblService.findUserIdTotal(authToken.getUserId(),1).get("totalMoney");// 1.获取用户余额，查询余额是否足够

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
        AuthToken authToken = AppTokenUtils.getAuthToken();
        if (!Objects.isNull(authToken.getUserId())) {
            AppTokenUtils.remove();
            redisTemplate.delete(String.format(RedisConstant.KEY_USER_TOKEN, authToken.getUserId()));
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
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(authToken.getUserId())).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
        user.setShareImg(getAppQrCode(authToken.getUserId()));
        user.setUserImg(userImg);
        userMapper.updateByPrimaryKeySelective(user);
        authToken.setUser(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, authToken.getUserId()), JSON.toJSONString(authToken));
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
        AuthToken authToken = AppTokenUtils.getAuthToken();
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
        AuthToken authToken = AppTokenUtils.getAuthToken();
        PageHelper.startPage(pageAction.getCurrentPage(),pageAction.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(User.class);
        wrapper.eq(User::getPid,authToken.getUserId()).eq(User::getDeleteFlag,0).eq(User::getUserType,0);
        List<User> pidUserList = userMapper.selectList(wrapper);
        return new PageInfo<>(pidUserList);
    }

    //查询直接上级
    @Override
    public GeneralResult parentUser() {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User pUser = userMapper.selectByPrimaryKey(authToken.getUser().getPid());
        return GeneralResult.success(pUser == null ? null : BeanUtils.copy(pUser));
    }

    //注销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delUser() {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(authToken.getUserId())).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
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
    public User addOrUpdateAliNum(String realName, String alipayNum) {
        PubFun.check(realName,alipayNum);
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(authToken.getUserId())).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
        user.setAlipayNum(alipayNum);//支付宝账户
        user.setRealName(realName);//真实姓名
        user.setUpdateUserId(authToken.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateByPrimaryKeySelective(user);
        authToken.setUser(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, authToken.getUserId()), JSON.toJSONString(authToken));
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
                LambdaQueryWrapper<User> wrapper =new LambdaQueryWrapper<>(User.class);
                wrapper.in(User::getUserId,collect).orderByDesc(User::getLevelNum);
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
        AuthToken authToken = AppTokenUtils.getAuthToken();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("未获取到用户ID");
        }
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(userId)).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
        if (!(authToken.getUserId()).equals(user.getPid())) {
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
        AuthToken authToken = AppTokenUtils.getAuthToken();
        if (super.baseCheck(userId, Objects::isNull)) {
            throw new RuntimeException("未获取到用户ID");
        }
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(userId)).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
        user.setUserType(2);//将用户等级修改为店主
        user.setAudit(2);//将审核状态改为审核通过
        userMapper.updateByPrimaryKeySelective(user);
        authToken.setUser(user);
        return true;
    }

    /**
     * @param list
     * @param resultMap
     * @param pid
     * @param pidStr
     * @param level
     * @return java.util.HashMap<java.lang.String               ,               java.lang.Object>
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
        wrapper.eq(User::getWxUnionId,wxUnionId).eq(User::getDeleteFlag,0);
        return userMapper.selectOne(wrapper);
    }



    //添加或者修改支付宝或者微信收款账户  type 1 支付宝 2 微信
    @Transactional
    @Override
    public User addOrUpdateAliOrWx(String tel,String name, String account, Integer type) {
        PubFun.check(account,type);
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User user = Optional.ofNullable(userMapper.selectByPrimaryKey(authToken.getUserId())).orElseThrow(() -> PubFun.throwException("未查询到用户信息"));
        if(type == 1){//支付宝
            PubFun.check(account,name);
            user.setAliName(name);
            user.setAliAccount(account);
        }else{
            PubFun.check(account,tel);
            user.setWxTel(tel);
            user.setWxAccount(account);
        }
        userMapper.updateByPrimaryKeySelective(user);
        authToken.setUser(user);
        redisTemplate.opsForValue().set(String.format(RedisConstant.KEY_USER_TOKEN, authToken.getUserId()), JSON.toJSONString(authToken));
        return BeanUtils.copy(user);
    }




    //分页查询我的直接和间接下级以及统计
    @Override
    public GeneralResult findCustomerAndStatistics(PageRequest pageRequest) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        Map<String, Object> map = new HashMap<>();
//		List<UserResp> res = new ArrayList<>();
//		List<UserResp> res1 = new ArrayList<>();
//		List<UserResp> res2 = new ArrayList<>();
        User user = userMapper.selectByPrimaryKey(authToken.getUserId());
        //所有一级二级
        Page<User> customerList = userMapper.findFirstAndSecondByUserId(user.getUserId(),user.getLevelNum()+1,user.getLevelNum()+2, pageRequest);
        if(customerList.getContent().size()>0){
            for (User s:customerList.getContent()) {
                Long aLong = userMapper.countUserChildByUserId(s.getUserId());
                s.setPassWord("");
                s.setSalt("");
                s.setSubordinateNum(aLong);//下一级数量
                s.setSubordinateLevel(s.getLevelNum()-user.getLevelNum());//下级等级
            }
        }
        map.put("page",customerList);
        map.put("total", userMapper.countUserChildByUserId(authToken.getUserId()));
        return GeneralResult.success(map);
    }

    /**
     * 循环截取某页列表进行分页
     * @param dataList 分页数据
     * @param pageSize  页面大小
     * @param pageNumber   当前页面
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


    /**查询实名认证信息 */
    @Override
    public GeneralResult findRealAutheInfo() {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        User one = userMapper.selectByPrimaryKey(authToken.getUserId());
        return GeneralResult.success(one);
    }

    /** 执行 - 实名信息认证 */
    @Override
    public GeneralResult runRealAutheInfo(String realName, String cardNo) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        JSONObject jsonObject = CardCheckUtils.checkUserCard(realName, cardNo);
        if(jsonObject != null){
            Boolean isok = jsonObject.getBoolean("isok");
            if(isok){
                User one = userMapper.selectByPrimaryKey(authToken.getUserId());
                one.setRealName(realName);
                one.setCardNumber(cardNo);
                userMapper.updateByPrimaryKey(one);
                return GeneralResult.success(one);
            }

        }
        return GeneralResult.failure(-1, "身份认证失败");
    }

    //根据是否是新用户提前五秒进入
    @Override
    public Boolean whetherNewUser(String time) {
        AuthToken authToken = AppTokenUtils.getAuthToken();
        LocalTime scheduleTime = DateUtil.stringToLocalTime(time, "HH:mm");//规定的开始时间
        Integer newUserDay = SystemConfig.getNewUserDay() == null ? 30 : Integer.valueOf(SystemConfig.getNewUserDay());
        LocalDateTime addTime = authToken.getUser().getAddTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(addTime,now);
        if(duration.toDays()>=0 && duration.toDays()<=newUserDay){//如果是新用户
            //判断是否五秒
            Integer userEnterInAdvance = SystemConfig.getUserEnterInAdvance() == null ? 5 : Integer.valueOf(SystemConfig.getUserEnterInAdvance());
            LocalTime localTime = now.toLocalTime();
            Long millis = Duration.between(localTime, scheduleTime).toMillis();//毫秒
            return millis<=userEnterInAdvance*1000 ? true : false;
        }else{
            return false;
        }
    }

}