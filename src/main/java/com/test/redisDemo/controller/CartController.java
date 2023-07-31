package com.test.redisDemo.controller;

import com.test.redisDemo.VO.CartItemVO;
import com.test.redisDemo.VO.CartVO;
import com.test.redisDemo.dao.VideoDao;
import com.test.redisDemo.model.VideoDO;
import com.test.redisDemo.util.JsonData;
import com.test.redisDemo.util.JsonUtil;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    RedisTemplate redisTemplate;

    //模拟Service
    @Autowired
    VideoDao videoDao;

    /**
     * @param videoId 商品对应的id
     * @param buyNum  购买的商品的数量
     * @return
     */
    @RequestMapping("add")
    public JsonData addCart(int videoId, int buyNum) {

        //获取带有用户id的购物车key（只要myCart发生变化，那么redis也会进行对应操作）
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();
        //获取id为videoId的对应的数据
        Object cacheObj = myCart.get(videoId + "");

        String result = "";


        //如果查询到的结果为空
        if (cacheObj == null) {
            CartItemVO cartItem = new CartItemVO();

            //获取对应商品id的数据
            VideoDO videoDO = videoDao.findDetailById(videoId);

            //获取对应的购物车的参数
            cartItem.setBuyNum(buyNum);
            cartItem.setPrice(videoDO.getPrice());
            cartItem.setProductId(videoDO.getId());
            cartItem.setProductImg(videoDO.getImg());
            cartItem.setProductTitle(videoDO.getTitle());

            //将对应的参数转化为json放入id为videoId的"我的购物车"中
            myCart.put(videoId + "", JsonUtil.objectToJson(cartItem));

        } else {
            //如果查询到用户的购物车中存在该商品，则将结果传给result
            result = (String) cacheObj;
            //将结果转化为CartItemVO对象存储到cartItemVO中
            CartItemVO cartItemVO = JsonUtil.jsonToPojo(result, CartItemVO.class);
            cartItemVO.setBuyNum(cartItemVO.getBuyNum() + buyNum);
            myCart.get(JsonUtil.objectToJson(cartItemVO));

        }

        return JsonData.buildSuccess();
    }

    /**
     * 查看"我的"购物车
     *
     * @return
     */
    @RequestMapping("/mycart")
    public JsonData getMyCart() {

        //获取带有用户id的购物车key
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();

        //获取全部的值
        List<Object> itemList = myCart.values();

        //将其转化为CartItemVO对象
        List<CartItemVO> cartItemVOList = new ArrayList<>();

        for (Object item : itemList) {
            //把item转化为String类型，然后再把目标对象传过去
            CartItemVO cartItemVO = JsonUtil.jsonToPojo((String)item, CartItemVO.class);
            cartItemVOList.add(cartItemVO);
        }

        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);

        return JsonData.buildSuccess(cartVO);
    }


    /**
     * 清空我的购物车
     * @return
     */
    @RequestMapping("clear")
    public JsonData clear(){

        String key = getCartKey();
        redisTemplate.delete(key);


        return JsonData.buildSuccess();
    }





    /**
     * 抽取我的购物车通用方法
     *
     * @return
     */
    private BoundHashOperations<String, Object, Object> getMyCartOps() {

        //获取用户的购物车的key
        String key = getCartKey();
        //返回带有用户key的购物车key
        return redisTemplate.boundHashOps(key);
    }

    private String getCartKey() {

        //模拟用户id， 从拦截器获取用户id
        int userId = 1;
        //将用户的id加到key中
        String cartKey = String.format("video:cart:%s", userId);
        //返回带有用户id的key
        return cartKey;
    }
}
