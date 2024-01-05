package com.zj.auction.general.pc.service;

import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.model.Wallet;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.WalletQuery;

public interface WalletManagerService {
    PageVo<Wallet> listWallet(WalletQuery query);
    PageVo<WalletRecord> listWalletRecord(WalletQuery query);
}
