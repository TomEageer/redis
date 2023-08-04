package com.test.redisDemo.dao;

import com.test.redisDemo.model.VideoCardDO;
import com.test.redisDemo.model.VideoDO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class VideoCardDao {

    public List<VideoCardDO> list(){

        /**
         * 模拟数据库查询耗时
         */
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /**
         *模拟视频列表
         */
        List<VideoCardDO> cardDOList = new ArrayList<>();

        VideoCardDO videoCardDO = new VideoCardDO();

        videoCardDO.setId(1);
        videoCardDO.setTitle("这个是标题");

        VideoDO videoDO1 = new VideoDO(1, "这只是一个视频title1", "图片的地址", 10);
        VideoDO videoDO2 = new VideoDO(2, "这只是一个视频title2", "图片的地址", 110);
        VideoDO videoDO3 = new VideoDO(3, "这只是一个视频title3", "图片的地址", 1110);
        VideoDO videoDO4 = new VideoDO(4, "这只是一个视频title4", "图片的地址", 11110);
        VideoDO videoDO5  = new VideoDO(5, "这只是一个视频title5", "图片的地址", 111110);

        List<VideoDO> videoDOS = new ArrayList<>();
        videoDOS.add(videoDO1);
        videoDOS.add(videoDO2);
        videoDOS.add(videoDO3);
        videoDOS.add(videoDO4);
        videoDOS.add(videoDO5);

        videoCardDO.setList(videoDOS);


        VideoCardDO videoCardDO2 = new VideoCardDO();

        videoCardDO2.setId(1);
        videoCardDO2.setTitle("这个是标题");

        VideoDO videoDO6 = new VideoDO(6, "这只是一个视频title6", "图片的地址", 100);
        VideoDO videoDO7 = new VideoDO(7, "这只是一个视频title7", "图片的地址", 1100);
        VideoDO videoDO8 = new VideoDO(8, "这只是一个视频title8", "图片的地址", 11100);
        VideoDO videoDO9 = new VideoDO(9, "这只是一个视频title9", "图片的地址", 111100);
        VideoDO videoDO10  = new VideoDO(10, "这只是一个视频title10", "图片的地址", 1111100);

        List<VideoDO> videoDOS2 = new ArrayList<>();
        videoDOS2.add(videoDO1);
        videoDOS2.add(videoDO2);
        videoDOS2.add(videoDO3);
        videoDOS2.add(videoDO4);
        videoDOS2.add(videoDO5);

        videoCardDO2.setList(videoDOS2);

        cardDOList.add(videoCardDO);
        cardDOList.add(videoCardDO2);

        return cardDOList;

    }
}
