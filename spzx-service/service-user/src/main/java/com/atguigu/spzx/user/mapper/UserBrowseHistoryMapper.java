package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface UserBrowseHistoryMapper {
    void add(@Param("userId") Long userId, @Param("skuId") Long skuId);

    List<UserBrowseHistory> find(Long userId);
}
