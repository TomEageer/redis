package com.test.redisDemo.controller;

import com.test.redisDemo.dao.VideoCardDao;
import com.test.redisDemo.model.VideoCardDO;
import com.test.redisDemo.service.VideoCardService;
import com.test.redisDemo.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/video/card")
public class VideoCardController {

    @Autowired
    private VideoCardService videoCardService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存key
     */
    private static final String VIDEO_CARD_CACHE_KEY = "video:card:key";


    @GetMapping("list_cache")
    public JsonData listCardCache() {


        Object obj = redisTemplate.opsForValue().get(VIDEO_CARD_CACHE_KEY);
        if (obj != null) {
            List<VideoCardDO> list = (List<VideoCardDO>) obj;


            return JsonData.buildSuccess(list);
        } else {

            List<VideoCardDO> list = videoCardService.list();
            redisTemplate.opsForValue().set(VIDEO_CARD_CACHE_KEY, list, 10, TimeUnit.MINUTES);

            return JsonData.buildSuccess(list);
        }
    }

    @GetMapping("list_noCache")
    public JsonData listCardNocache() {
        List<VideoCardDO> list = videoCardService.list();

        return JsonData.buildSuccess(list);
    }
}
