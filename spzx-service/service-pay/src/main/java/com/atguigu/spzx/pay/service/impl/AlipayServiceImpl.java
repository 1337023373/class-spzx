package com.atguigu.spzx.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.pay.properties.AlipayProperties;
import com.atguigu.spzx.pay.service.AlipayService;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
@Service
@Transactional
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private PaymentInfoService paymentInfoService;
    @Autowired
    private AlipayProperties alipayProperties ;
    @SneakyThrows  // lombok的注解，对外声明异常
    @Override
    public String submitAlipay(String orderNo) {

        //保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
//        商户订单号
        map.put("out_trade_no",paymentInfo.getOrderNo());
//        手机网站支付默认传值
        map.put("product_code","QUICK_WAP_WAY");
//        支付金额
        map.put("total_amount",0.01);
//        订单标题
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));


//          alipayRequest.setBizContent(map.toString());
//        AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest,"POST");
// 如果需要返回GET请求，请使用
// AlipayTradeWapPayResponse response = alipayClient.pageExecute(request,"GET");
//        String pageRedirectionData = response.getBody();
//        System.out.println(pageRedirectionData);
        // 发送请求
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest,"POST");
        if(response.isSuccess()){
            log.info("调用成功");
            return response.getBody();
        } else {
            log.info("调用失败");
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
