package com.zj.auction.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zj.auction.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("zj_user")
public class User extends BaseEntity implements Serializable {
    /**
     * 用户id
     */
    @Id
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 角色id
     */
    @ApiModelProperty("角色ID")
    private Integer roleId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;


    @ApiModelProperty("密码")
    private String passWord;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;



    /**
     * 盐
     */
    @ApiModelProperty(value = "加密盐")
    private String salt;


    @ApiModelProperty(value = "性别:1:男; 0:女")
    private Integer sex;


    @ApiModelProperty(value = "状态,0,正常，1,冻结")
    private Integer status;


    @ApiModelProperty(value = "审核状态：0默认，1待审核，2审核通过，3拒绝审核")
    private Integer audit;


    @ApiModelProperty(value = "审核不通过原因")
    private String auditExplain;


    @ApiModelProperty(value = "父级id")
    private Long pid;


    @ApiModelProperty(value = "父级名称")
    private String pUserName;


    @ApiModelProperty(value = "所有父级")
    private String pidStr;


    @ApiModelProperty(value = "推荐等级")
    private Integer levelNum;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String tel;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;

    /**
     * 登录ip
     */
    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    /**
     * 登录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 当前用户类型
     */
    @ApiModelProperty(value = "当前用户类型")
    private Integer currentUserType;

    /**
     * 支付密码
     */
    @ApiModelProperty(value = "支付密码")
    private String payPassword;


    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer departmentId;





    @ApiModelProperty(value = "冻结说明")
    private String frozenExplain;



    /**
     * 网名
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 分享img
     */
    @ApiModelProperty(value = "分享图片")
    private String shareImg;


    @ApiModelProperty(value = "用户头像")
    private String userImg;


    @ApiModelProperty(value = "背景图片")
    private String backgroundImg;


    @ApiModelProperty(value = "是否首次登陆，0不是，1是")
    private Integer isFirstLogin;

    /**
     * 分享类型
     */
    @ApiModelProperty(value = "分享类型")
    private Integer shareType;

    /**
     * 商店id
     */
    @ApiModelProperty(value = "商铺id")
    private Long shopId;

    /**
     * wx联盟id
     */
    @ApiModelProperty(value = "用户在微信开发平台的唯一标识即openId")
    @Column(columnDefinition = "varchar(64) comment '用户在微信开发平台的唯一标识'")
    private String wxUnionId;

    /**
     * 最后登录时间
     */
    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;



    /**
     * 出生时间
     */
    private LocalDateTime birthTime;

//    /**
//     * 设备号
//     */
//    private String deviceNumber;

//    /**
//     * 独特num
//     */
//    private String uniqueNum;

    /**
     * 角色商店id
     */
    private Long roleShopId;

    /**
     * 作用范围
     */
    @Enumerated(EnumType.ORDINAL)
    private Integer roleRange;

//    /**
//     * 在线状态
//     */
//    private Integer lineStatus;
//
//    /**
//     * 咨询费用
//     */
//    private BigDecimal consultingFee;
//
//    /**
//     * 评论总数
//     */
//    private Integer commentTotal;
//
//    /**
//     * 喜欢总数
//     */
//    private Integer likeTotal;


    @ApiModelProperty(value = "抽奖次数")
    private Integer luckyDrawNum;


    @ApiModelProperty(value = "支付宝账户")
    private String alipayNum;

    /**
     * wx电话
     */
    @ApiModelProperty(value = "wx电话")
    private String wxTel;

    /**
     * wx帐户
     */
    @ApiModelProperty(value = "wx帐户")
    private String wxAccount;

    /**
     * 阿里名字
     */
    @ApiModelProperty(value = "支付宝账户姓名")
    private String aliName;

    /**
     * 阿里账户
     */
    @ApiModelProperty(value = "支付宝拍品收款账户")
    private String aliAccount;

    /**
     * 封号到期时间
     */
    @ApiModelProperty(value = "封号到期时间")
    private LocalDateTime sealExpirationTime;


    @ApiModelProperty(value = "现金每场抢单限定")
    private Integer robOrderLimit;


    @ApiModelProperty(value = "提现额度")
    private BigDecimal withdrawalLimit;


    @ApiModelProperty(value = "完成拍品数量")
    private Integer finishAuction;


    @ApiModelProperty(value = "老客户几次上浮")
    private Integer comeUpNum;


    @ApiModelProperty(value = "是否团长 0 普通 1 vip 2 馆长")
    private Integer vipType;


    @ApiModelProperty(value = "分馆id")
    private Long tagId;

    @ApiModelProperty(value = "下级等级")
    private Integer subordinateLevel;


    @ApiModelProperty(value = "下级数量")
    private Long subordinateNum;


    @ApiModelProperty(value = "封禁次数")
    private Integer sealNum;

    /**
     * 钻石订单限制
     */
    @ApiModelProperty(value = "钻石每场抢单限定")
    private Integer diamondOrderLimit;


    @ApiModelProperty(value = "常用联系人姓名")
    private String commonName;


    @ApiModelProperty(value = "常用联系人电话")
    private String commonTel;


    @ApiModelProperty(value = "身份证")
    private String cardNumber;

    /**
     * 备忘录
     */
    private String memo;
    private String memo1;
    private String memo2;
    private String memo3;

    @ApiModelProperty(value = "直推人数")
    private Integer subUserNum;

    @ApiModelProperty(value = "推荐总人数")
    private Integer subUserNumAll;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型:0普通用户，1管理员")
    private Integer userType;

    private static final long serialVersionUID = 1L;


}