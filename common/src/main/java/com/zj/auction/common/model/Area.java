package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@Data
@TableName("zj_area")
public class Area implements Serializable {
    private Long areaId;

    private String areaName;

    private Long pid;

    private Integer levelNum;

    private String letter1;

    private String letter2;

    private Integer chooseNum;

    private Integer status;

    private String remakes;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private Integer deleteFlag;

    private Integer typeId;

    private String memo1;

    private Long addUserId;

    private String transactionId;

    private Long updateUserId;

    private String tel;

    @ApiModelProperty(value = "子集")
    @Transient
    @TableField(exist = false)
    private List<Area> children;

    private static final long serialVersionUID = 1L;


}