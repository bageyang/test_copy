package com.zj.auction.common.query;

import java.util.Objects;

public class PageQuery {
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return getSuitablePageNum();
    }

    private Integer getSuitablePageNum() {
        return (Objects.isNull(pageNum) || pageNum < 1) ? 1 : pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return getSuitablePageSize();
    }

    private Integer getSuitablePageSize() {
        return (Objects.isNull(pageSize) || pageSize < 1) ? 10 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
