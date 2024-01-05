package com.zj.auction.general.vo;

/**
 * ************************************************
 * 分页实体
 *
 * @author ?->MengDaNai
 * @version 1.0
 * @date 2019年2月18日 创建文件
 * @See ************************************************
 */
public class PageAction {

    /**
     * 记录总数
     **/
    protected Integer totalCount = 0;

    /**
     * 总页数
     **/
    protected Integer totalPage = 0;

    /**
     * 每页记录数
     **/
    protected Integer pageSize = 10;

    /**
     * 当前页数
     **/
    protected Integer currentPage = 1;

    /**
     * 开始的记录
     **/
    protected Integer startRow = 0;

    protected Integer pageCount = 0;

    protected Integer countPage;

    // wy  是否是最后一页
    protected Boolean isLastPage = false;

    // wy  是否是第一页
    protected Boolean isFirstPage = false;





    /**
     * 模糊搜索
     **/
    private String keyword;
    private String startTime;
    private String endTime;

    private String provinceId;
    private String cityId;
    private String countyId;

    private String subjectId;

    private String param1;
    private String param2;
    private String param3;
    private String paramId;


    public PageAction() {
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }



    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getCountPage() {
        return countPage;
    }

    public void setCountPage(Integer countPage) {
        this.countPage = countPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public Boolean getLastPage() {
        return isLastPage;
    }

    public void setLastPage(Boolean lastPage) {
        isLastPage = lastPage;
    }

    public Boolean getFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        isFirstPage = firstPage;
    }

    /**
     * 使所有参数变为有效值
     **/
    public void validateSite() {
        // 总数不能小于0
        if (totalCount < 1) totalCount = 0;
        // 页大小不能小于1 不能大于1000
        if (pageSize < 1) pageSize = 1;
        if (pageSize > 1000) pageSize = 1000;
        // 取得页总数
        totalPage = (totalCount + pageSize - 1) / pageSize;
        // 取得当前页
        if (currentPage < 1) {
            currentPage = 1;
            countPage = currentPage;
        }
        // 取得记录起启位置
        startRow = (currentPage - 1) * pageSize;
    }

    @Override
    public String toString() {
        return "PageAction [totalCount=" + totalCount + ", totalPage=" + totalPage + ", pageSize=" + pageSize
                + ", currentPage=" + currentPage + ", startRow=" + startRow + ", pageCount=" + pageCount
                + ", countPage=" + countPage + ", keyword=" + keyword + "]";
    }

}
