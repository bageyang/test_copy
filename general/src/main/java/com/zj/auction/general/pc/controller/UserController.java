package com.zj.auction.general.pc.controller;

import com.github.pagehelper.PageInfo;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.constant.SystemConfig;
import com.zj.auction.common.dto.UserDTO;
import com.zj.auction.common.model.Permis;
import com.zj.auction.common.model.User;
import com.zj.auction.general.shiro.SecurityUtils;
import com.zj.auction.common.util.RedisUtil;
import com.zj.auction.general.pc.service.UserService;
import com.zj.auction.common.vo.GeneralResult;
import com.zj.auction.common.vo.PageAction;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 *************************************************
 *	控制层 用户操作
 * @author  YW
 * @version 1.0
 * @date    2022年6月7日16:38:35
 * @See
 *************************************************
 */
@Api(value="PC用户接口",tags={"PC用户接口"})
@RestController
@RequestMapping(value="/pc/user")
public class UserController{

    @Autowired
    private UserService pcUserServer;


    @PostMapping("/test")
    public GeneralResult listMemberIndirect(@RequestBody UserDTO dto){
        return GeneralResult.success(pcUserServer.listMemberIndirect(dto));
    }

    /**
     * pc登录
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link GeneralResult}
     *///    @ReqLimit(rateSecond =4)
    @PostMapping(value="/userLogin")
    public GeneralResult getPcLogin(@RequestParam String username, @RequestParam String password) {
        return GeneralResult.success(pcUserServer.getPcLogin(username,password));
    }




    /**
     * @Description 登录后根据角色获取左侧菜单，未通过shiro获取
     * @Title toIndex
     * @Author YW
     * @Date 2022年6月7日16:40:33
     * @param
     * @return
     */
    @PostMapping(value="/getUserAuthority")
    public GeneralResult getUserAuthority() {
        User user = SecurityUtils.getPrincipal();
        List<Permis> listResult = pcUserServer.findByMenuId(user.getUserId());
        return GeneralResult.success(listResult);
    }


    /**
     * 得到管理员名单
     *
     * @param pageAction 页面动作
     * @return {@link GeneralResult}
     */
    @PostMapping(value="/getManagerPage")
    public GeneralResult getManagerList(@RequestBody PageAction pageAction) {
        return pcUserServer.getManagerList(pageAction);
    }



    /**
     * @Title: saveManager
     * @Description: 添加管理员
     * @author： YW
     * @date： 2022年6月7日16:41:10
     * @param userCfg
     * @return
     * @return：GeneralResult
     *
     */
//    @SystemLog
    @PostMapping(value="/createManager")
    public GeneralResult createManager(User userCfg) {
        return GeneralResult.success(pcUserServer.createManager(userCfg));
    }



    /**
     * @Title: findManagerByUserId
     * @Description: 根据用户id查询管理员
     * @author：Mao W
     * @date： 2022年6月7日16:41:35
     * @param userCfg
     * @return：GeneralResult
     */
    @PostMapping(value="/findManagerByUserId")
    public GeneralResult findManagerByUserId(User userCfg) {
        GeneralResult result = new GeneralResult();
        result.setResult(pcUserServer.findManagerByUserId(userCfg));
        return result;
    }

    /**
     * @Title: udpateManager
     * @Description: 修改管理员
     * @author：Mao W
     * @date： 2022年6月7日16:41:43
     * @param userCfg
     * @return：GeneralResult
     *
     */
//    @SystemLog
    @RequiresPermissions("manager:update")
    @PostMapping(value="/updateManager")
    public GeneralResult updateManager(UserDTO userCfg) {
        return GeneralResult.success(pcUserServer.updateManager(userCfg));
    }



    /**
     * @Title: deleteManager
     * @Description: 单个删除
     * @author：Mao Qi
     * @date： 2019年7月3日下午7:22:06
     * @param userCfg
     * @return：GeneralResult
     *
     */
//    @SystemLog
    @RequiresPermissions(value = {"manager:delete","member:delete"})
    @PostMapping(value="/deleteUser")
    public GeneralResult deleteUser(User userCfg) {
        return GeneralResult.success(pcUserServer.deleteUser(userCfg));
    }

    /**
     * @Title: updateManagerStatus
     * @Description: 修改管理员状态（启用或者禁用）
     * @author：Mao Qi
     * @date： 2019年7月3日下午7:22:59
     * @param userCfg
     * @return
     * @return：GeneralResult
     */
//    @SystemLog
    @RequiresPermissions("manager:updateStatus")
    @PostMapping(value="/updateManagerStatus")
    public GeneralResult updateManagerStatus(User userCfg) {
        return GeneralResult.success(pcUserServer.updateManagerStatus(userCfg));
    }


