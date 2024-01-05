package com.zj.auction.general.app.service;


import com.zj.auction.common.dto.BalanceChangeDto;
import com.zj.auction.common.dto.RebateTransferDto;
import com.zj.auction.common.model.Withdraw;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.vo.UserWalletVo;

import java.util.List;


public interface WalletService {
    /**
     * 更该用户金额
     * @param changeDto 参数
     */
    void changeUserBalance(BalanceChangeDto changeDto);

    /**
     * 增加用户金额 建议使用
     * @param changeDto
     */
    void incrementUserBalance(BalanceChangeDto changeDto);

    /**
     * 减少用户金额 建议使用
     * @param changeDto
     */
    void decrementUserBalance(BalanceChangeDto changeDto);

    /**
     * 冻结用户余额
     * @param changeDto 参数
     */
    void freezeUserBalance(BalanceChangeDto changeDto);
    /**
     * 解冻用户余额
     * @param changeDto 参数
     */
    void unfreezeUserBalance(BalanceChangeDto changeDto);

    UserWalletVo getUserWallet();

    /**
     * 绩效提现
     * @param transferDto
     * @return
     */
    Boolean rebateTransfer(RebateTransferDto transferDto);

    /**
     * 保证金转账
     * @param transferDto
     * @return
     */
    Boolean cashTransfer(RebateTransferDto transferDto);

    /**
     * 保证金申请提现
     * @param transferDto
     * @return
     */
    Boolean cashWithdraw(RebateTransferDto transferDto);
    List<Withdraw> listWithdrawRecord(PageQuery pageQuery);
    Boolean rejectCashWithdraw(Long withDrawId);
    Boolean successCashWithdraw(Long withDrawId);
}
