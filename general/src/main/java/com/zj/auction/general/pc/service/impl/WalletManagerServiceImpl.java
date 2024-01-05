package com.zj.auction.general.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.mapper.WalletMapper;
import com.zj.auction.common.mapper.WalletRecordMapper;
import com.zj.auction.common.model.Wallet;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.WalletQuery;
import com.zj.auction.general.pc.service.WalletManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletManagerServiceImpl implements WalletManagerService {
    private final WalletMapper walletMapper;
    private final WalletRecordMapper walletRecordMapper;

    @Autowired
    public WalletManagerServiceImpl(WalletMapper walletMapper, WalletRecordMapper walletRecordMapper) {
        this.walletMapper = walletMapper;
        this.walletRecordMapper = walletRecordMapper;
    }

    @Override
    public PageVo<Wallet> listWallet(WalletQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        return PageVo.of((Page<Wallet>)walletMapper.listWallet(query));
    }

    @Override
    public PageVo<WalletRecord> listWalletRecord(WalletQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        return PageVo.of((Page<WalletRecord>) walletRecordMapper.listWalletRecord(query));
    }
}
