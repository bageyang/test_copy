package com.zj.auction.general.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.dto.BalanceChangeDto;
import com.zj.auction.common.dto.RebateTransferDto;
import com.zj.auction.common.enums.FundTypeEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.enums.WithdrawStatEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.UserMapper;
import com.zj.auction.common.mapper.WalletMapper;
import com.zj.auction.common.mapper.WalletRecordMapper;
import com.zj.auction.common.mapper.WithdrawMapper;
import com.zj.auction.common.model.User;
import com.zj.auction.common.model.Wallet;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.model.Withdraw;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.util.QrCodeUtils;
import com.zj.auction.common.util.SnowFlake;
import com.zj.auction.common.vo.UserWalletVo;
import com.zj.auction.general.app.service.WalletService;
import com.zj.auction.general.shiro.SecurityUtils;
import javafx.concurrent.Worker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class WalletServiceImpl implements WalletService {
    private final WalletMapper walletMapper;
    private final WalletRecordMapper walletRecordMapper;
    private final UserMapper userMapper;
    private final WithdrawMapper withdrawMapper;

    @Autowired
    public WalletServiceImpl(WalletMapper walletMapper, WalletRecordMapper walletRecordMapper, UserMapper userMapper, WithdrawMapper withdrawMapper) {
        this.walletMapper = walletMapper;
        this.walletRecordMapper = walletRecordMapper;
        this.userMapper = userMapper;
        this.withdrawMapper = withdrawMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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

    private void fillTransactionSn(BalanceChangeDto dto) {
        if(StringUtils.isNotBlank(dto.getTransactionSn())){
            return;
        }
        long l = SnowFlake.nextId();
        dto.setTransactionSn(String.valueOf(l));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        if (i < 1) {
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        if (after.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(StatusEnum.BALANCE_NOT_ENOUGH_ERROR);
        }
        WalletRecord walletRecord = buildWalletRecord(changeDto);
        walletRecord.setBalanceBefore(before);
        walletRecord.setBalanceAfter(after);
        wallet.setBalance(after);
        walletRecordMapper.insertSelective(walletRecord);
        Long id = wallet.getId();
        int i = walletMapper.decrementUserBalance(id, changeNum, before);
        if (i < 1) {
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
        if (balanceAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(StatusEnum.BALANCE_NOT_ENOUGH_ERROR);
        }
        BigDecimal freezeAfter = freezeBefore.add(changeNum);
        walletRecord.setBalanceBefore(balanceBefore);
        walletRecord.setBalanceAfter(balanceAfter);
        wallet.setFreeze(freezeAfter);
        wallet.setBalance(balanceAfter);
        walletMapper.updateByPrimaryKey(wallet);
        walletRecordMapper.insertSelective(walletRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
        if (freezeAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(StatusEnum.BALANCE_NOT_ENOUGH_ERROR);
        }
        walletRecord.setBalanceBefore(balanceBefore);
        walletRecord.setBalanceAfter(balanceAfter);
        wallet.setFreeze(freezeAfter);
        wallet.setBalance(balanceAfter);
        walletMapper.updateByPrimaryKey(wallet);
        walletRecordMapper.insertSelective(walletRecord);
    }

    private Wallet getUserWallet(Long userId, FundTypeEnum fundType) {
        byte typeCode = fundType.getCode();
        Wallet wallet = walletMapper.selectWalletByUserIdAndType(userId, typeCode);
        if (Objects.isNull(wallet)) {
            createUserWallet(userId, fundType);
            wallet = walletMapper.selectWalletByUserIdAndType(userId, typeCode);
        }
        return wallet;
    }

    private boolean checkBalanceParam(BalanceChangeDto param) {
        fillTransactionSn(param);
        return Objects.nonNull(param)
                && Objects.nonNull(param.getUserId())
                && Objects.nonNull(param.getFundType())
                && Objects.nonNull(param.getChangeNum())
                && StringUtils.length(param.getRemark()) > 3;
    }

    private WalletRecord buildWalletRecord(BalanceChangeDto param) {
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setUserId(param.getUserId());
        walletRecord.setWalletType(param.getFundType().getCode());
        walletRecord.setChangeBalance(param.getChangeNum());
        walletRecord.setRemark(param.getRemark());
        return walletRecord;
    }

    @Override
    public UserWalletVo getUserWallet() {
        User user = SecurityUtils.getPrincipal();
        Long userId = user.getUserId();
        List<Wallet> wallets = walletMapper.selectAllByUserId(userId);
        Map<FundTypeEnum, Wallet> walletGroup = wallets.stream()
                .collect(Collectors.toMap(this::fundTypeGroup, Function.identity()));
        Wallet cashWallet = walletGroup.get(FundTypeEnum.CASH);
        if (Objects.isNull(cashWallet)) {
            cashWallet = createUserWallet(userId, FundTypeEnum.CASH);
        }
        Wallet integralWallet = walletGroup.get(FundTypeEnum.INTEGRAL);
        if (Objects.isNull(cashWallet)) {
            cashWallet = createUserWallet(userId, FundTypeEnum.INTEGRAL);
        }
        Wallet rebateWallet = walletGroup.get(FundTypeEnum.REBATE);
        if (Objects.isNull(cashWallet)) {
            cashWallet = createUserWallet(userId, FundTypeEnum.REBATE);
        }
        UserWalletVo userWalletVo = new UserWalletVo();
        userWalletVo.setCashBalance(cashWallet.getBalance());
        userWalletVo.setIntegralBalance(integralWallet.getBalance());
        userWalletVo.setRebateBalance(rebateWallet.getBalance());
        return userWalletVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rebateTransfer(RebateTransferDto transferDto) {
        User user = SecurityUtils.getPrincipal();
        Long userId = user.getUserId();
        Wallet rebateWallet = getUserWallet(userId, FundTypeEnum.REBATE);
        Wallet cashWallet = getUserWallet(userId, FundTypeEnum.CASH);
        BigDecimal transferNum = transferDto.getNum().abs();
        BigDecimal rebateBalance = rebateWallet.getBalance();
        BigDecimal cashBalance = cashWallet.getBalance();
        BigDecimal rebateAfter = rebateBalance.subtract(transferNum);
        if (rebateAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(StatusEnum.BALANCE_NOT_ENOUGH_ERROR);
        }
        WalletRecord rebateRecord = WalletRecord.builder()
                .userId(userId)
                .walletType(FundTypeEnum.REBATE.getCode())
                .changeBalance(transferNum.negate())
                .balanceBefore(rebateBalance)
                .balanceAfter(rebateAfter)
                .remark("绩效提现,绩效余额减少")
                .build();
        WalletRecord cashRecord = WalletRecord.builder()
                .userId(userId)
                .walletType(FundTypeEnum.CASH.getCode())
                .changeBalance(transferNum)
                .balanceBefore(cashBalance)
                .balanceAfter(cashBalance.add(transferNum))
                .remark("绩效提现,保证金余额增加")
                .build();
        walletRecordMapper.insertSelective(cashRecord);
        walletRecordMapper.insertSelective(rebateRecord);
        int r1 = walletMapper.incrementUserBalance(cashWallet.getId(), transferNum, cashBalance);
        int r2 = walletMapper.decrementUserBalance(rebateWallet.getId(), transferNum, rebateBalance);
        if (r1 < 1 || r2 < 0) {
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cashTransfer(RebateTransferDto transferDto) {
        User user = SecurityUtils.getPrincipal();
        Long userId = user.getUserId();
        String userName = user.getUserName();
        Long toUserId = transferDto.getToUserId();
        BigDecimal num = transferDto.getNum().abs();

        User otherOne = userMapper.selectByPrimaryKey(toUserId);
        Wallet myWallet = getUserWallet(userId, FundTypeEnum.REBATE);
        Wallet otherWallet = getUserWallet(toUserId, FundTypeEnum.CASH);

        BigDecimal myBalance = myWallet.getBalance();
        BigDecimal myAfter = myBalance.subtract(num);

        BigDecimal otherBalance = otherWallet.getBalance();
        BigDecimal otherAfter = otherBalance.add(num);

        if (myAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(StatusEnum.BALANCE_NOT_ENOUGH_ERROR);
        }
        WalletRecord myRecord = WalletRecord.builder()
                .userId(userId)
                .walletType(FundTypeEnum.CASH.getCode())
                .changeBalance(num.negate())
                .balanceBefore(myBalance)
                .balanceAfter(myAfter)
                .remark("转账给用户: "+otherOne.getUserName())
                .build();
        WalletRecord otherRecord = WalletRecord.builder()
                .userId(userId)
                .walletType(FundTypeEnum.CASH.getCode())
                .changeBalance(num)
                .balanceBefore(otherBalance)
                .balanceAfter(otherAfter)
                .remark("收到来自用户: "+userName+" 的转账")
                .build();
        walletRecordMapper.insertSelective(myRecord);
        walletRecordMapper.insertSelective(otherRecord);
        int r1 = walletMapper.decrementUserBalance(myWallet.getId(), num, myBalance);
        int r2 = walletMapper.incrementUserBalance(otherWallet.getId(), num, otherBalance);
        if (r1 < 1 || r2 < 0) {
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
        return true;
    }

    @Override
    public Boolean cashWithdraw(RebateTransferDto transferDto) {
        User user = SecurityUtils.getPrincipal();
        Long userId = user.getUserId();
        BigDecimal num = transferDto.getNum().abs();
        Wallet cashWallet = getUserWallet(userId, FundTypeEnum.CASH);
        BigDecimal balance = cashWallet.getBalance();
        BigDecimal after = balance.subtract(num);
        if (after.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(StatusEnum.BALANCE_NOT_ENOUGH_ERROR);
        }
        WalletRecord record = WalletRecord.builder()
                .userId(userId)
                .walletType(FundTypeEnum.CASH.getCode())
                .changeBalance(num.negate())
                .balanceBefore(balance)
                .balanceAfter(after)
                .remark("保证金提现")
                .build();
        Withdraw withdraw = Withdraw.builder()
                .userId(userId)
                .withdrawNum(num)
                .auditStatus(WithdrawStatEnum.WAIT_AUDIT.getCode())
                .drawTime(LocalDateTime.now())
                .build();
        walletRecordMapper.insertSelective(record);
        int r1 = walletMapper.decrementUserBalance(cashWallet.getId(), num, balance);
        int r2 = withdrawMapper.insertSelective(withdraw);
        if (r1 < 1 || r2 < 1) {
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
        return true;
    }

    @Override
    public Boolean rejectCashWithdraw(Long withDrawId) {
        if(Objects.isNull(withDrawId)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Withdraw withdraw = withdrawMapper.selectByPrimaryKey(withDrawId);
        if(Objects.isNull(withdraw)){
            throw new CustomException(StatusEnum.WITHDRAW_MISS_ERROR);
        }
        Long userId = withdraw.getUserId();
        BigDecimal num = withdraw.getWithdrawNum().abs();
        Wallet cashWallet = getUserWallet(userId, FundTypeEnum.CASH);
        BigDecimal balance = cashWallet.getBalance();
        BigDecimal after = balance.add(num);
        WalletRecord record = WalletRecord.builder()
                .userId(userId)
                .walletType(FundTypeEnum.CASH.getCode())
                .changeBalance(num.negate())
                .balanceBefore(balance)
                .balanceAfter(after)
                .remark("提现拒绝返还保证金")
                .build();
        withdraw.setAuditStatus(WithdrawStatEnum.REJECT.getCode());
        withdraw.setAuditTime(LocalDateTime.now());
        walletRecordMapper.insertSelective(record);
        int r1 = walletMapper.incrementUserBalance(cashWallet.getId(), num, balance);
        int r2 = withdrawMapper.updateByPrimaryKeySelective(withdraw);
        if (r1 < 1 || r2 < 1) {
            throw new CustomException(StatusEnum.OPERATION_FAIL_ERROR);
        }
        return true;
    }

    @Override
    public Boolean successCashWithdraw(Long withDrawId) {
        if(Objects.isNull(withDrawId)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Withdraw withdraw = withdrawMapper.selectByPrimaryKey(withDrawId);
        if(Objects.isNull(withdraw)){
            throw new CustomException(StatusEnum.WITHDRAW_MISS_ERROR);
        }
        withdraw.setAuditStatus(WithdrawStatEnum.SUCCESS.getCode());
        withdraw.setAuditTime(LocalDateTime.now());
        return withdrawMapper.updateByPrimaryKeySelective(withdraw)>0;
    }

    @Override
    public List<Withdraw> listWithdrawRecord( PageQuery pageQuery) {
        // todo
        Long userId = 2L;
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        return withdrawMapper.listWithdrawRecord(userId);
    }

    private Wallet createUserWallet(Long userId, FundTypeEnum fundType) {
        Wallet wallet = Wallet.builder()
                .userId(userId)
                .fundType(fundType.getCode())
                .build();
        walletMapper.insertSelective(wallet);
        return wallet;
    }

    private FundTypeEnum fundTypeGroup(Wallet t) {
        return FundTypeEnum.codeOf(t.getFundType());
    }
}
