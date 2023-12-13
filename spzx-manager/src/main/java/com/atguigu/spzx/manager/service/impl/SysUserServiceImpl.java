package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.common.service.util.AuthContextUtil;
import com.atguigu.spzx.manager.helper.MenuHelper;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional(readOnly = true)
    public LoginVo login(LoginDto loginDto) {
        String userName = loginDto.getUserName();
        String password = loginDto.getPassword();
//        获取验证码的数据
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
//        有了验证码首先需要先判断验证码是否正确,再判断账户密码
//        判断输入的验证码是否为空
        if (StrUtil.isEmpty(captcha)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_EMPTY);
        }
//      从redis中拿到验证码,比较是否相等,注意如果业务层向redis中添加的值有前缀，那么获取时，也必须加上
        String rediscode = redisTemplate.opsForValue().get("user:login:code:" + codeKey);
        if (!rediscode.equals(captcha)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
//      上面的验证码通过后，就删除redis中的验证码，避免同一验证码反复使用
        redisTemplate.delete("user:login:code:" + codeKey);

//        验证账号密码输入是否为空
        if (StrUtil.isEmpty(userName) || StrUtil.isEmpty(password)) {
//            throw new RuntimeException("账号或者密码为空");
            throw new GuiguException(ResultCodeEnum.NOT_EMPTY);
        }
//        通过得到的username去数据库中查询数据，得到对象，这样后面就能比较传入的参数和数据库是否对应
        SysUser sysUserByName = sysUserMapper.getSysUserByName(userName);
//      验证输入的账号是否存在于数据库,传入值，没有账号就是null，有就返回对应的对象
        if (sysUserByName == null) {
//            throw new RuntimeException("账号不存在");
            throw new GuiguException(ResultCodeEnum.ACCOUNT_NOT_EXIST);
        }
//        验证密码是否正确
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5DigestAsHex.equals(sysUserByName.getPassword())) {
//            throw new RuntimeException("密码不正确");
            throw new GuiguException(ResultCodeEnum.PASSWORD_ERROR);
        }
//        查看账号是否被禁用
        if (sysUserByName.getStatus() == 0) {

//            throw new RuntimeException("账号已禁用");
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
//        都没有问题了，就正常登录,生成token，并存入redis
        String token = UUID.randomUUID().toString().replace("-", "");

//        把SysUser对象转换为JSON字符串
        String sys = JSONObject.toJSONString(sysUserByName);
        redisTemplate.opsForValue().set("user:login:token:" + token, sys, 2, TimeUnit.HOURS);

//       因为设置了返回值类，所以确定了类，就可以生成对应的类对象，把数据添加后就返回
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
//        从redis中获取用户信息,并转化
//        String sysUserToken = redisTemplate.opsForValue().get("user:login:token:"+token);
//        return JSON.parseObject(sysUserToken, SysUser.class);
        SysUser sysUser = AuthContextUtil.get();
        return sysUser;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:token:" + token);
    }


}
