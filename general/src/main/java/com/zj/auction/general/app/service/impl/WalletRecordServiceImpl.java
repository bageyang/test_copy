package com.zj.auction.general.app.service.impl;


import com.github.pagehelper.Page;
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

    /**
     * 列表记录用户钱包
     *
     * @param pageQuery 页面查询
     * @return {@link List}<{@link WalletRecord}>
     */
    @Override
    public List<WalletRecord> listUserWalletRecord(PageQuery pageQuery) {
        // todo
//        User user = SecurityUtils.getPrincipal();
        Long userId = 202L;
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        return  (Page)walletRecordMapper.listUserWalletRecord(userId);
    }
}
