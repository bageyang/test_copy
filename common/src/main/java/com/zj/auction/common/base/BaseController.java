package com.zj.auction.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.auction.common.constant.TokenConstant;
import com.zj.auction.common.util.ServletUtils;
import com.zj.auction.common.vo.PageResult;
import com.zj.auction.common.vo.ResponseData;
import com.zj.auction.general.vo.PageAction;
import org.apache.commons.lang3.StringUtils;

/**
 * 基础控制器
 */
public class BaseController {

    public PageResult GetPageResult(IPage<?> page) {
        PageResult pageResult = new PageResult();
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setCount(page.getTotal());
        pageResult.setMessage("查询成功");
        pageResult.setData(page.getRecords());
        pageResult.setPages(page.getPages());
        return pageResult;
    }

//    public PageAction GetQueryParam(PageAction pageAction) {
//        PageAction pageDomain = new PageAction();
//        String page = ServletUtils.GetRequest().getParameter("page");
//        String limit = ServletUtils.GetRequest().getParameter("limit");
//        page = pageAction.getCurrentPage().toString();
//        limit =pageAction.getPageSize().toString();
//
//        pageDomain.setCurrentPage(Integer.parseInt(page));
//        pageDomain.setPageSize(Integer.parseInt(limit));
//        return pageDomain;
//    }

//    /**
//     * 自定义业务层调用方便 controller 可依然调用原先 ok 或者fail
//     */
//    public ResponseData GetResponse(ExecuteResult executeResult) {
//        ResponseData apiResult = new ResponseData();
//        apiResult.setCode(executeResult.getCode());
//        apiResult.setSuccess(executeResult.isSuccess());
//        apiResult.setMsg(executeResult.getMsg());
//        apiResult.setData(executeResult.getData());
//        return apiResult;
//    }
    public String getToken() {
        return ServletUtils.GetRequest().getHeader(TokenConstant.TOKEN);
    }


}
