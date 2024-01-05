package com.zj.auction.general.app.service;

import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.query.WalletQuery;

import java.util.List;

public interface WalletRecordService {
    PageVo<WalletRecord> listUserWalletRecord(WalletQuery pageQuery);
}
