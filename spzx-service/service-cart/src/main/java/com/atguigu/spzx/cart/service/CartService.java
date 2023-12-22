package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.h5.TradeVo;

import java.util.List;

public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> showCartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void clearCart();

    List<CartInfo> getAllCkecked();

    void deleteChecked();
}
