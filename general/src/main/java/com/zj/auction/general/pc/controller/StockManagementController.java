package com.zj.auction.general.pc.controller;

import com.zj.auction.common.dto.AuctionStockNumDto;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.general.pc.service.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/stock")
public class StockManagementController {
    private StockManagerService stockManagerService;

    @Autowired
    public StockManagementController(StockManagerService stockManagerService) {
        this.stockManagerService = stockManagerService;
    }

    @PostMapping("/addAuctionStock")
    public Ret<Boolean> addStockAndTransfer2Auction(AuctionStockNumDto query){
        return Ret.ok(stockManagerService.addAndTransfer2Auction(query));
    }
}
