package com.zj.auction.general.app.service;


import com.zj.auction.common.dto.BalanceChangeDto;


public interface WalletService {
    /**
     * 更该用户金额
     * @param changeDto 参数
     */
    void changeUserBalance(BalanceChangeDto changeDto);

    /**
     * 增加用户金额
     * @param changeDto
     */
    void incrementUserBalance(BalanceChangeDto changeDto);

    /**
     * 减少用户金额
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

}
