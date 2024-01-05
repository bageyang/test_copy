package com.zj.auction.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseCondition {

    @ApiModelProperty(value = "分页页号")
    private int page = 1;

    @ApiModelProperty(value = "分页大小")
    private int pageSize = 10;

    @ApiModelProperty(value = "主键")
    private String id;



    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否禁用",example = "是否禁用")
    private Integer isDisable;

    @ApiModelProperty(value = "是否已删除",example = "是否已删除")
    private Integer isDelete;
    @ApiModelProperty(value = "最后一次更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "是否时间区间查询,1添加大于等于startTime 小于等于endTime为条件,其他值或null无效",example = "是否时间区间查询,1添加大于等于startTime 小于等于endTime为条件,其他值或null无效")
    private Integer betweenByCreatTime;
    @ApiModelProperty(value = "排序从句",example = "排序从句")
    private String clause;
    @ApiModelProperty(value = "开始时间",example = "开始时间")
    private String StartTime;
    @ApiModelProperty(value = "结束时间",example ="结束时间" )
    private String EndTime;

}
