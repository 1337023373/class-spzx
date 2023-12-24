package com.atguigu.spzx.order.controller;


import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "结算界面购物车商品接口")
@RestController
@RequestMapping("api/order/")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Operation(summary = "展示购物车商品")
    @GetMapping("/orderInfo/auth/trade")
    public Result getOrderInfoTrade() {
        TradeVo tradeVo = orderInfoService.getOrderInfoTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "提交订单")
    @PostMapping("/orderInfo/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "立即购买选择的商品")
    @GetMapping("/orderInfo/auth/buy/{skuId}")
    public Result buy(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单信息,跳转支付页面")
    @GetMapping("/orderInfo/auth/{orderId}")
    public Result getOrderInfo(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取我的订单接口")
    @GetMapping("/orderInfo/auth/{page}/{limit}")
    public Result orderInfo(@PathVariable Integer page, @PathVariable Integer limit, Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.orderInfo(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "通过订单号获取订单信息")
    @GetMapping("/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    public Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable String orderNo) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNo(orderNo);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "远程调用支付后通过订单号修改订单信息状态")
    @GetMapping("/orderInfo/auth/updateOrderStatus/{orderNo}/{orderStatus}")
    public void updateOrderStatus(@PathVariable String orderNo, @PathVariable Integer orderStatus) {
        orderInfoService.updateOrderStatus(orderNo, orderStatus);
    }
}
