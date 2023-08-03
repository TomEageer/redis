package com.test.redisDemo.controller;

import com.google.code.kaptcha.Producer;
import com.test.redisDemo.util.CommonUtil;
import com.test.redisDemo.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/captcha")
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Producer captchaProducer;


    @GetMapping("get_captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {

//        创建一段随机的验证文本并把它放在captchaText中
        String captchaText = captchaProducer.createText();
//        获取request中的验证码的密钥放入key中
        String key = getCaptchaKey(request);
//        把随机的验证文本丢进redis中，并设置过期时间
        redisTemplate.opsForValue().set(key, captchaText, 10, TimeUnit.MINUTES);
//        把文本转化为图片存到bufferedImage中
        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);
//        创建一个输出流
        ServletOutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 判断图形验证码是否正确，若正确发送手机短信验证码
     * to 目标手机号码
     * captcha 目标手机号所需要的图形验证码
     * @return
     */
    @GetMapping("send_code")
    public JsonData sendCode(@RequestParam(value = "to", required = true)String to,
                             @RequestParam(value = "captcha", required = true) String captcha,
                             HttpServletRequest request) {

//        先获取用户的key
        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);

        if(key != null && cacheCaptcha != null && cacheCaptcha.equalsIgnoreCase(captcha)){
            redisTemplate.delete(key);

            //TODO 发送手机短信验证码

            return JsonData.buildSuccess();

        }else {
            return JsonData.buildError("验证码不正确或者失效");
        }
    }

    /**
     * 把获取到的key进行处理一下，防止过长，redis中的key不应该过长
     *
     * @param request
     * @return
     */
    private String getCaptchaKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
//        redis的key不应该过长，为了解决这个问题，使用md5转换一下，避免浪费太多空间
        String key = "user-service:captcha:" + CommonUtil.MD5(ip + userAgent);

        return key;
    }


}
