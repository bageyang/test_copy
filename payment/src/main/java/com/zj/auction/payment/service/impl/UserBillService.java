package com.zj.auction.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zj.auction.common.dto.PayDto;
import com.zj.auction.common.model.UserBill;
import com.zj.auction.common.vo.ResponseData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserBillService {
    String tradeCreate(Map bodys);
    void processOrder(Map<String, String> params);
    List<UserBill> getNoPayOrderByDuration(int minutes);
    void checkPayOrderStatus(String transtionSn);
    String queryOrder(String transtionSn);
    void closeOrder(String transtionSn);
}
