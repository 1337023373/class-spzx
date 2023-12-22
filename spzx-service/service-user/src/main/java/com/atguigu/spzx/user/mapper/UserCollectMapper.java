package com.atguigu.spzx.user.mapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface UserCollectMapper {


    void add(@Param("userId") Long userId,@Param("skuId")  Long skuId);

    void delete(Long skuId);
}
