package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrderInfoService {
    TradeVo getOrderInfoTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    TradeVo buy(Long skuId);

    OrderInfo getOrderInfo(Long orderId);

    PageInfo<OrderInfo> orderInfo(Integer page, Integer limit, Integer orderStatus);
}
