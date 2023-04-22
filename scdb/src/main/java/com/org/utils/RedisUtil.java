package com.org.utils;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

//redis工具类
@Component
public class RedisUtil {
    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //redis中操作string类型数据
    public Object getStringValue(String key,Object Object,int time){
        //获取string的value值
        ValueOperations<String,Object> operations=redisTemplate.opsForValue();
        if(!redisTemplate.hasKey(key)){
            operations.set(key,Object,time, TimeUnit.SECONDS);
        }
        return operations.get(key);
    }

    public String getStringValue(String key,String data){
        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        if(!stringRedisTemplate.hasKey(key)){
            operations.set(key,data,60,TimeUnit.SECONDS);
        }
        return operations.get(key);
    }

    //redis中操作list类型的数据
    public List<Object> getListValue(String key,Object object){
        ListOperations<String,Object> oerations=redisTemplate.opsForList();
        if(!redisTemplate.hasKey(key)){
            oerations.leftPushAll(key,object);
            redisTemplate.expire(key,60,TimeUnit.SECONDS);
        }
        System.out.println(oerations.range(key,0,-1));
        return oerations.range(key,0,-1);
    }
}
