package com.zj.auction.general.listener.strategy;

import com.zj.auction.common.dto.BalanceChangeDto;
import com.zj.auction.common.dto.PayDto;
import com.zj.auction.common.enums.FundTypeEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.enums.TransactionTypeEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.general.app.service.WalletRecordService;
import com.zj.auction.general.app.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CashRechargeHandler implements PayCallBackHandler{
    private static final String CASH_PAY_TYPE = "充值余额支付";
    private static final String SUCCESS_STAT = "成功支付";
    private static final String REMARK = "余额充值到账";
    private final WalletService walletService;

    @Autowired
    public CashRechargeHandler(WalletService walletService) {
        this.walletService = walletService;
    }


    @Override
    public boolean shouldHand(PayDto payDto) {
        String billType = payDto.getBillType();
        String billStatus = payDto.getBillStatus();
        return Objects.equals(CASH_PAY_TYPE,billType)&&Objects.equals(SUCCESS_STAT,billStatus);
    }

    @Override
    public void hand(PayDto payDto) {
        log.info("处理余额充值到账,msg: {}",payDto);
        Long userId = getUserId(payDto);
        BalanceChangeDto changeDto = new BalanceChangeDto();
        changeDto.setTransactionSn(payDto.getTranstionSn());
        changeDto.setChangeNum(payDto.getAmount());
        changeDto.setUserId(userId);
        changeDto.setFundType(FundTypeEnum.CASH);
        changeDto.setTransactionType(TransactionTypeEnum.RECHARGE);
        changeDto.setRemark(REMARK);
        walletService.incrementUserBalance(changeDto);
        log.info("处理余额充值到账完成");
    }

    private Long getUserId(PayDto payDto) {
        Long userId = payDto.getUserId();
        Long originSn = payDto.getOriginSn();
        if(Objects.isNull(userId)&&Objects.isNull(originSn)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        return Objects.nonNull(userId)?userId:originSn;
    }
}
