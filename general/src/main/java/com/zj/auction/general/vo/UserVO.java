package com.zj.auction.general.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户信息返回结果")
public class UserVO implements Serializable {

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "市区id")
    private Long cityId;

    @ApiModelProperty(value = "市区名称")
    private String city;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "角色类型")
    private Integer roleType;

    @ApiModelProperty(value = "分享图片")
    private String shareImg;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "常用电话")
    private String commonTel;

    @ApiModelProperty(value = "常用联系姓名")
    private String commonName;

    @ApiModelProperty(value = "用户头像")
    private String userImg;

    @ApiModelProperty(value = "用户类型，-1 后台管理 0普通用户，1 店主")
    private Integer userType;

    @ApiModelProperty(value = "浏览次数")
    private Long browseNum = 0L;

    @ApiModelProperty(value = "带看房次数")
    private Long withLookNum = 0L;

    @ApiModelProperty(value = "交易完成数量")
    private Long houseFinishNum = 0L;

    @ApiModelProperty(value = "优惠劵数量")
    private Long couponNum = 0L;

    @ApiModelProperty(value = "公司店铺名")
    private String storefrontName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "访问实际")
    private LocalDateTime visitTime;

    @ApiModelProperty(value = "银币余额")
    private BigDecimal silverBalance = BigDecimal.ZERO;

    @ApiModelProperty(value = "金币余额")
    private BigDecimal goldBalance = BigDecimal.ZERO;

    @ApiModelProperty(value = "店铺销售额")
    private BigDecimal saleTotal =BigDecimal.ZERO;

    @ApiModelProperty(value = "店铺佣金")
    private BigDecimal brokerage =BigDecimal.ZERO;

    @ApiModelProperty(value = "今天销量")
    private Integer saleVolume =0;

    @ApiModelProperty(value = "上级电话")
    private String pUserName;

    @ApiModelProperty(value = "店铺")
    private Long roleShopId;

    @ApiModelProperty(value = "未读数量")
    private Long unReadNum;

    @ApiModelProperty(value = "支付宝提现账户")
    private String alipayNum;

    @ApiModelProperty(value = "微信手开手机号")
    private String wxTel;

    @ApiModelProperty(value = "微信号")
    private String wxAccount;

    @ApiModelProperty(value = "支付宝账户姓名")
    private String aliName;

    @ApiModelProperty(value = "支付宝拍品收款账户")
    private String aliAccount;

    @ApiModelProperty(value = "是否团长 0 普通 1 团长")
    private Integer vipType = 0;

    @Transient
    @ApiModelProperty(value = "银行卡账号")
    private String bankNumber;

    @Transient
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "开户真实姓名")
    private String accountName;

    @ApiModelProperty(value = "下级数量")
    private Long subordinateNum;

    @ApiModelProperty(value = "下级等级")
    private Integer subordinateLevel;

    @ApiModelProperty(value = "提现额度")
    private BigDecimal withdrawalLimit;

    @ApiModelProperty(value = "注册时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "身份证")
    private String cardNumber;

    @ApiModelProperty(value = "分馆id")
    private Long tagId = 0L;

}

