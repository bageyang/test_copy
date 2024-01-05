package com.zj.auction.seckill.controller;

import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Auction;
import com.zj.auction.seckill.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @GetMapping("/area/{areaId}")
    public Object listAreaAuction(@PathVariable("areaId")Integer areaId){
        return Ret.ok(auctionService.listAuctionByAreaId(areaId));
    }

    @GetMapping("/info/{auctionId}")
    public Object listAreaAuction(@PathVariable("auctionId")Long auctionId){
        return Ret.ok(auctionService.getAuctionInfo(auctionId));
    }
}
