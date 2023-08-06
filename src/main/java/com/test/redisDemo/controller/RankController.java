package com.test.redisDemo.controller;

import com.test.redisDemo.model.VideoDO;
import com.test.redisDemo.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rank")
public class RankController {

    @Autowired
    private RedisTemplate redisTemplate;

    public static String DAILY_RANK_KEY = "video:rank:daily";

    @RequestMapping("daily_rank")
    public JsonData videoDailyRank(){

        List<VideoDO> list = redisTemplate.opsForList().range(DAILY_RANK_KEY, 0, -1);

        return JsonData.buildSuccess(list);
    }

}
