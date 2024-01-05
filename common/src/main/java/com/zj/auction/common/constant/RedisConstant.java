package com.zj.auction.common.constant;

public class RedisConstant {
    /**
     * 拍品信息redisKey
     */
    public static final String AUCTION_INFO_KEY = "auction:info";
    /**
     * 拍品库存余量redisKey
     */
    public static final String AUCTION_STOCK_KEY = "auction:stock:";
    /**
     * 拍品库存号对应redisKey
     */
    public static final String AUCTION_VOLUME_KEY = "auction:volume";
    public static final String MACHINE_SEQUENCE_KEY = "machine:sn";
    public static final String MACHINE_SEQUENCE_LOCK_KEY = "machine:sn_lock";
}
