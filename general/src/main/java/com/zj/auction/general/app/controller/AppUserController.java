package com.zj.auction.general.app.controller;
import com.zj.auction.common.dto.PassWordDTO;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.dto.UserDTO;
import com.zj.auction.common.model.Role;
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
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * app用户登录
 *
 * @author 胖胖不胖
 * @date 2022年6月17日11:42:25
 */
@Api(value="app用户接口",tags={"app用户接口"})
@RestController
@RequestMapping(value="/app/user")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class AppUserController {
    private final AppUserService appUserService;


    /**
     * 注册
     *
     * @param dto dto
     * @return {@link GeneralResult}
     */
    @ApiOperation(value = "注册")
    @PostMapping(value = "/register")
    public Ret register(@RequestBody UserDTO dto) {
        return Ret.ok(appUserService.register(dto));
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
    public GeneralResult registerShare(@RequestBody User userInfoTbl) {
        return GeneralResult.success(appUserService.registerShare(userInfoTbl));
    }


    /**
     * 登录
     *
     * @return {@link Ret}
     */
    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public Ret login(@RequestBody UserDTO dto) {
        return Ret.ok(appUserService.login(dto));
    }

    @ApiOperation("refreshToken")
    @PostMapping(value = "/refreshToken")
    public Ret login() {
        return Ret.ok(appUserService.refreshToken());
    }

    /**
     * @param
     * @Description 发送手机短信
     * @Title sendMessages
     */
    @ApiOperation(value = "发送短信")
    @ApiImplicitParam(name = "userName", value = "电话")
    @PostMapping(value = "/sendMessages")
    public Ret sendMessages(HttpServletRequest request,@RequestBody UserDTO dto) {
        return Ret.ok(appUserService.sendMessages(request,dto.getTel()));
    }

    @PostMapping(value = "/findNameByTel")
    public Ret findNameByTel(@RequestBody UserDTO dto){
        return Ret.ok(appUserService.findNameByTel(dto.getTel()));
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
    public GeneralResult addOrUpdateAliNum(@RequestBody UserDTO dto) {
        return GeneralResult.success(appUserService.addOrUpdateAliNum(dto));
    }

    /**
     * 更新用户名
     *
     * @return {@link GeneralResult}
     * @Description 修改手机号/用户名
     * @Title updateUserName
     */
    @ApiOperation(value = "修改手机号/用户名", notes = "nickName:昵称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "账户", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", dataType = "String"),
            @ApiImplicitParam(name = "newUserName", value = "新账户", dataType = "String")
    })
    @PostMapping(value = "/updateUserName")
    public GeneralResult updateUserName(@RequestBody UserDTO dto) {
        return GeneralResult.success(appUserService.updateUserName(dto));
    }


    /**
     * @Description updateCommonTel
     * @Title updateCommonTel

     */
    @ApiOperation(value = "修改常用手机号", notes = "commonTel:常用手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonTel", value = "常用手机号"),
            @ApiImplicitParam(name = "commonName", value = "常用联系姓名")
    })
    @PostMapping(value = "/updateCommonTel")
    public GeneralResult updateCommonTel(@RequestBody UserDTO dto) {
        return GeneralResult.success(appUserService.updateCommonTel(dto.getCommonTel(),dto.getCommonName()));
    }


    /**
     * @return
     * @Title: getDefaultAddrByUserId
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
     */
    @ApiOperation(value = "查询收货地址", notes = "不传参")
    @PostMapping(value = "/listAddr")
    public GeneralResult listAddr() {
        return GeneralResult.success(appUserService.listAddr());
    }

    /**
     * @return
     * @return：GeneralResult
     */
    @ApiOperation(value = "设置为默认地址", notes = "addrId:地址id")
    @ApiImplicitParam(name = "addrId", value = "地址id", dataType = "Long")
    @PostMapping(value = "/updateDefaultAddrById")
    public GeneralResult updateDefaultAddrById(@RequestBody UserDTO dto) {
        return GeneralResult.success( appUserService.updateDefaultAddrById(dto.getAddrId()));
    }

    /**
     * @Description 根据id查询单个地址
     */
    @ApiOperation(value = "根据id查询单个地址", notes = "addrId:地址id")
    @ApiImplicitParam(name = "addrId", value = "地址id", dataType = "Long")
    @PostMapping(value = "/getAddrById")
    public GeneralResult getAddrById(@RequestBody UserDTO dto) {
        return GeneralResult.success(appUserService.getAddrById(dto));
    }

    /**
     * @param
     * @Description 删除地址
     */
    @ApiOperation(value = "删除地址", notes = "addrId:地址id")
    @ApiImplicitParams({@ApiImplicitParam(name = "addrId", value = "地址id", dataType = "Long")})
    @PostMapping(value = "/deleteAddr")
    public GeneralResult deleteAddr(@RequestBody UserDTO dto) {
        return GeneralResult.success(appUserService.deleteAddr(dto.getAddrId()));
    }

    /**
     * @Description 新增收货地址
     * @Title addAddr
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
     * @Description 修改密码
     * @Title updatePassWord
     */
    @ApiOperation(value = "修改登入密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassWord", value = "原密码", dataType = "String"),
            @ApiImplicitParam(name = "newPassWord", value = "新密码", dataType = "String")
    })
    @PostMapping(value = "/updatePassWord")
    public GeneralResult updatePassWord(@RequestBody PassWordDTO dto) {
        return GeneralResult.success(appUserService.updatePassWord(dto));
    }



    /**
     * @Description 设置密码
     * @Title addPassword
     */
    @ApiOperation(value = "设置密码", notes = "tel：电话号,password：密码，code：短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "String")
    })
    @PostMapping(value = "/addPassword")
        public GeneralResult addPassword(@RequestBody PassWordDTO dto) {
        return GeneralResult.success(appUserService.addPassword(dto));
    }

    /**
     * @Description 忘记密码
     * @Title forgetPassword
     */
    @ApiOperation(value = "忘记密码", notes = "tel：电话号,password：密码，code：短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "String"),
    })
    @PostMapping(value = "/forgetPassword")
    public GeneralResult forgetPassword(@RequestBody PassWordDTO dto) {
        return GeneralResult.success(appUserService.forgetPassword(dto));
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

     * @Title 验证交易密码
     * @Description payPassword
     */
    @ApiOperation(value = "验证交易密码")
    @PostMapping("checkPayPassword")
    public GeneralResult payPassword(@RequestBody PassWordDTO dto) {
        PubFun.check (  dto );
        return GeneralResult.success(appUserService.isPayPassword(dto.getPayPassword() ));
    }


    /**
     * @Description 添加支付密码
     * @Title addPayPassword
     */
    @ApiOperation(value = "添加支付密码")
    @PostMapping("addPayPassword")
    public GeneralResult addPayPassword(@RequestBody PassWordDTO dto) {
        PubFun.check (  dto );
        return GeneralResult.success(appUserService.addPayPassword( dto.getPayPassword()));
    }


    /**
     * @param
     * @Description 查询用户信息
     */
    @ApiOperation(value = "查询用户信息")
    @PostMapping(value = "/findByUserCfg")
    public GeneralResult findByUserCfg() {
        return GeneralResult.success(appUserService.findByUserCfg());
    }

    /**
     * @param userCfg
     * @return
     */
    @ApiOperation(value = "根据账号查询用户", notes = "userName(String):账号")
    @PostMapping(value = "/findByUserName")
    public GeneralResult findByUserName(User userCfg) {
        return GeneralResult.success(appUserService.findByUserName(userCfg));
    }

    /**
     * @param
     * @Description 登出

     */
    @ApiOperation(value = "退出登入")
    @PostMapping(value = "/outLogin")
    public GeneralResult outLogin() {
        return GeneralResult.success(appUserService.exit());
    }


    /**
     * @param
     * @Description 注销

     */
    @ApiOperation(value = "注销")
    @PostMapping(value = "/delUser")
    public GeneralResult delUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println("退出前-->"+user);
        SecurityUtils.getSubject().logout();
        System.out.println("退出后-->"+user);
        //return GeneralResult.success(appUserService.delUser());
        return GeneralResult.success("注销成功");
    }


    /**
     * @Description 实名认证

     */
    @ApiOperation(value = "实名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "真实姓名", dataType = "String"),
            @ApiImplicitParam(name = "cardNum", value = "身份证号", dataType = "String"),
            @ApiImplicitParam(name = "frontImage", value = "身份证正面", dataType = "String"),
            @ApiImplicitParam(name = "reverseImage", value = "身份证反面", dataType = "String")
    })
    @PostMapping(value = "/authIdentity")
    public GeneralResult authIdentity(@RequestBody UserDTO dto) {
        return GeneralResult.success(appUserService.authIdentity(dto));
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
    @PostMapping(value = "/findCustomer")
    public GeneralResult findCustomerByUserId(@RequestBody PageAction action) {
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
    public GeneralResult runRealAutheInfo(@RequestBody UserDTO dto) {
        return appUserService.runRealAutheInfo(dto);
    }

    /**
     * @Description 判断是否是新用户可以提前进入
     */
    @ApiOperation(value = "判断是否是新用户可以提前进入")
    @PostMapping(value = "/whetherNewUser")
    public GeneralResult whetherNewUser(String time) {
        return GeneralResult.success(appUserService.whetherNewUser(time));
    }

    /**
     * @Description 查询所有角色
     */
    @ApiOperation(value = "查询所有角色")
    @PostMapping(value = "/getRole")
    public GeneralResult getRole(){
        return GeneralResult.success(appUserService.geRole());
    }

    /**
     * @Description 添加角色
     */
    @ApiOperation(value = "添加角色")
    @PostMapping(value = "/addRole")
    public GeneralResult addRole(@RequestBody Role role){
        return GeneralResult.success(appUserService.addRole(role));
    }

    /**
     * @Description 修改角色
     */
    @ApiOperation(value = "修改角色")
    @PostMapping(value = "/updateRole")
    public GeneralResult updateRole(@RequestBody Role role){
        return GeneralResult.success(appUserService.updateRole(role));
    }

    /**
     * @Description 查询当前用户角色
     */
    @ApiOperation(value = "查询当前用户角色")
    @PostMapping(value = "/getUserRole")
    public GeneralResult getUserRole(){
        return GeneralResult.success(appUserService.getUserRole());
    }

    /**
     * @Description 根据用户查询角色
     */
    @ApiOperation(value = "根据用户查询角色")
    @GetMapping(value = "/getRoleByUser")
    public GeneralResult getRoleByUser(Long userId){
        return GeneralResult.success(appUserService.getRoleByUser(userId));
    }

}
