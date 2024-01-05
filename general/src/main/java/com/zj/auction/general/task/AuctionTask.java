package com.zj.auction.general.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Slf4j
public class AuctionTask {
    /**
     * 处理流拍拍品
     * 每10 分钟运行一次
     */
    @Async("customThreadPool")
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void handUnAuctionStock(){
        // 查询今日所有拍卖场次
        // 筛选已结束的拍卖场次
        // 查询结束场次的未拍下的订单
        // 计算超时支付时间
        // 查询拍下支付超时的订单
        // 将订单更改未流拍状态
    }

    @Async("customThreadPool")
    @Scheduled(cron = "0 0 0 * * ? ")
    public void transferUnAuctionStock(){
        // 获取分布式锁,未获取到则放弃
        // 获取所有流拍订单
        // 查询是否有相同类型以及价格拍品
        // 重组库存余拍品关系
    }
}
