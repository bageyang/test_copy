package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.auction.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("zj_bank")
public class Bank extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "银行卡编号")
    private Long bankId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "关联的用户id")
    private Long userId;

    @ApiModelProperty(value = "收款方开户行")
    private String bankCode;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String bankNumber;
    /**
     * 开户姓名
     */
    @ApiModelProperty(value = "开户真实姓名")
    private String accountName;
    /**
     * 银行预留手机号
     */
    @ApiModelProperty(value = "银行预留手机号")
    private String tel;
    /**
     * 银行logo
     */
    @ApiModelProperty(value = "银行logo")
    private String bankLogo;

    private static final long serialVersionUID = 1L;

}