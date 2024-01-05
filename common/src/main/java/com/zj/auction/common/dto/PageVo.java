package com.zj.auction.common.dto;

import com.github.pagehelper.Page;
import com.zj.auction.common.vo.PageAction;

import java.util.List;


public class PageVo<T> {
    private List<T> records;
    private long total;
    private long pages;


    public static <T> PageVo<T> of(T data, PageAction pageAction) {
        PageVo<T> page = new PageVo<>();
        page.setRecords((List<T>) data);
        page.setTotal(pageAction.getTotalCount());
        page.setPages(pageAction.getPageSize());
        return page;
    }

    public static <T> PageVo<T> of(Page<T> pageh) {
        PageVo<T> page = new PageVo<>();
        page.setRecords(pageh.getResult());
        page.setTotal(pageh.getTotal());
        page.setPages(pageh.getPages());
        return page;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }
}
