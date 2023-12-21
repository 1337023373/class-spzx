package com.atguigu.spzx.order.service.impl;


import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.common.util.AuthContextUtil;
import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.feign.user.UserFeignClient;
import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.model.entity.h5.UserInfo;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
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

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

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

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
//        通过orderInfoDto 获取所有订单项
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
//        判断订单是否为空
        if (orderItemList == null) {
            throw new GuiguException(ResultCodeEnum.ORDER_EMPTY);
        }
//          校验商品库存是否足够
//        遍历OrderItem集合,校验库存是否足够,需要远程调用商品sku信息
        for (OrderItem orderItem : orderItemList) {
//            远程调用获取sku信息
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId()).getData();
            if (orderItem.getSkuNum().intValue() > productSku.getStockNum().intValue()) {
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
        }
//        添加数据到order_info表,需要远程调用获取用户地址
        OrderInfo orderInfo = new OrderInfo();
//        从当前线程获取数据
        UserInfo userInfo = AuthContextUtil.getUserInfo();
//        设置orderinfo的id,使用当前的时间戳这样的唯一的值
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        orderInfo.setUserId(userInfo.getId());
        orderInfo.setNickName(userInfo.getNickName());

//        看实现类发现后面很多数据是关于地址的,所以这里需要远程调用
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
//        获取总金额

//        添加数据到order_item表

//        添加到order_log表

//        把生成订单的商品,从购物车删除

//        返回订单id

    }
}
