package com.atguigu.spzx.pay.mapper;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;

public interface PaymentInfoMapper {
    PaymentInfo savePaymentInfo(String orderNo);

    void save(PaymentInfo paymentInfo);

    PaymentInfo getPaymentInfoByOrderNo(String out_trade_no);

    void updateById(PaymentInfo paymentInfo);
}
