package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.auction.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@TableName("zj_area")
public class Area extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "地区编号")
    private Long areaId;

    /**
     * 地区名
     */
    @ApiModelProperty(value = "地区名")
    private String areaName;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long pid;

    /**
     * 等级;1省级2市级3区县级
     */
    @ApiModelProperty(value = "等级;1省级2市级3区县级4街区")
    private Integer levelNum;

    /**
     * 拼音大写首字母
     */
    @ApiModelProperty(value = "拼音大写首字母")
    private String letter1;

    /**
     * 全小写拼音
     */
    @ApiModelProperty(value = "全小写拼音")
    private String letter2;

    /**
     * 被选次数
     */
    @ApiModelProperty(value = "被选次数")
    private Integer chooseNum;

    /**
     * 备用状态
     */
    @ApiModelProperty(value = "状态 0默认未开通 1开通")
    private Integer status;

    @ApiModelProperty(value = "状态 默认0不审核 1审核")
    private Integer registerControl = 0;

    /**
     * 备用备注
     */
    @ApiModelProperty(value = "备用备注")
    private String remakes;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "类型")
    private Integer typeId;


    @ApiModelProperty(value = "区域电话")
    private String tel;

    private String memo1;
    @ApiModelProperty(value = "子集")
    @Transient
    @TableField(exist = false)
    private List<Area> children;

    private static final long serialVersionUID = 1L;


}