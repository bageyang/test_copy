package com.zj.auction.payment.task;

import com.zj.auction.common.model.UserBill;
import com.zj.auction.payment.service.UserBillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class KuDianPayTask {
    @Resource
    private UserBillServiceImpl userBillService;
    /**
     * 从第0秒开始每隔10分钟秒执行1次，查询创建超过5分钟，并且未支付的订单
     */
//    @Scheduled(cron = "0 0 0/30 * * ?")
//    @Scheduled(cron = "0/30 * * * * ?")
    public void payOrderConfirm(){
        log.info("payOrderConfirm 被执行......");
        //查询10分钟之类未被执行的支付订单
        List<UserBill> noPayOrders = userBillService.getNoPayOrderByDuration(10);

        for (UserBill userBill : noPayOrders) {
            String orderNo = userBill.getTranstionSn();
            log.warn("超时支付订单 ===> {}", orderNo);

            //核实订单状态：调用支付宝查单接口
            userBillService.checkPayOrderStatus(orderNo);
        }

    }
}
