package com.zj.auction.common.constant;

public class RedisConstant {
    /**
     * 拍品信息
     */
    public static final String AUCTION_INFO_KEY = "auction:info";

    /**
     * 拍品对应库存号(list)
     */
    public static final String AUCTION_STOCK_KEY = "auction:stock:";


    public static final String PC_USER_TOKEN = "PcToken:%s";

    /**
     * 拍品库存量(hashmap)
     */
    public static final String AUCTION_REMAINDER_KEY = "auction:remainder";
    public static final String AUCTION_SEQUENCE_KEY = "auction:sequence:";

    public static final String MACHINE_SEQUENCE_KEY = "machine:sn";

    public static final String MACHINE_SEQUENCE_LOCK_KEY = "machine:sn_lock";

    public static final String AUCTION_LUA_SCRIPT =
            "if ((redis.call('exists', KEYS[1]) == 1) and (redis.call('exists', KEYS[2]) == 1)) then " +
            "if(tonumber(redis.call('hget', KEYS[1], ARGV[1])) > 0) then " +
            "redis.call('hincrby', KEYS[1], ARGV[1], -1) " +
            "return redis.call('LPOP', KEYS[2]); " +
            "end; " +
            "return nil; " +
            "end; " +
            "return nil;";
}
