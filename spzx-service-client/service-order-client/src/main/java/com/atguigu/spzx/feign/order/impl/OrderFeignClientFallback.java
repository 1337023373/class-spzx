package com.atguigu.spzx.feign.order.impl;

import com.atguigu.spzx.feign.order.OrderFeignClient;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderFeignClientFallback implements OrderFeignClient {

    @Override
    public Result<OrderInfo> getOrderInfoByOrderNo(String orderNo) {
        log.info("OrderFeignClientFallback...getOrderInfoByOrderNo方法执行了");
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Override
    public OrderInfo updateOrderStatus(String orderNo, Integer orderStatus) {
        return null;
    }

}