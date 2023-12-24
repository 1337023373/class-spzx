package com.atguigu.spzx.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.feign.order.OrderFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.pay.mapper.PaymentInfoMapper;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private OrderFeignClient orderFeignClient;

    @Resource
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
//        通过订单号查询所有
       PaymentInfo paymentInfo =  paymentInfoMapper.savePaymentInfo(orderNo);
//         这不能直接插入信息,因为用户有可能点到支付后,却并不支付,那么后面再点过来时,如果表中有这个订单号的数据
//        则说明尚未支付,那么直接使用着数据即可
        if (paymentInfo == null) {
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo).getData();
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.save(paymentInfo);
        }

        return paymentInfo;
    }

    @Override
    public void updatePaymentStatus(Map<String, String> paramMap) {
//        查询PaymentInfo
       PaymentInfo paymentInfo =  paymentInfoMapper.getPaymentInfoByOrderNo(paramMap.get("out_trade_no"));
//         判断是否已支付,已经支付的状态为1
        if (paymentInfo.getPaymentStatus() == 1) {
            return;
        }
//       更新对应的数据
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(paramMap.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(paramMap));
        paymentInfoMapper.updateById(paymentInfo);

//        远程调用order，修改状态
        orderFeignClient.updateOrderStatus(paymentInfo.getOrderNo(), 1);

//        远程调用商品，修改商品的数量
//        通过orderNo,找到所有订单
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo()).getData();
        List<OrderItem> orderItemList = orderInfo.getOrderItemList();
        orderItemList.forEach(orderItem -> {
            productFeignClient.updateSkuSaleNum(orderItem.getSkuId(),orderItem.getSkuNum());
        });
    }


}
