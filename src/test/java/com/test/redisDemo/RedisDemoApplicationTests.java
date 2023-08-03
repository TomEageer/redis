package com.test.redisDemo;

import com.test.redisDemo.model.UserDO;
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
	void setTest2(){
		String name;
		name = "tom";
		redisTemplate.opsForValue().set("name", name);

	}

	@Test
	void getTest(){

		String str = (String) redisTemplate.opsForValue().get("name");

		System.out.println(str);
	}



}
