package com.zj.auction.general.app.service.impl;


import com.github.pagehelper.PageHelper;
import com.zj.auction.common.mapper.WalletRecordMapper;
import com.zj.auction.common.model.User;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.general.app.service.WalletRecordService;
import com.zj.auction.general.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletRecordServiceImpl implements WalletRecordService {
    private final WalletRecordMapper walletRecordMapper;

    public WalletRecordServiceImpl(WalletRecordMapper walletRecordMapper) {
        this.walletRecordMapper = walletRecordMapper;
    }

    @Override
    public List<WalletRecord> listUserWalletRecord(PageQuery pageQuery) {
        User user = SecurityUtils.getPrincipal();
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        return walletRecordMapper.listUserWalletRecord(user.getUserId());
    }
}
