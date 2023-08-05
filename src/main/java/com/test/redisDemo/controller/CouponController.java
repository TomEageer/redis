package com.test.redisDemo.controller;

import com.test.redisDemo.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("add")
    public JsonData setCoupon(@RequestParam(value = "coupon_id", required = true) int couponId) {

        //唯一标识符，防止其他线程误删
        String uuid = UUID.randomUUID().toString();

        String lockKey = "lock:coupon:" + couponId;

        lock(uuid, lockKey, couponId);

        return JsonData.buildSuccess();
    }

    private void lock(String uuid, String lockKey, int couponId) {


        //Lua脚本
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";


        Boolean nativeLock = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 30, TimeUnit.SECONDS);

        System.out.println(uuid + "加锁状态" + nativeLock);
        if (nativeLock) {
            //加锁成功
            try {
                //TODO 做相关的业务逻辑
                TimeUnit.SECONDS.sleep(4L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                //解锁操作
                Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(lockKey), uuid);
                System.out.println("解锁状态" + result);
            }

        } else {
            //加锁失败
            try {
                System.out.println("加锁失败，进入自旋");
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //睡眠一会儿再尝试获取锁
            lock(uuid, lockKey, couponId);
        }

    }

}