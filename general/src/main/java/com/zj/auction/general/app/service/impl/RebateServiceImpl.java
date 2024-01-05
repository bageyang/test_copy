package com.zj.auction.general.app.service.impl;

import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.mapper.SystemCnfMapper;
import com.zj.auction.common.mapper.UserMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.general.app.service.RebateService;
import com.zj.auction.general.app.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RebateServiceImpl implements RebateService {
    private final SystemCnfMapper systemCnfMapper;
    private final UserMapper userMapper;
    private final WalletService walletService;

    @Autowired
    public RebateServiceImpl(SystemCnfMapper systemCnfMapper, UserMapper userMapper, WalletService walletService) {
        this.systemCnfMapper = systemCnfMapper;
        this.userMapper = userMapper;
        this.walletService = walletService;
    }

    @Override
    public void rebateOrder(Order order) {
        BigDecimal totalAmount = order.getTotalAmount();
        BigDecimal handFee = order.getHandFee();
        String rebateRateStr = systemCnfMapper.selectValueByKeyName(Constant.REBATE_RATE_KEY);
        Long userId = order.getUserId();
        Long parentId = userMapper.selectUserParent(userId);
        BigDecimal rebateRate = new BigDecimal(rebateRateStr);
        BigDecimal rebateMoney = handFee.multiply(rebateRate);

        walletService.changeUserBalance();
    }
}
