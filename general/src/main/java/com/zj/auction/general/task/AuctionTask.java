package com.zj.auction.general.task;

import com.zj.auction.general.pc.service.AuctionManagerService;
import com.zj.auction.general.pc.service.OrderManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Slf4j
public class AuctionTask {
    @Autowired
    private AuctionManagerService auctionManagerService;
    /**
     * 处理流拍拍品
     * 每10 分钟运行一次
     */
    @Async("customThreadPool")
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void handUnAuctionStock(){
        log.info("=========处理流拍拍品============");
        auctionManagerService.handUnAuctionStock();
        log.info("=========处理流拍完毕============");
    }

    @Async("customThreadPool")
    @Scheduled(cron = "0 0 0 * * ? ")
    public void transferUnAuctionStock(){
        // 获取分布式锁,未获取到则放弃
        // 获取所有流拍订单
        // 查询是否有相同类型以及价格拍品
        // 重组库存余拍品关系
        log.info("=========上架流拍拍品============");
        auctionManagerService.reAuctionOrder();
        log.info("=========上架流拍拍品============");
    }
}
