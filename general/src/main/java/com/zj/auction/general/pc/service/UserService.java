package com.zj.auction.general.pc.service;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zj.auction.common.model.Permis;
import com.zj.auction.common.model.SystemCnf;
import com.zj.auction.common.model.User;
import com.zj.auction.general.vo.GeneralResult;
import com.zj.auction.general.vo.LoginResp;
import com.zj.auction.general.vo.PageAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title PcUserService
 * @Package com.duoqio.boot.business.pc.service.userInfo
 * @Description 用户业务接口
 * @Author Mao Qi
 * @Date 2019/9/4 14:21
 * @Copyright 重庆多企源科技有限公司
 * @Website {http://www.duoqio.com/index.asp?source=code}
 */
public interface UserService  {

    /**
     * @Description pc登录
     * @Title getPcLogin
     * @Author Mao Qi
     * @Date 2021/1/15 11:09
     * @param userName
     * @param passWord
     * @return	com.duoqio.common.vo.LoginResp
     */
    LoginResp getPcLogin(String userName, String passWord);



    /**
     * 	查询SystemConfig数据
     * @param parameter
     * @return
     */
    List<Map<String, Object>> getSystemConfig(String parameter);


    /**
     * @Description 根据用户id查找菜单
     * @Title findByMenuId
     * @Author Mao Qi
     * @Date 2019/9/4 14:22
     * @param userId
     * @return	java.util.List<com.duoqio.boot.business.entity.SysPermisTbl>
     */
    List<Permis> findByMenuId(Long userId);

    /**
     * @Title: getManagerList
     * @Description: 平台管理员list
     * @author：Mao Qi
     * @date： 2019年7月3日下午5:02:55
     * @param pageAction
     * @return：GeneralResult
     *
     */
    GeneralResult getManagerList(PageAction pageAction);

    /**
     * @Title: saveManager
     * @Description: 添加管理员
     * @author：Mao Qi
     * @date： 2019年7月3日下午5:43:55
     * @param userCfg
     * @return
     * @return：Boolean
     *
     */
    Boolean createManager(User userCfg);

    /**
     * @Title: updateManager
     * @Description: 修改管理员
     * @author：Mao Qi
     * @date： 2019年7月3日下午5:43:55
     * @param userCfg
     * @return
     * @return：Boolean
     *
     */
    Boolean updateManager(User userCfg);

    /**
     * @Title: deleteManager
     * @Description: 删除管理员
     * @author：Mao Qi
     * @date： 2019年7月3日下午6:08:51
     * @param userCfg
     * @return
     * @return：Boolean
     *
     */
    Boolean deleteUser(User userCfg);

    /**
     * @Title: findManagerByUserId
     * @Description:根据id查询管理员信息
     * @author：Mao Qi
     * @date： 2019年7月3日下午6:11:22
     * @return
     * @return：UserCfg
     *
     */
    User findManagerByUserId(User userCfg);

    /**
     * @Title: updateManagerStatus
     * @Description: 修改管理员状态（启用或禁用）
     * @author：Mao Qi
     * @date： 2019年7月3日下午6:09:24
     * @param userCfg
     * @return
     * @return：Boolean
     *
     */
    Boolean updateManagerStatus(User userCfg);


    /*----------------------------APP会员信息-----------------------------*/


    /**
     * @Description 查询平台管理员
     * @Title getUserPage
     * @Author Mao Qi
     * @Date 2021/2/2 22:43
     * @param pageAction
     * @return	com.duoqio.common.vo.GeneralResult
     */
    PageInfo<User> getUserPage(PageAction pageAction, Integer userType, List<Long> userIds);



    /**
     * @Description 根据用户id查询直接下级
     * @Title listMemberDirect
     * @Author Mao Qi
     * @Date 2019/10/12 13:49
     * @param pageAction
     * @param pid
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
    GeneralResult getSubUserPage(PageAction pageAction, Long pid);

    /**
     * @Description 根据用户id查询间接下级
     * @Title listMemberIndirect
     * @Author Mao Qi
     * @Date 2019/10/12 13:49
     * @param pageAction
     * @param maps
     *
     * @return	com.duoqio.boot.framework.result.GeneralResult
     */
    GeneralResult listMemberIndirect(PageAction pageAction, HashMap<String, Object> maps);

    /**
     * @title: findMemberAudit
     * @description: 查询待审核会员
     * @author: Mao Qi
     * @date: 2020年4月3日下午5:50:35
     * @param pageAction
     * @param map
     * @return: GeneralResult
     */
    GeneralResult findMemberAudit(PageAction pageAction, HashMap<String, Object> map);


    /**
     * 	添加用户
     * @param userCfg
     * @return
     */
    Boolean insertUser(User userCfg);

    /**
     * 	查看用户信息
     * @param userId
     * @return
     */
    User getUserByUserId(Long userId);


    /**
     * @Description 修改会员状态
     * @Title updateUserStatus
     * @Author Mao Qi
     * @Date 2021/2/3 15:28
     * @param userId
     * @param status
     * @param frozenExplain
     * @return	java.lang.Boolean
     */
    Boolean updateUserStatus(Long userId, Integer status,String frozenExplain);
    /**
     * @title: deleteMember
     * @description: 删除多个会员
     * @author: Mao Qi
     * @date: 2020年4月3日上午10:53:05
     * @param userCfg
     * @return: Boolean
     */
    Boolean deleteMember(User userCfg);

    /**
     * @Description 根据用户id查询统计数据
     * @Title getStatistics
     * @Author Mao Qi
     * @Date 2019/10/16 17:39
     * @param userCfg
     * @return	java.util.HashMap<java.lang.String,java.lang.Object>
     */
    HashMap<String, Object> getStatistics(User userCfg);

    /**
     * @Description 分页查询系统配置
     * @Title findParameter
     * @Author Mao Qi
     * @Date 2021/2/3 16:14
     * @param pageAction
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult findParameter(PageAction pageAction);

    /**
     * @Description 查看所有自定义参数配置
     * @Title getAllCfgs
     * @Author Mao Qi
     * @Date 2021/2/3 16:13
     * @param
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult getAllCfgs();

    /**
     * @Description 修改自定义参数配置
     * @Title updateCfg
     * @Author Mao Qi
     * @Date 2021/2/3 16:14
     * @param systemCfg
     * @return	java.lang.Integer
     */
    Integer updateCfg(SystemCnf systemCfg) ;

    /**
     * @Description 修改自定义参数配置2 负载均衡
     * @Title updateCfg
     * @Author Mao Qi
     * @Date 2021/2/3 16:14
     * @return	java.lang.Integer
     */
    Integer updateCfgTwo( String keyValue,  String keyId, String memo,String keyName) throws Exception;

    /**
     * @title: listArea
     * @description: 查询地区
     * @author: Mao Qi
     * @date: 2019年7月16日下午8:06:32
     * @return: Map<String, Object>
     */
    Map<String, Object> listArea();

    /**
     * @title: updateAuditRejection
     * @description: 会员审核拒绝通过
     * @author: Mao Qi
     * @date: 2020年4月3日下午7:19:17
     * @param userId
     * @param auditExplain
     * @return: Integer
     */
    Integer updateAuditRejection(Long userId, String auditExplain);

    /**
     * @Description 实名通过审核
     * @Title updateAuditApproval
     * @Author Mao Qi
     * @Date 2020/4/5 23:12
     * @param userId
     * @return	java.lang.Integer
     */
    Integer updateAuditApproval(Long userId);

    /**
     * @Description 变更资金
     * @Title changeIntegral
     * @Author Mao Qi
     * @Date 2019/10/16 17:37
     * @param userId
     * @param type
     * @param integral
     * @param remark
     * @return	java.lang.Integer
     */
    Integer changeBalance(Long userId,Integer moneyType, Integer type, BigDecimal integral, String remark);


    /**
     * @Description 变更卡劵
     * @Title changeCardVolume
     * @Author Mao Qi
     * @Date 2019/10/28 9:45
     * @param userId
     * @param type
     * @param cardVolumeTotal
     * @param remark
     * @return	java.lang.Integer
     */
    Integer changeCardVolume(Long userId, Integer type, BigDecimal cardVolumeTotal, String remark);

    /**
     * @Description 条件查询APP用户列表
     * @Title findAPPUser
     * @Author Mao Qi
     * @Date 2019/9/16 21:03
     * @param keyword
     * @param pageable
     * @return	org.springframework.data.domain.Page<com.duoqio.boot.business.entity.UserInfoTbl>
     */
    Page<User> findAPPUser(String keyword, Pageable pageable);

    /**
     * @Description 根据用户名查找用户
     * @Title findByDeleteFlagFalseAndUserName
     * @Author Mao Qi
     * @Date 2020/5/13 22:04
     * @param userName
     * @return	com.duoqio.entity.pojo.UserInfoTbl
     */
    User findByDeleteFlagFalseAndUserName(String userName);

    /**
     * @param pageAction
     * @param httpServletResponse
     * @Description 导出经纪人
     * @Title exportBroker
     * @Author Mao Qi
     * @Date 2020/10/21 9:18
     * @return void
     */
    void exportUser(PageAction pageAction,Integer userType,String userIds, HttpServletResponse httpServletResponse);


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
     * @Description 根据id查询用户信息
     * @Title findUserById
     * @Author Mao Qi
     * @Date 2021/1/21 13:55
     * @param userId
     * @return	com.duoqio.entity.pojo.UserInfoTbl
     */
    User findUserById(Long userId);

    /**
     * @Description 注册统计
     * @Title statistics
     * @Author Mao Qi
     * @Date 2021/2/3 17:35
     * @param
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult statistics();

    /**
     * @Description 注册K线统计
     * @Title registerKLine
     * @Author Mao Qi
     * @Date 2021/2/4 9:42
     * @param
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult registerKLine();

    /**
     * @Description PC修改个人头像和密码
     * @Title updatePassOrImg
     * @Author Mao Qi
     * @Date 2021/3/8 15:13
     * @param userImg
     * @param oldPass
     * @param newPass
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult updatePassOrImg(String userImg, String oldPass, String newPass);

    /**
     * @Description 修改用户每场竞拍次数
     * @Title updRobOrderLimit
     * @Author Mao Qi
     * @Date 2021/11/17 15:22
     * @param userId
     * @param robOrderLimit
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult updRobOrderLimit(Long userId, Integer robOrderLimit);

    /**
     * @Description 修改用户钻石每场竞拍次数
     * @Title updDiamondOrderLimit
     * @Author Mao Qi
     * @Date 2022/3/29 10:20
     * @param userId
     * @param diamondOrderLimit
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult updDiamondOrderLimit(Long userId, Integer diamondOrderLimit);

    /**
     * @Description 修改提现额度
     * @Title updWithdrawalLimit
     * @Author Mao Qi
     * @Date 2022/2/21 16:58
     * @param userId
     * @param withdrawalLimit
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult updWithdrawalLimit(Long userId, BigDecimal withdrawalLimit);

    /**
     * @Description 修改用户vip
     * @Title updVipType
     * @Author Mao Qi
     * @Date 2021/11/18 21:48
     * @param userId
     * @param vipType
     * @return	com.duoqio.common.vo.GeneralResult
     */
    GeneralResult updVipType(Long userId, Integer vipType, Long tagId);
}
