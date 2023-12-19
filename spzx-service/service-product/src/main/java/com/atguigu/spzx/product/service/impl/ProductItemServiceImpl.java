package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

import java.lang.reflect.Array;
import java.util.*;

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

    @Override
    public ProductItemVo item(Long skuId) {
//        创建VO对象，用于封装最终数据
        ProductItemVo productItemVo = new ProductItemVo();
//       根据skuId 查询商品sku信息
        ProductSku productSku = productSkuMapper.findProductSkuById(skuId);
//        通过得到的sku信息，从中得到productId，从而获取商品信息
        Long productId = productSku.getProductId();
        Product product = productMapper.findProductById(productId);
//        获取轮播图,,直接通过mapper拿到url
        String sliderUrls = product.getSliderUrls();
//          把字符串切割成数组
        String[] split = sliderUrls.split(",");
//          转化为集合
        List<String> SliderUrl = Arrays.asList(split);

//        通过productId获取商品详细图片信息
        String productDetailsUrl = productDetailsMapper.findProductDetailsById(productId);
        String[] split1 = productDetailsUrl.split(",");
        List<String> DetailsUrl = Arrays.asList(split1);

//        商品规格信息,直接通过product的查询能得到他的属性
        String specValue = product.getSpecValue();
        JSONArray parseArray = JSON.parseArray(specValue);
//        封装map集合，
//        根据商品id获取商品所有的sku列表,直接拿上面的sku信息来用
        List<ProductSku> productSkuList = productSkuMapper.findProductSkuByParendId(productId);
        Map<String, Object> skuSpecValueMap = new HashMap<>();
//        因为每个sku商品信息都对应一个id,所以遍历得到的productSkuList，把他的属性和id都存入map中
        productSkuList.forEach(item ->{
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });
//        把需要的数据封装到vo中
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSliderUrlList(SliderUrl);
        productItemVo.setDetailsImageUrlList(DetailsUrl);
        productItemVo.setSpecValueList(parseArray);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        return productItemVo;
    }
}
