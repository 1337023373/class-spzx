package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.dto.user.UserLoginDto;
import com.atguigu.spzx.model.dto.user.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import com.atguigu.spzx.model.entity.user.UserCollect;
import com.atguigu.spzx.model.vo.user.UserInfoVo;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserInfoService {
    void userRegister(UserRegisterDto userRegisterDto);


    String login(UserLoginDto userLoginDto, HttpServletRequest request);


    UserInfoVo getCurrentUserInfo(String token);


    Boolean collect( Long skuId);

    Boolean cancelCollect(Long skuId);

    Boolean isCollect(Long skuId);

    PageInfo<UserCollect> findUserCollectPage(Integer page, Integer limit);

   void userBrowseHistory(Long userId, Long skuId);

    PageInfo<UserBrowseHistory> findUserBrowseHistoryPage(Integer page, Integer limit);
}
