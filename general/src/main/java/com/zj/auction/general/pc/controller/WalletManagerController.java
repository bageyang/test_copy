package com.zj.auction.general.pc.controller;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Wallet;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.WalletQuery;
import com.zj.auction.general.pc.service.WalletManagerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/wallet")
public class WalletManagerController {
    private final WalletManagerService walletManagerService;

    @Autowired
    public WalletManagerController(WalletManagerService walletManagerService) {
        this.walletManagerService = walletManagerService;
    }

    @PostMapping("/list")
    public Ret<PageVo<Wallet>> listUserWallet(@RequestBody WalletQuery query){
        return Ret.ok( walletManagerService.listWallet(query));
    }

    @PostMapping("/listRecord")
    public Ret<PageVo<WalletRecord>> listUserWalletRecord(@RequestBody WalletQuery query){
        return Ret.ok( walletManagerService.listWalletRecord(query));
    }
}
