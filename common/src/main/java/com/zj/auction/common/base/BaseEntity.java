package com.zj.auction.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {




    @ApiModelProperty(value = "删除标志  默认0")
    private Integer deleteFlag;


    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "创建者")
    private Long addUserId;

    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者")
    private Long updateUserId;

    @ApiModelProperty(value = "事务ID")
    private String transactionId;



}
