package com.atguigu.spzx.order.service.impl;


import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CartFeignClient cartFeignClient;

    @Override
    public TradeVo getOrderInfoTrade() {

        TradeVo tradeVo = new TradeVo();

        List<OrderItem> orderItemOrderList = new ArrayList<>();
//        远程调用得到cart模块中的所有选中的商品
        List<CartInfo> cartInfoList = cartFeignClient.getAllCkecked().getData();
        cartInfoList.forEach(cartInfo -> {
            OrderItem orderItem = new OrderItem();
                    orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setSkuNum(cartInfo.getSkuNum());
                    orderItemOrderList.add(orderItem);
                }
        );
//        for (int i = 0; i < cartInfoList.size(); i++) {
//            CartInfo cartInfo = cartInfoList.get(i);
//            OrderItem orderItem = new OrderItem();
//                    orderItem.setSkuId(cartInfo.getSkuId());
//            orderItem.setSkuName(cartInfo.getSkuName());
//            orderItem.setThumbImg(cartInfo.getImgUrl());
//            orderItem.setSkuPrice(cartInfo.getCartPrice());
//            orderItem.setSkuNum(cartInfo.getSkuNum());
//                    orderItemOrderList.add(orderItem);
//        }
        // cartInfoList.forEach(e->orderItemOrderList.add(new OrderItem()));
//       计算总金额,通过数量*单价
        BigDecimal totalBigDecimal = new BigDecimal(0);
//        orderItemOrderList.forEach(item ->
//               totalBigDecimal.add(item.getSkuPrice()).multiply(new BigDecimal(item.getSkuNum())));
        for(OrderItem item : orderItemOrderList) {
            totalBigDecimal = totalBigDecimal.add(item.getSkuPrice().multiply(new BigDecimal(item.getSkuNum())));
        }
        tradeVo.setTotalAmount(totalBigDecimal);
        tradeVo.setOrderItemList(orderItemOrderList);
        return tradeVo;
    }
}
