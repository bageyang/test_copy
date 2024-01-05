package com.zj.auction.general.listener.strategy;

import com.zj.auction.common.dto.PayDto;

public interface PayCallBackHandler {
    /**
     * 是否处理该消息
     * @return
     */
    boolean shouldHand(PayDto payDto);

    /**
     * 处理方法
     * @param payDto
     */
    void hand(PayDto payDto);
}
