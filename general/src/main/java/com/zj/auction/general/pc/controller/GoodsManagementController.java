package com.zj.auction.general.pc.controller;

import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.query.GoodsQuery;
import com.zj.auction.general.pc.service.GoodsManagerService;
import com.zj.auction.general.pc.service.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/goods")
public class GoodsManagementController {
    private GoodsManagerService goodsManagerService;

    @Autowired
    public GoodsManagementController(GoodsManagerService goodsManagerService) {
        this.goodsManagerService = goodsManagerService;
    }

    @PostMapping("/add")
    public Ret<Boolean> addGoods(Goods goods){
        return Ret.ok(goodsManagerService.addGoods(goods));
    }

    @PostMapping("/update")
    public Ret<Boolean> updateGoods(Goods goods){
        return Ret.ok(goodsManagerService.updateGoods(goods));
    }

    @PostMapping("/list")
    public Ret<List<Goods>> list(GoodsQuery query){
        return Ret.ok(goodsManagerService.listGoods(query));
    }



}
