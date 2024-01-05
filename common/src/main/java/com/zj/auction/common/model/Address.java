package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.auction.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@TableName("zj_address")
public class Address extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "收货地址编号")
    private Long addrId;

    private LocalDateTime addTime;

    @ApiModelProperty(value = "省,市, 区/县")
    private String addr1;

    @ApiModelProperty(value = "具体地址")
    private String addr2;

    @ApiModelProperty(value = "具体地址")
    private String addr3;

    @ApiModelProperty(value = "市对应的编号")
    private Integer cityId;

    @ApiModelProperty(value = "省对应的编号")
    private Integer provinceId;

    @ApiModelProperty(value = "区/县对应的编号")
    private Integer countyId;

    @ApiModelProperty(value = "是否默认地址，0非默认，1默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "收货人")
    private String name;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "状态")
    private Integer status = 0;

    @ApiModelProperty(value = "手机号")
    private String tel1;

    @ApiModelProperty(value = "固定电话")
    private String tel2;

    @ApiModelProperty(value = "类型")
    private Integer typeId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区/县")
    private String county;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "经度")
    private String longitude ;

    @ApiModelProperty(value = "纬度")
    private String latitude ;

    private static final long serialVersionUID = 1L;


}