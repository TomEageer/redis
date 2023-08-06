package com.test.redisDemo;

import com.test.redisDemo.model.UserDO;
import com.test.redisDemo.model.VideoDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void setTest() {
        UserDO userDO = new UserDO();
        userDO.setName("tom");
        userDO.setPwd("123");

        redisTemplate.opsForValue().set("school:class:1", userDO);
    }

    @Test
    void setTest2() {
        String name;
        name = "tom";
        redisTemplate.opsForValue().set("name", name);

    }

    @Test
    void getTest() {

        String str = (String) redisTemplate.opsForValue().get("name");

        System.out.println(str);
    }

    @Test
    public void saveRank() {

        String DAILY_RANK_KEY = "video:rank:daily";

        VideoDO video1 = new VideoDO(3, "视频1", "地址1", 1099);
        VideoDO video2 = new VideoDO(5, "视频2", "地址2", 59);
        VideoDO video3 = new VideoDO(53, "视频3", "地址3", 49);
        VideoDO video4 = new VideoDO(15, "视频4", "地址4", 49);
        VideoDO video5 = new VideoDO(45, "视频5", "地址5", 89);

        redisTemplate.opsForList().leftPushAll(DAILY_RANK_KEY, video5, video4, video3, video2, video1);

    }

    @Test
    public void replaceRank() {

        String DAILY_RANK_KEY = "video:rank:daily";

        VideoDO videoDO = new VideoDO(4444, "视频修改过的", "地址修改过的", 1234);

        redisTemplate.opsForList().set(DAILY_RANK_KEY, 4, videoDO);

    }

}
