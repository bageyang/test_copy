package com.zj.auction.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@ApiModel(description = "竞拍区域表")
@Table(name = "zj_auction_area")
public class AuctionArea implements Serializable {
    private Long id;

    private String areaName;

    private Byte areaType;

    @JSONField(format = "HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm")
    @DateTimeFormat(pattern = "HH:mm")
    @ApiModelProperty(value = "开始时间")
    private LocalTime startTime;

    @JSONField(format = "HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm")
    @DateTimeFormat(pattern = "HH:mm")
    @ApiModelProperty(value = "结束时间")
    private LocalTime endTime;

    private Byte open;

    private String remark;

    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    private Byte isDeleted;

    private static final long serialVersionUID = 1L;
}