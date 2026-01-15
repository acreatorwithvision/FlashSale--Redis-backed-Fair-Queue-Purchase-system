package com.flashsale.api.redis;

public class StockLuaScript {

    public static final String DECREMENT_STOCK= """
            local stock =redis.call('GET',KEYS[1])
            if not stock or tonumber(stock) <= 0 then
                return 0
            end
            redis.call('DECR',KEYS[1])
            return 1
        """;
}
