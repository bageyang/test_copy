package com.zj.auction.general.app.controller;

import com.zj.auction.common.dto.RebateTransferDto;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.vo.UserWalletVo;
import com.zj.auction.general.app.service.WalletRecordService;
import com.zj.auction.general.app.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;
    private final WalletRecordService walletRecordService;

    public WalletController(WalletService walletService, WalletRecordService walletRecordService) {
        this.walletService = walletService;
        this.walletRecordService = walletRecordService;
    }


    @GetMapping("/info")
    public Ret<UserWalletVo> getUserWallet(){
        return Ret.ok(walletService.getUserWallet());
    }

    @PostMapping("/record")
    public Ret<List<WalletRecord>> listUserWalletRecord(@RequestBody PageQuery pageQuery){
        return Ret.ok(walletRecordService.listUserWalletRecord(pageQuery));
    }

    @PostMapping("/rebateTransfer")
    public Ret<Boolean> rebateTransfer(@RequestBody RebateTransferDto transferDto){
        return Ret.ok(walletService.rebateTransfer(transferDto));
    }

    @PostMapping("/cashTransfer")
    public Ret<Boolean> cashTransfer(@RequestBody RebateTransferDto transferDto){
        return Ret.ok(walletService.cashTransfer(transferDto));
    }
}