    /*----------------------------APP会员信息-----------------------------*/


    /**
     * @Description 后台查询用户列表
     * @Title getUserPage
     * @Author Mao Qi
     * @Date 2021/2/2 22:44
     * @param pageAction
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @PostMapping(value="/getUserPage")
    public GeneralResult getUserPage(PageAction pageAction, List<Long> userIds) {
        PageInfo<User> page = pcUserServer.getUserPage(pageAction,userIds);
        pageAction.setTotalCount((int)page.getTotal());
        return GeneralResult.success(page.getList(),pageAction);
    }

    /**
     * @title: getSubUserPage
     * @description: 根据用户id查询直接下级
     * @author: Mao Qi
     * @date: 2019年7月22日上午11:45:36
     * @param pageAction
     * @param pid 父级用户id
     * @return: GeneralResult
     */
    @RequiresPermissions(value="member:listDirect")
    @PostMapping(value="/getSubUserPage")
    public GeneralResult getSubUserPage(PageAction pageAction, Long pid) {
        return pcUserServer.getSubUserPage(pageAction,pid);
    }

    /**
     * @Description 修改会员状态
     * @Title updateUserStatus
     * @Author Mao Qi
     * @Date 2021/2/3 15:59
     * @param userId
     * @param status
     * @param frozenExplain
     * @return	com.duoqio.common.vo.GeneralResult
     */
//    @SystemLog
    @RequiresPermissions(value="user:disable")
    @PostMapping(value="/updateUserStatus")
    public GeneralResult updateUserStatus(Long userId, Integer status,String frozenExplain) {
        return GeneralResult.success(pcUserServer.updateUserStatus(userId,status,frozenExplain));
    }





    /**
     * @Description 导出会员
     * @Title exportUser
     * @Author Mao Qi
     * @Date 2020/10/21 10:41
     * @param pageAction
     * @param httpServletResponse
     * @return	void
     */
//    @SystemLog
    @RequiresPermissions(value="member:export")
    @PostMapping("/exportUser")
    public void exportUser(PageAction pageAction,Integer userType,String userIds, HttpServletResponse httpServletResponse){
        pcUserServer.exportUser(pageAction,userType,userIds, httpServletResponse);
    }

    /**
     * @Description 根据id查询会员信息
     * @Title getUserByUserId
     * @Author Mao Qi
     * @Date 2019/10/23 12:37
     * @param userId
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
    @PostMapping(value="/getUserByUserId")
    public GeneralResult getUserByUserId(Long userId) {
        GeneralResult result = new GeneralResult();
        User userCfg = pcUserServer.getUserByUserId(userId);
        result.setResult(userCfg);
        return result;
    }


    /**
     * @title: deleteMember
     * @description: 删除用户
     * @author: Mao Qi
     * @date: 2020年4月3日上午10:50:45
     * @param userId
     * @return: GeneralResult
     */
//    @SystemLog
    @PostMapping(value="/deleteMember")
    public GeneralResult deleteMember(Long userId) {
        GeneralResult result = new GeneralResult();
        User userCfg = new User();
        pcUserServer.deleteMember(userId);
        return result;
    }



    /**
     * @Title: removeSession
     * @Description: 退出登录清除session
     * @author：Mao Qi
     * @date： 2020年4月2日下午5:13:31
     * @return：GeneralResult
     *
     */
    @PostMapping(value="/userLogout")
    public GeneralResult removeSession() {
        User user =  SecurityUtils.getPrincipal();
        SecurityUtils.logout();
        RedisUtil.del(RedisConstant.PC_USER_TOKEN+user.getUserId());

        return GeneralResult.success();
    }
    /**
     * @Description 获取token
     * @Title token
     * @Author Mao Qi
     * @Date 2019/10/31 19:31
     * @param
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
    @PostMapping(value="/token")
    public GeneralResult token(){
        return GeneralResult.success();
    }

    @PostMapping(value="/tokenOk")
    public GeneralResult tokenOk() {
        return GeneralResult.success();
    }


    /**
     * @title: listArea
     * @description: 查询地区
     * @author: Mao Qi
     * @date: 2019年7月16日下午8:06:32
     * @return: List<Map<String,Object>>
     */
    @PostMapping(value="listArea")
    public GeneralResult listArea() {
        GeneralResult result = new GeneralResult();
        result.setResult(pcUserServer.listArea());
        return result;
    }


