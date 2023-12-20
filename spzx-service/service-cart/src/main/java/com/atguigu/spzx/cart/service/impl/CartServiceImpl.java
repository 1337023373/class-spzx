package com.atguigu.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.common.util.AuthContextUtil;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate redisTemplate;
    //    注入远程调用
    @Autowired
    private ProductFeignClient productFeignClient;

    //    通过用户id，定义redis中的hash的key值
//    Hash 可以包含多个字段（field），每个字段都有一个对应的值。可以通过字段名来访问和修改 Hash 中的值。
    private String getCartKey() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
//        获取当前线程中登录用户的id
//        Long userId = AuthContextUtil.getUserInfo().getId();
//        后面会反复获取，直接把id也一起提出去
//        通过userid获取redis中的购物车的key
        String cartKey = getCartKey();

//        通过key去redis中获取对应的value,因为封装类是CartInfo,所以需要转换，这里直接就强转
        CartInfo cartInfo = (CartInfo) redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

//        判断cartInfo是否为空，空就是说明里面没有对应的添加的数据，这样购物车的数字直接加上传入的skunum
//        如果不是空，那就是里面原本就有数据，那么他的数量就是原本的数量加上传入的skunum即可
        if (cartInfo != null) {
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
        } else {
//           没有这个商品,就需要把点击的商品的所有属性都添加到购物车中
            cartInfo = new CartInfo();
//            通过远程接口获取商品数据
            ProductSku productSku = productFeignClient.getBySkuId(skuId).getData();
//            给cartInfo设置属性
            cartInfo.setSkuId(skuId);
            cartInfo.setSkuNum(1);
            cartInfo.setIsChecked(1);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
        }
//        把数据重新放到reids中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), cartInfo);
    }

    /**
     * 展示购物车数据
     *
     * @return
     */
    @Override
    public List<CartInfo> showCartList() {
//        获取当前购物车的key值
        String cartKey = getCartKey();
//        拿到redis中的数据
        List<CartInfo> careInfoList = redisTemplate.opsForHash().values(cartKey);

        return careInfoList;
    }

    /**
     * 删除购物车
     *
     * @param skuId
     */
    @Override
    public void deleteCart(Long skuId) {
        String cartKey = getCartKey();
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(skuId));
    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        String cartKey = getCartKey();
//        hasKey用于检查指定 Hash 中是否存在指定的字段。
        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if (hasKey) {
            CartInfo cartInfo = (CartInfo) redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
            cartInfo.setIsChecked(isChecked);
//            设置后放在redis上
            redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), cartInfo);
        }

    }

    /**
     * 购物车的全选状态
     *
     * @param isChecked
     */
    @Override
    public void allCheckCart(Integer isChecked) {
        String cartKey = getCartKey();
//        根据key值获取商品信息,上面有方法展示过所有购物车数据
        List<CartInfo> cartInfoList = showCartList();
//          遍历每条数据
        cartInfoList.forEach(cartInfo -> {
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey, String.valueOf(cartInfo.getId()), cartInfo);
                }
        );
    }

    /**
     * 清空购物车
     */
    @Override
    public void clearCart() {
        String cartKey = getCartKey();
        redisTemplate.delete(cartKey);
    }


}
