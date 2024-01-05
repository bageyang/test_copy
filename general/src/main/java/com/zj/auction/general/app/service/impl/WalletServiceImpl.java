package com.zj.auction.general.app.service.impl;

import com.zj.auction.common.dto.BalanceChangeDto;
import com.zj.auction.common.enums.FundTypeEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.WalletMapper;
import com.zj.auction.common.mapper.WalletRecordMapper;
import com.zj.auction.common.model.Wallet;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.general.app.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Objects;


@Service
public class WalletServiceImpl implements WalletService {
    private final WalletMapper walletMapper;
    private final WalletRecordMapper walletRecordMapper;

    @Autowired
    public WalletServiceImpl(WalletMapper walletMapper, WalletRecordMapper walletRecordMapper) {
        this.walletMapper = walletMapper;
        this.walletRecordMapper = walletRecordMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void changeUserBalance(BalanceChangeDto balanceChangeDto) {
        if (!checkBalanceParam(balanceChangeDto)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Long userId = balanceChangeDto.getUserId();
        BigDecimal changeNum = balanceChangeDto.getChangeNum();
        FundTypeEnum fundType = balanceChangeDto.getFundType();
        Wallet wallet = getUserWallet(userId, fundType);
        BigDecimal before = wallet.getBalance();
        BigDecimal after = before.add(changeNum);
        WalletRecord walletRecord = buildWalletRecord(balanceChangeDto);
        walletRecord.setBalanceBefore(before);
        walletRecord.setBalanceAfter(after);
        wallet.setBalance(after);
        walletMapper.updateByPrimaryKeySelective(wallet);
        walletRecordMapper.insertSelective(walletRecord);
    }

    @Override
    public void incrementUserBalance(BalanceChangeDto changeDto) {
        if (!checkBalanceParam(changeDto)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Long userId = changeDto.getUserId();
        BigDecimal changeNum = changeDto.getChangeNum().abs();
        FundTypeEnum fundType = changeDto.getFundType();
        Wallet wallet = getUserWallet(userId, fundType);
        BigDecimal before = wallet.getBalance();
        BigDecimal after = before.add(changeNum);
        WalletRecord walletRecord = buildWalletRecord(changeDto);
        walletRecord.setBalanceBefore(before);
        walletRecord.setBalanceAfter(after);
        wallet.setBalance(after);
        walletRecordMapper.insertSelective(walletRecord);
        Long id = wallet.getId();
        int i = walletMapper.incrementUserBalance(id, changeNum, before);
        if(i<1){
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
    }

    @Override
    public void decrementUserBalance(BalanceChangeDto changeDto) {
        if (!checkBalanceParam(changeDto)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Long userId = changeDto.getUserId();
        BigDecimal changeNum = changeDto.getChangeNum().abs();
        FundTypeEnum fundType = changeDto.getFundType();
        Wallet wallet = getUserWallet(userId, fundType);
        BigDecimal before = wallet.getBalance();
        BigDecimal after = before.subtract(changeNum);
        if(after.compareTo(BigDecimal.ZERO)<0){
            throw new CustomException(StatusEnum.BALANCE_NOT_ENOUGH_ERROR);
        }
        WalletRecord walletRecord = buildWalletRecord(changeDto);
        walletRecord.setBalanceBefore(before);
        walletRecord.setBalanceAfter(after);
        wallet.setBalance(after);
        walletRecordMapper.insertSelective(walletRecord);
        Long id = wallet.getId();
        int i = walletMapper.decrementUserBalance(id, changeNum, before);
        if(i<1){
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void freezeUserBalance(BalanceChangeDto changeDto) {
        if (!checkBalanceParam(changeDto)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Long userId = changeDto.getUserId();
        BigDecimal changeNum = changeDto.getChangeNum().abs();
        FundTypeEnum fundType = changeDto.getFundType();
        Wallet wallet = getUserWallet(userId, fundType);
        WalletRecord walletRecord = buildWalletRecord(changeDto);
        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal freezeBefore = wallet.getFreeze();
        BigDecimal balanceAfter = balanceBefore.subtract(changeNum);
        BigDecimal freezeAfter = freezeBefore.add(changeNum);
        walletRecord.setBalanceBefore(balanceBefore);
        walletRecord.setBalanceAfter(balanceAfter);
        wallet.setFreeze(freezeAfter);
        wallet.setBalance(balanceAfter);
        walletMapper.updateByPrimaryKey(wallet);
        walletRecordMapper.insertSelective(walletRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void unfreezeUserBalance(BalanceChangeDto changeDto) {
        if (!checkBalanceParam(changeDto)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Long userId = changeDto.getUserId();
        BigDecimal changeNum = changeDto.getChangeNum().abs();
        FundTypeEnum fundType = changeDto.getFundType();
        Wallet wallet = getUserWallet(userId, fundType);
        WalletRecord walletRecord = buildWalletRecord(changeDto);
        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal freezeBefore = wallet.getFreeze();
        BigDecimal balanceAfter = balanceBefore.add(changeNum);
        BigDecimal freezeAfter = freezeBefore.subtract(changeNum);
        walletRecord.setBalanceBefore(balanceBefore);
        walletRecord.setBalanceAfter(balanceAfter);
        wallet.setFreeze(freezeAfter);
        wallet.setBalance(balanceAfter);
        walletMapper.updateByPrimaryKey(wallet);
        walletRecordMapper.insertSelective(walletRecord);

    }

    private Wallet getUserWallet(Long userId,FundTypeEnum fundType){
        byte typeCode = fundType.getCode();
        Wallet wallet = walletMapper.selectWalletByUserIdAndType(userId, typeCode);
        if(Objects.isNull(wallet)){
            wallet = Wallet.builder()
                    .userId(userId)
                    .fundType(typeCode)
                    .build();
            walletMapper.insertSelective(wallet);
            wallet = walletMapper.selectWalletByUserIdAndType(userId, typeCode);
        }
        return wallet;
    }

    private boolean checkBalanceParam(BalanceChangeDto param) {
        return Objects.nonNull(param)
                &&Objects.nonNull(param.getUserId())
                &&Objects.nonNull(param.getFundType())
                &&Objects.nonNull(param.getChangeNum())
                && StringUtils.length(param.getRemark())>4;
    }

    private WalletRecord buildWalletRecord(BalanceChangeDto param){
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setUserId(param.getUserId());
        walletRecord.setWalletType(param.getFundType().getCode());
        walletRecord.setChangeBalance(param.getChangeNum());
        walletRecord.setRemark(param.getRemark());
        return walletRecord;
    }
}
