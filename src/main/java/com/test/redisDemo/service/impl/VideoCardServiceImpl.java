package com.test.redisDemo.service.impl;

import com.test.redisDemo.dao.VideoCardDao;
import com.test.redisDemo.model.VideoCardDO;
import com.test.redisDemo.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PrimitiveIterator;

@Service
public class VideoCardServiceImpl implements VideoCardService {

    @Autowired
    private VideoCardDao videoCardDao;

    @Override
    public List<VideoCardDO> list() {

        return videoCardDao.list();
    }
}
