package com.flashsale.api.service;

import com.flashsale.api.redis.StockLuaScript;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StockService {

    private RedisTemplate<String, String> redisTemplate=new RedisTemplate<>();
    private DefaultRedisScript<Long> script=new DefaultRedisScript<>();

    public StockService(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate=redisTemplate;
        this.script=new DefaultRedisScript<>();
        this.script.setScriptText(StockLuaScript.DECREMENT_STOCK);
        this.script.setResultType(Long.class);



    }

    public boolean tryBuy(String itemId){
        String key="stock:item:" + itemId;

        Long result=redisTemplate.execute(script,List.of(key));

        return result != null && result==1;
    }
}
