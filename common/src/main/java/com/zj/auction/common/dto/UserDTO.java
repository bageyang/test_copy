package com.zj.auction.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 胖胖不胖
 */
@Data
public class UserDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * mima
     */
    private String passWord;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 盐
     */
    private String salt;

    /**
     * 性
     */
    private Integer sex;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 审计
     */
    private Integer audit;

    /**
     * 审计解释
     */
    private String auditExplain;

    /**
     * pid
     */
    private Long pid;

    /**
     * p用户名
     */
    private String pUserName;

    /**
     * pid str
     */
    private String pidStr;

    /**
     * 水平num
     */
    private Integer levelNum;

    /**
     * 电话
     */
    private String tel;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 当前用户类型
     */
    private Integer currentUserType;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 添加时间
     */
    private LocalDateTime addTime;

    /**
     * 添加用户id
     */
    private Long addUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新用户id
     */
    private Long updateUserId;

    /**
     * 冰冻解释
     */
    private String frozenExplain;

    /**
     * 删除标记
     */
    private Integer deleteFlag;

    /**
     * 网名
     */
    private String nickName;

    /**
     * 分享img
     */
    private String shareImg;

    /**
     * 用户img
     */
    private String userImg;

    /**
     * 背景img
     */
    private String backgroundImg;

    /**
     * 是否第一次登录
     */
    private Integer isFirstLogin;

    /**
     * 分享类型
     */
    private Integer shareType;

    /**
     * 商店id
     */
    private Long shopId;

    /**
     * wx联盟id
     */
    private String wxUnionId;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 事务id
     */
    private String transactionId;

    /**
     * 出生时间
     */
    private LocalDateTime birthTime;

    /**
     * 设备号
     */
    private String deviceNumber;

    /**
     * 独特num
     */
    private String uniqueNum;

    /**
     * 角色商店id
     */
    private Long roleShopId;

    /**
     * 作用范围
     */
    private Integer roleRange;

    /**
     * 在线状态
     */
    private Integer lineStatus;

    /**
     * 咨询费用
     */
    private BigDecimal consultingFee;

    /**
     * 评论总数
     */
    private Integer commentTotal;

    /**
     * 喜欢总数
     */
    private Integer likeTotal;

    /**
     * 幸运抽奖num
     */
    private Integer luckyDrawNum;

    /**
     * 支付宝num
     */
    private String alipayNum;

    /**
     * wx电话
     */
    private String wxTel;

    /**
     * wx帐户
     */
    private String wxAccount;

    /**
     * 阿里名字
     */
    private String aliName;

    /**
     * 阿里账户
     */
    private String aliAccount;

    /**
     * 印章过期时间
     */
    private LocalDateTime sealExpirationTime;

    /**
     * rob顺序限制
     */
    private Integer robOrderLimit;

    /**
     * 取款限额
     */
    private BigDecimal withdrawalLimit;

    /**
     * 完成拍卖
     */
    private Integer finishAuction;

    /**
     * 提出num
     */
    private Integer comeUpNum;

    /**
     * vip类型
     */
    private Integer vipType;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 下属等级
     */
    private Integer subordinateLevel;

    /**
     * 全国矿工工会下属
     */
    private Long subordinateNum;

    /**
     * 密封num
     */
    private Integer sealNum;

    /**
     * 钻石订单限制
     */
    private Integer diamondOrderLimit;

    /**
     * 普通名字
     */
    private String commonName;

    /**
     * 常见电话
     */
    private String commonTel;

    /**
     * 备忘录
     */
    private String memo;

    /**
     * 用户类型
     */
    private Integer userType;

    private String address;

    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endDate;

    private String userIds;

    private String cardNum;

    private String frontImage;

    private String reverseImage;

    private Integer page;
    private Integer pageSize;
}
