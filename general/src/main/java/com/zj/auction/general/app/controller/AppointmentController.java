package com.zj.auction.general.app.controller;

import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.AuctionArea;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/app/area")
public class AppointmentController {
    //todo 表中设置可预约时间段 预约后预约时间段到开拍前属于可浏览，竞拍开始属于竞拍中 ，竞拍结束后待开放
    @GetMapping(value = "list/{areaName}")
    public Ret<List<AuctionAreaDto>>  AreaList(@PathVariable("areaName") String areaName){
        return Ret.ok();
    }
}
