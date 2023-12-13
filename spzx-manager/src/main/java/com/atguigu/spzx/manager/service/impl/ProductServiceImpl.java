package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper ;
    @Autowired
    private ProductSkuMapper productSkuMapper ;
    @Autowired
    private ProductDetailsMapper productDetailsMapper ;

    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> productList = productMapper.showAll(productDto);
        return new PageInfo<>(productList);
    }

    @Override
    public void SaveProduct(Product product) {
//       先把product表中的数据进行添加
        productMapper.addAll(product);
//        设置商品库存


//        获取商品的sku信息
        List<ProductSku> productSkuList = productSkuMapper.getproductSkuList();

//        遍历能得到表中所有数据,但是前端填写并回传的并没有这么多,所以差什么,就添加什么
        for (int i = 0; i < productSkuList.size(); i++) {
//
            ProductSku productSku = productSkuList.get(i);
//            设置并添加skuCode
            productSku.setSkuCode(product.getId() + "_" + i);
//            设置skuName
            productSku.setSkuName(product.getName() + " " + productSku.getSkuSpec());
            // 设置上架状态
            productSku.setStatus(0);
//            设置销量
            productSku.setSaleNum(0);
//            设置商品id
            productSku.setProductId(product.getId());

            productSkuMapper.save(productSku);
        }
//  获取轮播图地址
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }

//    根据id获取数据
    @Override
    public Product getById(Long id) {
       Product product =  productMapper.getById(id);
        System.out.println(product.getId());

        // 根据商品的id查询sku数据
        List<ProductSku> productSkuList = productSkuMapper.getById(id);
        product.setProductSkuList(productSkuList);

        ProductDetails productDetails = productDetailsMapper.getById(product.getId());
       product.setDetailsImageUrls(productDetails.getImageUrls());

        return product;
    }

//    更新
    @Override
    public void updateById(Product product) {
        productMapper.updateById(product);

//        获取product中所有的sku的数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (ProductSku sku : productSkuList) {
//            调用mapper方法,把遍历出来的数据挨个更新
            productSkuMapper.updateById(sku);
        }

//        先获取图片的地址
        String urls = product.getDetailsImageUrls();
//        通过id拿到原本的图片,这里和上面通过id展示数据一样的
        ProductDetails productDetails = productDetailsMapper.getById(product.getId());
//        在把更新的图片添加进去
        product.setDetailsImageUrls(urls);
//        更新保存
        productDetailsMapper.updateById(productDetails);
    }

//    删除
    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);
        productSkuMapper.deleteById(id);
        productDetailsMapper.deleteById(id);

    }
}
