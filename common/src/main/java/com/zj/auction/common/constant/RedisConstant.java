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

    public static final String KEY_USER_TOKEN = "Token:%s";

    /**
     * 拍品库存量(hashmap)
     */
    public static final String AUCTION_REMAINDER_KEY = "auction:remainder";
    /**
     * 拍品自增id序列
     */
    public static final String AUCTION_SEQUENCE_KEY = "auction:sequence:";

    /**
     * 创建拍品全局锁
     */
    public static final String AUCTION_GENERATOR_LOCK_KEY = "auction:generator:lock";

    /**
     * SnowFlake 全局机器号相关 key
     */
    public static final String MACHINE_SEQUENCE_KEY = "machine:sn";

    /**
     * SnowFlake 全局机器号相关 key
     */
    public static final String MACHINE_SEQUENCE_LOCK_KEY = "machine:sn_lock";

    /**
     * 秒杀订单缓存key
     */
    public static final String AUCTION_ORDER_CACHE_KEY = "auction:createdOrder";

    public static final String AUCTION_LUA_SCRIPT = "if ((redis.call('exists', KEYS[1]) == 1) and (redis.call('exists', KEYS[2]) == 1)) then if(tonumber(redis.call('hget', KEYS[1], ARGV[1])) > 0) then redis.call('hincrby', KEYS[1], ARGV[1], -1) return redis.call('LPOP', KEYS[2]); end; return nil; end; return nil;";

    public static final String AUCTION_LUA_SCRIPT_SHA = "636c1465770b16cd5f70e6b3bc50aa05ac29dfc9";
}
