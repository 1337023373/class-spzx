package com.atguigu.spzx.order.controller;


import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}