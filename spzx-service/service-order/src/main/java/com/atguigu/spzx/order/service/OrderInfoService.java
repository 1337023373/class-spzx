package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.vo.h5.TradeVo;

import java.util.List;

public interface OrderInfoService {
    TradeVo getOrderInfoTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);
}
