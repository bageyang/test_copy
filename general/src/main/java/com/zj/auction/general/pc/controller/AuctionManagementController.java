package com.zj.auction.general.pc.controller;

import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.query.GoodsQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/auction")
public class AuctionManagementController {

    @PostMapping("/list")
    public Ret<PageVo<Auction>> list(GoodsQuery query){
//        return Ret.ok(goodsManagerService.listGoods(query));
        return null;
    }
}
