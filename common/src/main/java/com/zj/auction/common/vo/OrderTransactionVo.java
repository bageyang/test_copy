package com.zj.auction.common.vo;

import com.zj.auction.common.model.Order;

public class OrderTransactionVo {
    private Order sellOrder;
    private Order buyOrder;
    private UserAccountInfo  sellUser;
    private UserAccountInfo buyUser;

    public Order getSellOrder() {
        return sellOrder;
    }

    public void setSellOrder(Order sellOrder) {
        this.sellOrder = sellOrder;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(Order buyOrder) {
        this.buyOrder = buyOrder;
    }

    public UserAccountInfo getSellUser() {
        return sellUser;
    }

    public void setSellUser(UserAccountInfo sellUser) {
        this.sellUser = sellUser;
    }

    public UserAccountInfo getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(UserAccountInfo buyUser) {
        this.buyUser = buyUser;
    }
}
