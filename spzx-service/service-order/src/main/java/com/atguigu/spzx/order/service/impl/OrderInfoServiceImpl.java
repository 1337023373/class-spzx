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
import com.atguigu.spzx.model.entity.order.OrderLog;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.mapper.OrderInfoMapper;
import com.atguigu.spzx.order.mapper.OrderItemMapper;
import com.atguigu.spzx.order.mapper.OrderLogMapper;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderLogMapper orderLogMapper;

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
        orderInfo.setRemark(orderInfoDto.getRemark());
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());

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
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice()).multiply(new BigDecimal(orderItem.getSkuNum()));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.save(orderInfo);

//        添加数据到order_item表

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }
//        添加到order_log表
        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);
//        把生成订单的商品,从购物车删除
        cartFeignClient.deleteChecked();

//        返回订单id
        return orderInfo.getId();
    }

    /**
     * 立即购买选中的商品
     * @param skuId
     * @return
     */
    @Override
    public TradeVo buy(Long skuId) {
        ProductSku productSku = productFeignClient.getBySkuId(skuId).getData();
        TradeVo tradeVo = new TradeVo();
        OrderItem orderItem = new OrderItem();


        orderItem.setSkuId(productSku.getId());
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setSkuNum(1);

        List<OrderItem> orderItemOrderList = new ArrayList<>();
        orderItemOrderList.add(orderItem);
        tradeVo.setOrderItemList(orderItemOrderList);
        tradeVo.setTotalAmount(orderItem.getSkuPrice());

        return tradeVo;
    }

    /**
     * 获取订单信息,跳转支付页面
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getOrderInfo(orderId);
    }

    /**
     * 获取我的订单信息
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    @Override
    public PageInfo<OrderInfo> orderInfo(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page, limit);
        Long userInfoId = AuthContextUtil.getUserInfo().getId();
       List<OrderInfo> orderInfoList =  orderInfoMapper.orderInfo(userInfoId, orderStatus);

        return new PageInfo<>(orderInfoList);
    }

    /**\
     * 获取订单信息
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
//        根据订单号查询
       OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderNo(orderNo);
//       通过得到的订单里面的id去查询每个订单项
        List<OrderItem> orderItemByOrderId = orderItemMapper.getOrderItemByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItemByOrderId);

        return orderInfo;
    }

    /**
     * 通过orderNo，orderStatus远程调用
     * @param orderNo
     * @param orderStatus
     * @return
     */
    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {
//        OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderNo(orderNo);
//        orderInfo.setOrderStatus(orderStatus);
//        orderInfo.setPayType(2);
//        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderNo,orderStatus);

    }

}
