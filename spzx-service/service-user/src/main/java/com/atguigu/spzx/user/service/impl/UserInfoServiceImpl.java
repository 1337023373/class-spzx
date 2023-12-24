package com.atguigu.spzx.user.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.common.util.AuthContextUtil;
import com.atguigu.spzx.common.util.IpUtil;

import com.atguigu.spzx.model.dto.user.UserLoginDto;
import com.atguigu.spzx.model.dto.user.UserRegisterDto;

import com.atguigu.spzx.model.entity.h5.UserInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.user.UserInfoVo;
import com.atguigu.spzx.user.mapper.UserCollectMapper;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StreamUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserCollectMapper userCollectMapper;


    @Autowired
    protected RedisTemplate<String,String> redisTemplate;
    @Override
    public void userRegister(UserRegisterDto userRegisterDto) {

        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();
//        先非空校验，看dto中的数据是否为空
        if (username == null || password == null || nickName == null || code == null) {
            throw new GuiguException(ResultCodeEnum.NOT_EMPTY);
        }
//        校验验证码
        String redisCode = redisTemplate.opsForValue().get("phone:code:" + username);
//        redisTemplate.opsForValue().set("phone:code:15703844444",code);
//        Object redisCode = redisTemplate.opsForValue().get("phone:code:15703844444");
        if (!code.equals(redisCode)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
//        通过dto拿到数据库中的数据
       UserInfo userInfo =  userInfoMapper.userRegister(username);
//        如果用户名不为空，说明该用户已被注册
        if (null != userInfo) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_EXIST);
        }

        userInfo = new UserInfo();
//        都没有问题就往用户注册表中添加,
        userInfo.setUsername(username);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setNickName(nickName);
//        后面添加的是根据表中哪些数据不是自动生成的来添加,因为不添加就是null
        userInfo.setSex(1);
        userInfo.setStatus(1);
        userInfoMapper.save(userInfo);

//        添加完毕后删除code,实际上应该在每次校验验证码后就失效,这里是为了方便放在最后
        redisTemplate.delete(redisCode);
    }

    /*
    *   会员登录
    * */
    @Override
    public String login(UserLoginDto userLoginDto, HttpServletRequest request) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
//    先做非空判断
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            throw new GuiguException(ResultCodeEnum.NOT_EMPTY);
        }
        UserInfo userInfo = userInfoMapper.userRegister(username);
//        再判断username对应的表中的数据是否存在
        if (userInfo == null) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_NOT_EXIST);
        }
//        判断userame是否被封
        if (userInfo.getStatus() == 0) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
//        判断密码是否正确
        if (!userInfo.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            throw new GuiguException(ResultCodeEnum.PASSWORD_ERROR);
        }
//        更新用户信息
        String ipAddress = IpUtil.getIpAddress(request);
        userInfo.setLastLoginIp(ipAddress);
        userInfo.setLastLoginTime(new Date());
        userInfoMapper.update(userInfo);
//        都通过后,通过UUID生成一个字符串作为token
        String token = UUID.randomUUID().toString().replace("-", "");
//        把token作为key,返回的数据作为value(为了方便识别,一般要转换为JSON字符串)放在redis上
        redisTemplate.opsForValue().set("user:login:" + token, JSONObject.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;
    }

    /*
    * 登录后展示会员信息
    * 这里可以通过token直接拿到userInfo内的所有值直接返回，但是有个实体类，且只要其中的两个属性，所有需要转换下
    * 尤其需要注意此从redis中查到的值是JSON格式，所以需要转化
    * */
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
////        根据保存的token去数据库查找
//        String userInfoJSON = redisTemplate.opsForValue().get("user:login:" + token);
////        判断是否为空
//        if (StrUtil.isEmpty(userInfoJSON)) {
//            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
//        }
//        UserInfo userInfo = JSONObject.parseObject(userInfoJSON, UserInfo.class);
        //从AuthContextUtil中获取用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        //创建UserInfoVo对象
        UserInfoVo userInfoVo = new UserInfoVo();
        //复制属性值
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }

    /**
     * 用户收藏
     */
    @Override
    public Boolean collect(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        userCollectMapper.add(userId,skuId);
        return true;
    }

    /**
     * 用户取消收藏
     */
    @Override
    public void cancelCollect(Long skuId) {

        userCollectMapper.delete(skuId);

    }

}