    /**
     * @Description 变更资金
     * @Title changeIntegral
     * @Author Mao Qi
     * @Date 2019/10/16 17:38
     * @param userId
     * @param type
     * @param integral
     * @param remark
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
//    @SystemLog
    @RequiresPermissions(value="member:changeBalance")
    @PostMapping("/changeBalance")
    public GeneralResult changeBalance(Long userId,Integer moneyType,Integer type,BigDecimal integral,String remark) {
        return GeneralResult.success(pcUserServer.changeBalance(userId,moneyType,type,integral,remark));
    }

    /**
     * @Description 获取当前用户信息
     * @Title getUserData
     * @Author Mao Qi
     * @Date 2019/9/17 17:47
     * @param
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
    @PostMapping(value="/getUserData")
    public GeneralResult getUserData(){
        GeneralResult result = new GeneralResult();
        User user =  SecurityUtils.getPrincipal();
        result.setResult(user);
        result.setToken(SystemConfig.getSysSoftwareName());
        return result;
    }

    /**
     * @Description 实名审核
     * @Title updateAuditRejection
     * @Author Mao Qi
     * @Date 2020/4/5 23:07
     * @param userId
     * @param auditExplain
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
//    @SystemLog
    @PostMapping(value="/updateAuditRejection")
    public GeneralResult updateAuditRejection(Long userId,String auditExplain) {
        GeneralResult result = new GeneralResult();
        Integer integer = pcUserServer.updateAuditRejection(userId, auditExplain);
        result.setCode(integer>0?0:-1);
        return result;
    }


    /**
     * @Description 审核通过
     * @Title updateAuditApproval
     * @Author Mao Qi
     * @Date 2020/4/5 23:10
     * @param userId
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
//    @SystemLog
    @PostMapping(value="/updateAuditApproval")
    public GeneralResult updateAuditApproval(Long userId) {
        return GeneralResult.success(pcUserServer.updateAuditApproval(userId));
    }


    /**
     * @Description 注册统计
     * @Title statisticalMoney
     * @Author Mao Qi
     * @Date 2020/12/30 21:05
     * @return	com.duoqio.boot.framework.vo.GeneralResult
     */
    @PostMapping("/statistics")
    public GeneralResult statistics() {
        return pcUserServer.statistics();
    }



    /**
     * @Description PC修改个人头像和密码
     * @Title updatePassOrImg
     * @Author Mao Qi
     * @Date 2021/3/8 15:12
     * @param userImg
     * @param oldPass
     * @param newPass
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @PostMapping("/updatePassOrImg")
    public GeneralResult updatePassOrImg(String userImg, String oldPass , String newPass) {
        return pcUserServer.updatePassOrImg(userImg,oldPass,newPass);
    }

    /**
     * @Description 修改用户现金每场竞拍次数
     * @Title updRobOrderLimit
     * @Author Mao Qi
     * @Date 2021/11/17 15:22
     * @param userId
     * @param remarks
     * @return	com.duoqio.common.vo.GeneralResult
     */
    @PostMapping("/updRobOrderLimit")
    public GeneralResult updRobOrderLimit(Long userId, Integer remarks) {
        return pcUserServer.updRobOrderLimit(userId,remarks);
    }

    /**
     * @param userId  用户id
     * @param remarks 备注
     * @return {@link GeneralResult}
     * @Description 修改用户钻石每场竞拍次数
     */
    @PostMapping("/updDiamondOrderLimit")
    public GeneralResult updDiamondOrderLimit(Long userId, Integer remarks) {
        return pcUserServer.updDiamondOrderLimit(userId,remarks);
    }

    /**
     * @param userId  用户id
     * @param remarks 备注
     * @return {@link GeneralResult}
     * @Description 修改用户每场竞拍次数
     */
    @PostMapping("/updWithdrawalLimit")
    public GeneralResult updWithdrawalLimit(Long userId, BigDecimal remarks) {
        return pcUserServer.updWithdrawalLimit(userId,remarks);
    }


    /**
     * 设置团长
     *
     * @param userId  用户id
     * @param vipType vip类型
     * @param tagId   标签id
     * @return {@link GeneralResult}
     */
    @PostMapping("/updVipType")
    public GeneralResult updVipType(Long userId, Integer vipType, Long tagId) {
        return pcUserServer.updVipType(userId,vipType,tagId);
    }
}
