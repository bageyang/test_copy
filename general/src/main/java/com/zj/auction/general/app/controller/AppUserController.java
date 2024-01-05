package com.zj.auction.general.app.controller;
import com.zj.auction.common.model.User;
import com.zj.auction.common.util.PubFun;
import com.zj.auction.common.vo.PageAction;
import com.zj.auction.general.app.service.AppUserService;
import com.zj.auction.common.vo.GeneralResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * app用户登录
 *
 * @author 胖胖不胖
 * @date 2022/06/08
 */
@Api(value="app用户接口",tags={"app用户接口"})
@RestController
@RequestMapping(value="/app/user")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class AppUserController {
    private final AppUserService appUserService;


    /**
     * @param tel     电话
     * @param code    验证码
     * @Description   用户注册
     */
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "账户", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String"),
            @ApiImplicitParam(name = "pUsername", value = "父级id", dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "父级id", dataType = "Long"),
    })
    @PostMapping(value = "/register")
    public GeneralResult register(String tel, String password, String code, Long pid, String pUsername) {
        return GeneralResult.success(appUserService.register(tel,password, code,pid,pUsername));
    }

    /**
     * @Description 二维码分享注册
     * @Title registerShare
     */
    @ApiOperation(value = "二维码分享注册", notes = "tel(String)：注册号码，passWord(String):密码，memo1(String):手机验证码,pid(Integer):父级id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "注册号码"),
            @ApiImplicitParam(name = "passWord", value = "密码"),
            @ApiImplicitParam(name = "memo1", value = "手机验证码"),
            @ApiImplicitParam(name = "pid", value = "父级id")
    })
    @PostMapping(value = "/registerShare")
    public GeneralResult registerShare(User userInfoTbl) {
        return GeneralResult.success(appUserService.registerShare(userInfoTbl));
    }

    /**
     * @param userName 用户名/手机号
     * @param code  短信验证码
     * @Description 登录
     * @Title login
     * @Author Mao Qi
     * @Date 2019/9/8 10:01
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation("单点登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "账户", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", dataType = "String"),
    })
    @PostMapping(value = "/login")
    public GeneralResult login(String userName, String password,String code) {
        return GeneralResult.success(appUserService.login(userName,password, code));
    }

    /**
     * @param userName
     * @Description 发送手机短信
     * @Title sendMessages
     * @Author Mao Qi
     * @Date 2019/9/6 16:47
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "发送短信")
    @ApiImplicitParam(name = "userName", value = "电话")
    @PostMapping(value = "/sendMessages")
    public GeneralResult sendMessages(HttpServletRequest request, String userName) {
        return GeneralResult.success(appUserService.sendMessages(request,userName));
    }


    /**
     * @Description 增加或修改支付宝账户
     */
    @ApiOperation(value = "增加或修改支付宝账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "真实姓名", dataType = "String"),
            @ApiImplicitParam(name = "alipayNum", value = "支付宝账户", dataType = "String")
    })
    @PostMapping(value = "/addOrUpdateAliNum")
    public GeneralResult addOrUpdateAliNum(String realName, String alipayNum) {
        return GeneralResult.success(appUserService.addOrUpdateAliNum(realName,alipayNum));
    }

    /**
     * 更新用户名
     *
     * @param userName 用户名
     * @param code     代码
     * @return {@link GeneralResult}
     * @Description 修改手机号/用户名
     * @Title updateUserName
     */
    @ApiOperation(value = "修改手机号/用户名", notes = "nickName:昵称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "账户", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", dataType = "String")
    })
    @PostMapping(value = "/updateUserName")
    public GeneralResult updateUserName(String userName, String code) {
        return GeneralResult.success(appUserService.updateUserName(userName,code));
    }


    /**
     * @Description updateCommonTel
     * @Title updateCommonTel
     * @Author Mao Qi
     * @Date 2022/3/28 16:11
     * @param commonTel
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @ApiOperation(value = "修改常用手机号", notes = "commonTel:常用手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonTel", value = "常用手机号"),
            @ApiImplicitParam(name = "commonName", value = "常用联系姓名")
    })
    @PostMapping(value = "/updateCommonTel")
    public GeneralResult updateCommonTel(String commonTel,String commonName) {
        return GeneralResult.success(appUserService.updateCommonTel(commonTel,commonName));
    }


    /**
     * @return
     * @Title: getDefaultAddrByUserId
     * @Description: 查询用户默认收货地址
     * @author：Mao Qi
     * @date： 2019年7月9日下午7:23:31
     * @return：AddrInfoTbl
     */
    @ApiOperation(value = "查询用户默认收货地址", notes = "不传参")
    @PostMapping(value = "/getDefaultAddrByUserId")
    public GeneralResult getUserDefaultAddr() {
        return GeneralResult.success(appUserService.getDefaultAddrByUserId());
    }

    /**
     * @param
     * @Description 查询收货地址
     * @Title listAddr
     * @Author Mao Qi
     * @Date 2019/10/14 14:07
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "查询收货地址", notes = "不传参")
    @PostMapping(value = "/listAddr")
    public GeneralResult listAddr() {
        return GeneralResult.success(appUserService.listAddr());
    }

    /**
     * @param addrId
     * @return
     * @Title: updateDefaultAddrById
     * @Description: 设置为默认地址
     * @author：Mao Qi
     * @date： 2019年7月9日下午8:35:33
     * @return：GeneralResult
     */
    @ApiOperation(value = "设置为默认地址", notes = "addrId:地址id")
    @ApiImplicitParam(name = "addrId", value = "地址id", dataType = "Long")
    @PostMapping(value = "/updateDefaultAddrById")
    public GeneralResult updateDefaultAddrById(Long addrId) {
        return GeneralResult.success( appUserService.updateDefaultAddrById(addrId));
    }

    /**
     * @param addrId
     * @Description 根据id查询单个地址
     * @Title getAddrById
     * @Author Mao Qi
     * @Date 2019/9/8 16:00
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "根据id查询单个地址", notes = "addrId:地址id")
    @ApiImplicitParam(name = "addrId", value = "地址id", dataType = "Long")
    @PostMapping(value = "/getAddrById")
    public GeneralResult getAddrById(Long addrId) {
        return GeneralResult.success(appUserService.getAddrById(addrId));
    }

    /**
     * @param addrId
     * @Description 删除地址
     * @Title deleteAddr
     * @Author Mao Qi
     * @Date 2019/10/24 19:28
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "删除地址", notes = "addrId:地址id")
    @ApiImplicitParams({@ApiImplicitParam(name = "addrId", value = "地址id", dataType = "Long")})
    @PostMapping(value = "/deleteAddr")
    public GeneralResult deleteAddr(Long addrId) {
        return GeneralResult.success(appUserService.deleteAddr(addrId));
    }

    /**
     * @Description 新增收货地址
     * @Title addAddr
     * @Author Mao Qi
     * @Date 2020/6/12 14:12
     * @param name
     * @param tel1
     * @param addr2
     * @param defaultFlag
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @ApiOperation(value = " 增加收货地址", notes = "name:姓名,tel1:电话,addr2:定位地址,addr3:门牌号，Longitude:纬度,Latitude经度,defaultFlag:是否默认（0非默认,1默认)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String"),
            @ApiImplicitParam(name = "tel1", value = "电话", dataType = "String"),
            @ApiImplicitParam(name = "addr2", value = "详细地址", dataType = "String"),
            @ApiImplicitParam(name = "defaultFlag", value = "0 非默认 1 默认", dataType = "Integer")
    })
    @PostMapping(value = "/addAddr")
    public GeneralResult addAddr(String name,String tel1,String addr2,String city,String district,String province,
                                 String cityId,String districtId,String provinceId,Integer defaultFlag) {
        return GeneralResult.success(appUserService.addAddr(name,tel1,addr2, city, district, province, cityId, districtId, provinceId,defaultFlag));
    }

    /**
     * @Description 修改收货地址
     * @Title updateAddr
     * @Author Mao Qi
     * @Date 2020/6/12 14:23
     * @param addrId
     * @param name
     * @param tel1
     * @param addr2
     * @param defaultFlag
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @ApiOperation(value = "修改收货地址", notes = "addrId 地址id ,name:姓名,tel1:电话,addr2:定位地址,addr3:门牌号，Longitude:经度,Latitude 纬度,defaultFlag:是否默认（0非默认,1默认)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addrId", value = "地址id", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String"),
            @ApiImplicitParam(name = "tel1", value = "电话", dataType = "String"),
            @ApiImplicitParam(name = "addr2", value = "定位地址", dataType = "String"),
            @ApiImplicitParam(name = "defaultFlag", value = "0 非默认 1 默认", dataType = "Integer")
    })
    @PostMapping(value = "/updateAddr")
    public GeneralResult updateAddr(Long addrId,String name,String tel1,String addr2,String city,String district,String province,
                                    String cityId,String districtId,String provinceId,Integer defaultFlag) {
        return GeneralResult.success(appUserService.updateAddr( addrId, name, tel1, addr2 , city, district, province, cityId, districtId, provinceId, defaultFlag));
    }

    /**
     * @param oldPassWord
     * @param oldPassWord
     * @Description 修改密码
     * @Title updatePassWord
     * @Author Mao Qi
     * @Date 2019/9/8 16:03
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "修改登入密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassWord", value = "原密码", dataType = "String"),
            @ApiImplicitParam(name = "newPassWord", value = "新密码", dataType = "String")
    })
    @PostMapping(value = "/updatePassWord")
    public GeneralResult updatePassWord(String oldPassWord, String newPassWord) {
        return GeneralResult.success(appUserService.updatePassWord(oldPassWord, newPassWord));
    }



    /**
     * @Description 设置密码
     * @Title addPassword
     * @Author Mao Qi
     * @Date 2020/10/8 19:48
     * @param tel
     * @param code
     * @param password
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @ApiOperation(value = "设置密码", notes = "tel：电话号,password：密码，code：短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "String")
    })
    @PostMapping(value = "/addPassword")
    public GeneralResult addPassword(String tel,String code, String password) {
        return GeneralResult.success(appUserService.addPassword(tel, code,password));
    }

    /**
     * @Description 忘记密码
     * @Title forgetPassword
     * @Author Mao Qi
     * @Date 2020/10/8 19:48
     * @param tel
     * @param code
     * @param password
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @ApiOperation(value = "忘记密码", notes = "tel：电话号,password：密码，code：短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "String"),
    })
    @PostMapping(value = "/forgetPassword")
    public GeneralResult forgetPassword(String tel,String code, String password) {
        return GeneralResult.success(appUserService.forgetPassword(tel, code,password));
    }


    /**
     * @Description 判断用户是否已经设置支付密码
     * @Title isPayPassword
     */
    @ApiOperation(value = "判断用户是否已经设置支付密码")
    @PostMapping("/hasPayPassword")
    public GeneralResult hasPayPassword(){
        return GeneralResult.success(appUserService.hasPayPassword());
    }


    /**
     * @param payPassword
     * @Title 验证交易密码
     * @Description payPassword
     */
    @ApiOperation(value = "验证交易密码")
    @PostMapping("checkPayPassword")
    public GeneralResult payPassword(String payPassword) {
        PubFun.check (  payPassword );
        return GeneralResult.success(appUserService.isPayPassword( payPassword ));
    }


    /**
     * @Description 添加支付密码
     * @Title addPayPassword
     * @Author Mao Qi
     * @Date 2020/6/12 13:53
     * @param payPassword
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @ApiOperation(value = "添加支付密码")
    @PostMapping("addPayPassword")
    public GeneralResult addPayPassword(String payPassword) {
        PubFun.check (  payPassword );
        return GeneralResult.success(appUserService.addPayPassword( payPassword ));
    }


    /**
     * @param
     * @Description 查询用户信息
     * @Title findByUserCfg
     * @Author Mao Qi
     * @Date 2019/10/31 19:22
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "查询用户信息")
    @PostMapping(value = "/findByUserCfg")
    public GeneralResult findByUserCfg() {
        return GeneralResult.success(appUserService.findByUserCfg());
    }

    /**
     * @param userCfg
     * @return
     * @title: findByUserName
     * @description: 根据账号查询用户
     * @author: Mao Qi
     * @date: 2019年7月16日下午3:28:25
     * @return: GeneralResult
     */
    @ApiOperation(value = "根据账号查询用户", notes = "userName(String):账号")
    @PostMapping(value = "/findByUserName")
    public GeneralResult findByUserName(User userCfg) {
        return GeneralResult.success(appUserService.findByUserName(userCfg));
    }

    /**
     * @param
     * @Description 登出
     * @Title exit
     * @Author Mao Qi
     * @Date 2019/9/12 15:54
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "退出登入")
    @PostMapping(value = "/outLogin")
    public GeneralResult outLogin() {
        return GeneralResult.success(appUserService.exit());
    }


    /**
     * @param
     * @Description 注销
     * @Title delUser
     * @Author Mao Qi
     * @Date 2019/9/12 15:54
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "注销")
    @PostMapping(value = "/delUser")
    public GeneralResult delUser() {
        return GeneralResult.success(appUserService.delUser());
    }


    /**
     * @Description 实名认证
     * @Title realNameAuth
     * @Author Mao Qi
     * @Date 2020/4/3 15:39
     * @return com.duoqio.boot.framework.result.GeneralResult
     */
    @ApiOperation(value = "实名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "真实姓名", dataType = "String"),
            @ApiImplicitParam(name = "cardNum", value = "身份证号", dataType = "String"),
            @ApiImplicitParam(name = "frontImage", value = "身份证正面", dataType = "String"),
            @ApiImplicitParam(name = "reverseImage", value = "身份证反面", dataType = "String")
    })
    @PostMapping(value = "/authIdentity")
    public GeneralResult authIdentity(String realName, String cardNum, String frontImage, String reverseImage) {
        return GeneralResult.success(appUserService.authIdentity(realName, cardNum, frontImage, reverseImage));
    }




    /**
     * @Description 查询上级信息
     * @Title parentUser
     */
    @ApiOperation(value = "查询上级")
    @PostMapping(value = "/parentUser")
    public GeneralResult parentUser() {
        return appUserService.parentUser();
    }


    /**
     * @Description 分页查询我的直接下级
     */
    @ApiOperation(value = "分页查询我的直接下级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页数", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "Integer")
    })
    @PostMapping(value = "/findCustomer")
    public GeneralResult findCustomerByUserId(PageAction action) {
        return GeneralResult.success(appUserService.findCustomerByUserId(action));
    }


    @ApiOperation(value = "查询实名认证信息")
    @PostMapping(value = "/findRealAutheInfo")
    public GeneralResult findRealAutheInfo() {
        return appUserService.findRealAutheInfo();
    }

    @ApiOperation(value = "执行 - 实名信息认证")
    @PostMapping(value = "/runRealAutheInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "姓名", dataType = "string"),
            @ApiImplicitParam(name = "cardNumber", value = "身份证号", dataType = "string"),
    })
    public GeneralResult runRealAutheInfo(@RequestParam String realName, @RequestParam String cardNumber) {
        return appUserService.runRealAutheInfo(realName, cardNumber);
    }

    /**
     * @Description 判断是否是新用户可以提前进入
     */
    @ApiOperation(value = "判断是否是新用户可以提前进入")
    @PostMapping(value = "/whetherNewUser")
    public GeneralResult whetherNewUser(String time ) {
        return GeneralResult.success(appUserService.whetherNewUser(time));
    }

}
