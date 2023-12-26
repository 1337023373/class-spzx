package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserCollect;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface UserCollectMapper {


    void add(@Param("userId") Long userId,@Param("skuId")  Long skuId);

    void delete(Long skuId);

    List<UserCollect>  findIsCollect(Long skuId);

    List<UserCollect> findUserCollect(Long userId);
}
