package com.zj.auction.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Mao Qi
 * @describe 分页信息
 * @title PageResp.java
 * @date 2019年7月18日 下午4:15:35
 */
@Data
@ApiModel(value = "分页信息")
public class PageResp<T>   {
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "分页内容")
    private List<T> content;
    @ApiModelProperty(value = "页码")
    private int pageNumber;
    @ApiModelProperty(value = "条目")
    private int pageSize;
    @ApiModelProperty(value = "总数")
    private long total;
    @ApiModelProperty(value = "总页数")
    private int totalPages;

    /**
     * 创建一个新的实例 PageVO.
     */
    public PageResp() {
        super();
    }

    /**
     * 创建一个新的实例 PageVO.
     *
     * @param content    content
     * @param total      total
     * @param pageNumber pageNumber
     * @param pageSize   pageSize
     */
    public PageResp(List<T> content, long total, int pageNumber, int pageSize) {
        super();
        this.content = content;
        this.total = total;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = pageSize == 0 ? 1 : (int) Math.ceil(total / pageSize);
    }

    /**
     * @param <T>        T
     * @param content    content
     * @param total      total
     * @param pageNumber pageNumber
     * @param pageSize   pageSize
     * @describe build
     * @title build
     * @author Mao Qi
     * @date 2019年7月18日 下午4:20:38
     */
    public static <T> PageResp<T> build(List<T> content, long total, int pageNumber, int pageSize) {
        return new PageResp<>(content, total, pageNumber, pageSize);
    }
}
