package com.test.redisDemo.dao;

import com.test.redisDemo.model.VideoDO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VideoDao {

    private static Map<Integer, VideoDO> map = new HashMap<>();

    static {
        map.put(1,new VideoDO(1,"视频1","地址1",1099));
        map.put(2,new VideoDO(2,"视频2","地址2",79));
        map.put(3,new VideoDO(3,"视频3","地址3",49));
        map.put(4,new VideoDO(4,"视频4","地址4",49));
        map.put(5,new VideoDO(5,"视频5","地址5",49));
        map.put(6,new VideoDO(6,"视频6","地址6",59));
    }


    /**
     * 模拟从数据库找
     * @param videoId
     * @return
     */
    public VideoDO findDetailById(int videoId) {
        return map.get(videoId);
    }
}