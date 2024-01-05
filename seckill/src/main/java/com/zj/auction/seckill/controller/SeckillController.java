package com.zj.auction.seckill.controller;

import com.zj.auction.common.dto.BaseOrderDto;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.seckill.service.AuctionService;
import com.zj.auction.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exclusive")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @GetMapping("/{auctionId}")
    public Ret<BaseOrderDto> seckillAuction(@PathVariable("auctionId")Long auctionId){
        return seckillService.seckill(auctionId);
    }
}
