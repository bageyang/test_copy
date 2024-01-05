package com.zj.auction.general.service;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.zj.auction.common.model.Address;
import com.zj.auction.common.model.Bank;
import com.zj.auction.common.model.User;
import com.zj.auction.general.vo.GeneralResult;
import com.zj.auction.general.vo.LoginResp;
import com.zj.auction.general.vo.UserVO;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface UserService extends Mapper<User> {

    /**
     * @Description 查询用户为多少人服务统计
     * @Title customerTotal
     * @Author Mao Qi
     * @Date 2020/4/21 17:22
     * @param
     * @return	java.lang.Integer
     */
    Integer customerTotal();


    /**
     * @Description 实名认证
     * @Title authIdentity
     * @Author Mao Qi
     * @Date 2020/4/3 17:47
     * @param realName       真实姓名
     * @param cardNum		 身份证号
     * @param frontImage	 身份证正面
     * @param reverseImage	 身份证反面
     * @return	java.lang.Boolean
     */
    Boolean authIdentity(String realName, String cardNum, String frontImage, String reverseImage);

    /**
     * @Description 新增用戶
     * @Title save
     * @Author Mao Qi
     * @Date 2020/2/15 13:04
     * @param insertUserInfoTbl1
     * @return	com.duoqio.boot.business.entity.UserInfoTbl
     */
    User save(User  insertUserInfoTbl1);



    UserVO register(String tel, String password, String code, Long pid, String pUsername);


    Boolean registerShare(User user);

    /**
     * @title: bindRelationShare
     * @description: 分享下单绑定上下级关系
     * @author: Mao Qi
     * @date: 2019年7月30日下午4:19:47
     * @param userId 当前用户id
     * @param pid 父级id
     * @return: Boolean
     */
    Boolean bindRelationShare(Long userId, Long pid);

    /**
     * 判断是否注册
     * @param tel
     * @return
     */
    void telCheck(String tel);

    /**
     * @Description 登录
     * @Title login
     * @Author Mao Qi
     * @Date 2019/9/8 11:52
     * @param userName 账户/手机号
     * @param code
     * @return	com.duoqio.boot.business.entity.UserInfoTbl
     */
    LoginResp login(String userName, String password, String code);


    /**
     * 发送短信
     * @param tel
     * @return
     */
    Map<String, Object> sendMessages(HttpServletRequest request,String tel);

    /**
     * @Title: findUserById
     * @Description: 根据用户id查询用户信息
     * @author：Mao Qi
     * @date： 2019年8月13日下午3:01:31
     * @param userId
     * @return：UserCfg
     */
    User findUserById(Long userId);

    /**
     * @Description 根据用户id查询用户信息,金币,银币,店铺业绩
     * @Title findUserDetail
     * @Author Mao Qi
     * @Date 2020/10/8 19:50
     * @return	com.duoqio.entity.custom.UserInfoTblResp
     */
    UserVO findUserDetail();



    /**
     * @Title: updateNickName
     * @Description: 修改昵称
     * @author：Mao Qi
     * @date： 2019年7月9日下午4:57:42
     * @param userCfg
     * @return
     * @return：UserInfoTblDTO
     */
    UserVO updateNickName(User userCfg);

    /**
     * @Description 修改常用手机号
     * @Title updateCommonTel
     * @Author Mao Qi
     * @Date 2022/3/28 16:11
     * @param commonTel
     * @return	com.duoqio.entity.custom.UserInfoTblResp
     */
    UserVO updateCommonTel(String commonTel,String commonName);

    /**
     * @Title: updateRealName
     * @Description: 修改真实姓名
     * @author：Mao Qi
     * @date： 2019年7月9日下午4:57:42
     * @param userCfg
     * @return
     * @return：Boolean
     */
    Boolean updateRealName(User userCfg);

    /**
     * @Title: listAddr
     * @Description: 查询收货地址
     * @author：Mao Qi
     * @date： 2019年7月9日下午8:32:21
     * @return：List<AddrInfoTbl>
     */
    List<Address> listAddr();

    /**
     * @Title: updateDefaultAddrById
     * @Description: 设置为默认地址
     * @author：Mao Qi
     * @date： 2019年7月9日下午7:25:18
     * @param addrId
     * @return
     * @return：Boolean
     */
    Boolean updateDefaultAddrById(Long addrId);

    /**
     * @Title: getDefaultAddrByUserId
     * @Description: 查询用户默认收货地址
     * @author：Mao Qi
     * @date： 2019年7月9日下午7:23:31
     * @return
     * @return：AddrInfoTbl
     */
    Address getDefaultAddrByUserId();

    /**
     * @Description 根据id查询单个地址
     * @Title getAddrById
     * @Author Mao Qi
     * @Date 2019/9/6 16:31
     * @param addrInfoTbl
     * @return	com.duoqio.boot.business.entity.AddrInfoTbl
     */
    Address getAddrById(Address addrInfoTbl);

    /**
     * @Description 根据id查询单个地址
     * @Title getAddrById
     * @Author Mao Qi
     * @Date 2019/9/6 16:31
     * @param addrId
     * @return	com.duoqio.boot.business.entity.AddrInfoTbl
     */
    Address getAddrById(Long addrId);

    /**
     * @Title: deleteAddr
     * @Description: 删除地址
     * @author：Mao Qi
     * @date： 2019年7月9日下午7:22:05
     * @param addrId
     * @return
     * @return：Boolean
     */
    Boolean deleteAddr(Long addrId);

    /**
     * @Description 添加收货地址
     * @Title addAddr
     * @Author Mao Qi
     * @Date 2020/6/12 14:13
     * @param name
     * @param tel1
     * @param addr2
     * @param defaultFlag
     * @return	java.lang.Boolean
     */
    Boolean addAddr(String name,String tel1,String addr2,String city,String district,String province,
                    String cityId,String districtId,String provinceId,Integer defaultFlag);

    /**
     * @Description 修改地址
     * @Title updateAddr
     * @Author Mao Qi
     * @Date 2020/6/12 14:19
     * @param addrId
     * @param name
     * @param tel1
     * @param addr2
     * @param defaultFlag
     * @return	java.lang.Boolean
     */
    Boolean updateAddr(Long addrId,String name,String tel1,String addr2,String city,String district,String province,
                       String cityId,String districtId,String provinceId,Integer defaultFlag);



    /**
     * @Description 修改密码
     * @Title updatePassWord
     * @Author Mao Qi
     * @Date 2019/9/8 16:04
     * @param oldPassWord
     * @param newPassWord
     * @return	java.lang.Boolean
     */
    Boolean updatePassWord(String oldPassWord, String newPassWord);


    /**
     * @Description 修改手机号/用户名
     * @Title updateUserName
     * @Author Mao Qi
     * @Date 2020/10/8 19:36
     * @param userName
     * @param code
     * @return	com.duoqio.entity.custom.UserInfoTblResp
     */
    UserVO updateUserName(String userName, String code);

    /**
     * @Description 设置/忘记密码
     * @Title addPassWord
     * @Author Mao Qi
     * @Date 2020/10/8 19:49
     * @param tel
     * @param code
     * @param password
     * @return	java.lang.Boolean
     */
    Boolean addPassword(String tel,String code, String password);

    /**
     * @Description 是否有支付密码
     * @Title hasPayPassword
     * @Author Mao Qi
     * @Date 2020/6/12 13:42
     * @param
     * @return	boolean
     */
    boolean hasPayPassword();


    /**
     * @Description 校验支付密码
     * @Title isPayPassword
     * @Author Mao Qi
     * @Date 2020/6/12 13:47
     * @param payPassword
     * @return	boolean
     */
    boolean isPayPassword(String payPassword);

    /**
     * @Description 设置支付密码
     * @Title addPayPassword
     * @Author Mao Qi
     * @Date 2020/6/12 13:52
     * @param payPassword
     * @return	boolean
     */
    boolean addPayPassword(String payPassword);

    /**
     * @Description 添加银行卡
     * @Title addBankCard
     * @Author Mao Qi
     * @Date 2019/10/15 10:36
     * @param bankInfoTbl
     * @return	java.lang.Boolean
     */
    Boolean addBankCard(Bank bankInfoTbl, String code);

    /**
     * @Title: getBankById
     * @Description: 根据银行卡id查询银行卡
     * @author：Mao Qi
     * @date： 2019年7月10日下午5:50:12
     * @param bankId
     * @return BankInfoTbl
     * @return：BankInfoTbl
     */
    Bank getBankById(Long bankId);

    /**
     * @Title: listBank
     * @Description: 根据用户id查询银行卡
     * @author：Mao Qi
     * @date： 2019年7月10日下午5:50:32
     * @return：BankInfoTbl
     */
    List<Bank> listBank();

    /**
     * @Title: deleteBank
     * @Description: 删除银行卡
     * @author：Mao Qi
     * @date： 2019年7月10日下午6:05:48
     * @param bankInfoTbl
     * @return：Boolean
     */
    Boolean deleteBank(Bank bankInfoTbl);

    /**
     * 查询用户信息
     * @return
     */
    UserVO findByUserCfg();


    /**
     * @Title: exit
     * @Description: 退出登录
     * @author：Mao Qi
     * @date： 2019年7月10日下午3:19:02
     * @return
     * @return：Boolean
     */
    Boolean exit();

    /**
     * @Description 修改头像
     * @Title updateUserImg
     * @Author Mao Qi
     * @Date 2019/9/11 16:57
     * @param imgFilePath
     * @return	com.duoqio.boot.business.entity.UserInfoTbl
     */
    UserVO updateUserImg(String imgFilePath);

    /**
     * 根据账号查询用户
     * @author: Mao Qi
     * @date: 2019年7月16日下午3:30:12
     * @param userCfg
     * @return: UserCfg
     */
    UserVO findByUserName(User userCfg);

    /**
     * @title: checkUserByChildren
     * @description: 判断用户id是否是当前用户下级
     * @author: Mao Qi
     * @date: 2020年4月2日下午8:56:27
     * @param userId
     * @return: Boolean
     */
    Boolean checkUserByChildren(Long userId);


    /**
     * @Description 分页查询我的直接下级
     * @Title findCustomerByUserId
     * @Author Mao Qi
     * @Date 2021/1/8 23:04
     * @param pageNumber
     * @param pageSize
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult findCustomerByUserId(Integer pageNumber, Integer pageSize);


    /**
     * @Description 查询所有上级
     * @Title listParentUser
     * @Author Mao Qi
     * @Date 2019/10/8 9:14
     * @param userId
     * @param upLevel
     * @return	java.util.List<com.duoqio.boot.business.entity.UserInfoTbl>
     */
    List<User> listParentUser(Long userId, Integer upLevel);

    /**
     * @title: updateUserType
     * @description: 根据用户id修改用户等级
     * @author: Mao Qi
     * @date: 2019年7月29日下午6:07:21
     * @param userId 用户id
     * @return: Boolean
     */
    Boolean updateUserType(Long userId);



    /**
     * @title: findParentId
     * @description: 根据当前用户id查询其父级集合和等级
     * @author: Mao Qi
     * @date: 2019年7月31日下午3:43:38
     * @param pid
     * @return: HashMap<String,Object>
     */
    HashMap<String, Object> findParentId(List<Map<String, Object>> list, HashMap<String, Object> resultMap, Long pid, String pidStr, Integer level);

    /**
     * @title: userEntity
     * @description: 创建对象
     * @author: Mao Qi
     * @date: 2019年8月13日下午5:31:42
     * @param pid 父级id
     * @param pUserName 父级账号
     * @param pidStr 父级集合
     * @param levelNum 等级
     * @param tel 账号
     * @param passWord 密码
     * @param userImg 头像
     * @return: UserInfoTbl
     */
    default User userEntity(Long pid, String pUserName, String pidStr, Integer levelNum,
                                   String tel, String passWord, String salt, String userImg, String backgroundImg) {
        User userCfgEntity = new User();
        userCfgEntity.setPid(pid);
        userCfgEntity.setPidStr(pidStr);
        userCfgEntity.setLevelNum(levelNum);
        userCfgEntity.setShareType(1);//推荐方式，默认0,1通过二维码分享推荐，2通过合伙人添加推荐
        userCfgEntity.setUserType(0);//用户类型，0普通用户，1店主
        userCfgEntity.setIsFirstLogin(1);//	是否首次登录，1是，0不是
        userCfgEntity.setRoleType(0);
        userCfgEntity.setUserName(tel);
        userCfgEntity.setPassWord(passWord);
        userCfgEntity.setSalt(salt);
        userCfgEntity.setUserImg(userImg);
        userCfgEntity.setAddTime(LocalDateTime.now());
        userCfgEntity.setTel(tel);
        userCfgEntity.setStatus(0);
        userCfgEntity.setAudit(0);
        userCfgEntity.setLoginTime(LocalDateTime.now());
        userCfgEntity.setUpdateTime(LocalDateTime.now());
        userCfgEntity.setBackgroundImg(backgroundImg);
        userCfgEntity.setSubUserNum(0);
        userCfgEntity.setSubUserNumAll(0);
        return userCfgEntity;
    }




    /*----------------------------------微信------------------------------------*/

    /**
     * @Description 根据微信唯一id查询
     * @Title findByWxUnionId  用户在微信开发平台的唯一标识
     * @Author Mao Qi
     * @Date 2020/2/15 11:30
     * @param wxUnionId
     * @return	com.duoqio.boot.business.entity.UserInfoTbl
     */
    User findByWxUnionId(String wxUnionId);



    /**
     * @Description 忘记密码
     * @Title forgetPassword
     * @Author Mao Qi
     * @Date 2020/11/13 18:36
     * @param tel
     * @param code
     * @param password

     * @return	java.lang.Boolean
     */
    Boolean forgetPassword(String tel, String code, String password);


    /**
     * @Description 根据用户id查询用户剩余抽奖次数和剩余金币
     * @Title findUserDrawNumAndGold
     * @Author Mao Qi
     * @Date 2021/1/8 22:34
     * @param
     * @return	java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String ,Object> findUserDrawNumAndGold();


    /**
     * @Description 查询直接上级
     * @Title parentUser
     * @Author Mao Qi
     * @Date 2021/1/8 22:51
     * @param
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult parentUser();

    /**
     * @Description 注销
     * @Title delUser
     * @Author Mao Qi
     * @Date 2021/3/8 10:21
     * @param
     * @return	java.lang.Boolean
     */
    Boolean delUser();

    /**
     * @Description 添加或者修改支付宝账户
     * @Title addOrUpdateAliNum
     * @Author Mao Qi
     * @Date 2021/4/27 14:13
     * @param realName
     * @param alipayNum
     * @return	java.lang.Boolean
     */
    UserVO addOrUpdateAliNum(String realName, String alipayNum);

    /**
     * @Description 添加或者修改支付宝或者微信收款账户
     * @Title addOrUpdateAliOrWx
     * @Author Mao Qi
     * @Date 2021/10/22 10:09
     * @param tel
     * @param account
     * @param type
     * @return	com.duoqio.entity.custom.UserInfoTblResp
     */
    UserVO addOrUpdateAliOrWx(String tel,String name, String account,Integer type);

    /**
     * @Description 查询用户银行卡
     * @Title findUserBank
     * @Author Mao Qi
     * @Date 2021/10/22 14:38
     * @param
     * @return	com.duoqio.entity.pojo.BankInfoTbl
     */
    Bank findUserBank();

    /**
     * @Description 添加或者修改银行卡
     * @Title addOrUpdBank
     * @Author Mao Qi
     * @Date 2021/10/22 14:53
     * @param tel
     * @param accountName
     * @param BankName
     * @param BankNumber
     * @return	java.lang.Boolean
     */
    Boolean addOrUpdBank(String tel, String accountName, String BankName, String BankNumber);

    /**
     * @Description 分页查询我的直接和间接下级以及统计
     * @Title findCustomerAndStatistics
     * @Author Mao Qi
     * @Date 2021/10/22 16:20
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult findCustomerAndStatistics(PageRequest pageRequest);


    GeneralResult performance(Integer type);
    /**
     * 查询实名认证信息
     * @return
     */
    GeneralResult findRealAutheInfo();

    /**
     * 执行 - 实名信息认证
     * @param realName
     * @param cardNo
     * @return
     */
    GeneralResult runRealAutheInfo(String realName, String cardNo);

    /**
     * @Description 根据是否是新用户
     * @Title whetherNewUser
     * @Author Mao Qi
     * @Date 2021/11/22 11:21
     * @return	java.lang.Boolean
     */
    Boolean whetherNewUser(String time);
}
