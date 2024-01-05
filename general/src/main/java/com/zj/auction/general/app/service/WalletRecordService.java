package com.zj.auction.general.app.service;

import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.PageQuery;

import java.util.List;

public interface WalletRecordService {
    PageVo<WalletRecord> listUserWalletRecord(PageQuery pageQuery);
}
