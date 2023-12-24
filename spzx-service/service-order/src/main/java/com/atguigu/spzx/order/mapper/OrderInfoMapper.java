package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import feign.Param;

import java.util.List;

public interface OrderInfoMapper {
    void save(OrderInfo orderInfo);

    OrderInfo getOrderInfo(Long orderId);

    List<OrderInfo> orderInfo(@Param("userId") Long userId, @Param("orderStatus") Integer orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    void updateById(String orderNo, Integer orderStatus);
}
