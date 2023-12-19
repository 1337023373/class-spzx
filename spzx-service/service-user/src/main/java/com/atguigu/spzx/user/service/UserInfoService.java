package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.dto.user.UserLoginDto;
import com.atguigu.spzx.model.dto.user.UserRegisterDto;
import com.atguigu.spzx.model.vo.user.UserInfoVo;
import jakarta.servlet.http.HttpServletRequest;

public interface UserInfoService {
    void userRegister(UserRegisterDto userRegisterDto);


    String login(UserLoginDto userLoginDto, HttpServletRequest request);

    void getCurrentUserInfo(UserInfoVo userInfoVo);
}
