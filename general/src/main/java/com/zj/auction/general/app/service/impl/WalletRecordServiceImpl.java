package com.zj.auction.general.app.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.mapper.WalletRecordMapper;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.general.app.service.WalletRecordService;
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
    public PageVo<WalletRecord> listUserWalletRecord(PageQuery pageQuery) {
        // todo
//        User user = SecurityUtils.getPrincipal();
        Long userId = 202L;
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        return  PageVo.of((Page<WalletRecord>) walletRecordMapper.listUserWalletRecord(userId));
    }
}
