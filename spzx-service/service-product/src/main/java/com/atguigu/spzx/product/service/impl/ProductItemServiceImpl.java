package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.mapper.ProductSpecMapper;
import com.atguigu.spzx.product.service.ProductItemService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class ProductItemServiceImpl implements ProductItemService {
//    注入商品sku信息
    @Resource
    private ProductSkuMapper productSkuMapper;
//    注入商品信息
    @Resource
    private ProductMapper productMapper;
//    注入轮播图
    @Resource
    private ProductDetailsMapper productDetailsMapper;
//    注入商品规格
    @Resource
    private ProductSpecMapper productSpecMapper;

    @Override
    public ProductItemVo item(Long skuId) {
//        创建VO对象，用于封装最终数据
        ProductItemVo productItemVo = new ProductItemVo();
//       根据skuId 查询商品sku信息
        ProductSku productSku = productSkuMapper.findProductSkuById(skuId);
//        通过得到的sku信息，从中得到productId，从而获取商品信息
        Long productId = productSku.getProductId();
        Product product = productMapper.findProductById(productId);
//        通过productId获取商品详细信息
        List<ProductDetails> productDetailsList= productDetailsMapper.findProductDetailsById(productId);
//        封装map集合，
//        根据商品id获取商品所有的sku列表
        List<ProductSku> productSkuList = productSkuMapper.findProductSkuByParendId(productId);
        Map<String, Object> skuSpecValueMap = new HashMap<>();
//        因为每个sku商品信息都对应一个id,所以遍历得到的productSkuList，把他的属性和id都存入map中
        productSkuList.forEach(item ->{
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });
//        把需要的数据封装到vo中


        return null;
    }
}
