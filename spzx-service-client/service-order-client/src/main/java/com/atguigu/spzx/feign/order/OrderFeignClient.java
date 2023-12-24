package com.atguigu.spzx.feign.order;

import com.atguigu.spzx.feign.order.impl.OrderFeignClientFallback;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-order" , fallback = OrderFeignClientFallback.class)
public interface OrderFeignClient {

    @GetMapping("/api/order/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    public Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable String orderNo) ;

    @GetMapping("/api/order/orderInfo/auth/updateOrderStatus/{orderNo}/{orderStatus}")
    public OrderInfo updateOrderStatus(@PathVariable String orderNo, @PathVariable Integer orderStatus);
}
