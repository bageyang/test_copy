package com.zj.auction.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zj.auction.common.vo.ResponseData;

import java.util.Map;

public interface UserBillService {
    String tradeCreate(Map bodys);
    void processOrder(Map<String, String> params);
}
