package com.atguigu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeVoService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeVoServiceImpl implements ValidateCodeVoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
//        使用hutool工具类生成图片验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
//      通过拿到对象获取,对应的值
//        图片验证码对应的字符串数据
        String codeValue = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();

//        生成uuid作为验证码的key,即是封装类中的codekey
        String codekey = UUID.randomUUID().toString().replace("-", "");

//        把token存到redis
        redisTemplate.opsForValue().set("user:login:code:"+codekey, codeValue, 5, TimeUnit.MINUTES);

//        把结果封装成对象,属性存入,并返回
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(codekey);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }
}
