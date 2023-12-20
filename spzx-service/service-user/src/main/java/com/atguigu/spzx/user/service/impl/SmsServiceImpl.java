package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.user.service.SmsService;
import com.atguigu.spzx.user.utils.SmsUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public void sendCode(String phone) {
        String randomNumeric = RandomStringUtils.randomNumeric(4);
//        调用smsutils类向手机发送消息
//        SmsUtils.sendCode(phone,randomNumeric);
        redisTemplate.opsForValue().set("phone:code:" + phone, randomNumeric);

    }
}
