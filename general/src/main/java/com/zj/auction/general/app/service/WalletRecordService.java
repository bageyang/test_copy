package com.zj.auction.general.app.service;

import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.PageQuery;

import java.util.List;

public interface WalletRecordService {
    List<WalletRecord> listUserWalletRecord(PageQuery pageQuery);
}
