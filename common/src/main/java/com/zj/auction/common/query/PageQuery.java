package com.zj.auction.common.query;

import java.util.Objects;

public class PageQuery {
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return getSuitablePageNum();
    }

    private Integer getSuitablePageNum() {
        if(Objects.isNull(pageNum) || pageNum < 1){
            setPageNum(1);
        }else {
            setPageNum(pageNum);
        }
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return getSuitablePageSize();
    }

    private Integer getSuitablePageSize() {
        if(Objects.isNull(pageSize) || pageSize < 1){
            setPageSize(10);
        }else {
            setPageSize(pageSize);
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
