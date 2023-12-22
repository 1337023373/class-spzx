package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.dto.user.UserLoginDto;
import com.atguigu.spzx.model.dto.user.UserRegisterDto;
import com.atguigu.spzx.model.vo.user.UserInfoVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserInfoService {
    void userRegister(UserRegisterDto userRegisterDto);


    String login(UserLoginDto userLoginDto, HttpServletRequest request);


    UserInfoVo getCurrentUserInfo(String token);


    Boolean collect( Long skuId);

    void cancelCollect(Long skuId);
}
