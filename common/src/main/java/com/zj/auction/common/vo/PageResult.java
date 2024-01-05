package com.zj.auction.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页数据
 */
@Data
public class PageResult<T> {

    public Long currentPage;

    public Long count;

    public List<T> data;

    public String message;

    public Long pages;


}
