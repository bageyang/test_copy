package com.zj.auction.general.app.controller;

import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.dto.RebateTransferDto;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.WalletRecord;
import com.zj.auction.common.model.Withdraw;
import com.zj.auction.common.query.PageQuery;
import com.zj.auction.common.query.WalletQuery;
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
    public Ret<PageVo<WalletRecord>> listUserWalletRecord(@RequestBody WalletQuery pageQuery){
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
    @PostMapping("/rejectCashWithdraw")
    public Ret<Boolean> rejectCashWithdraw(@RequestParam(name = "withDrawId") Long withDrawId){
        return Ret.ok(walletService.rejectCashWithdraw(withDrawId));
    }

    @PostMapping("/successCashWithdraw")
    public Ret<Boolean> successCashWithdraw(@RequestParam(name = "withDrawId") Long withDrawId){
        return Ret.ok(walletService.successCashWithdraw(withDrawId));
    }

    @PostMapping("/applyWithdraw")
    public Ret<Boolean> applyWithdraw(@RequestBody RebateTransferDto transferDto){
        return Ret.ok(walletService.cashWithdraw(transferDto));
    }

    @PostMapping("/withdraw/record")
    public Ret<List<Withdraw>> withdrawRecord(@RequestBody PageQuery pageQuery){
        return Ret.ok(walletService.listWithdrawRecord(pageQuery));
    }
}
