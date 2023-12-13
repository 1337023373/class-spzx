package com.atguigu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.manager.listener.CategoryListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategory(Long parentId) {
//        先查询所有数据
        List<Category> categoryList = categoryMapper.getCategory(parentId);

//        前端通过hasChildren属性,来判断是否有子菜单,所以这里需要通过id去判断,
/*
        Lambda 表达式 category -> { } 定义了一个匿名函数，它接受一个名为 category 的参数，并在花括号 { } 内定义了要执行的操作。
您可以在花括号内编写要执行的代码，以对每个 category 元素进行自定义操作
*/
        categoryList.forEach(category -> {
//            通过遍历出来的数据的id,再去表中查找,如果parentid有跟他相同的,就说明有子菜单
           Integer count =  categoryMapper.getCountByParentId(category.getId());

//添加HasChildren的属性值,有子菜单就大于0,为true,没有子菜单就为false
           category.setHasChildren(count>0);
        });
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {

        try {

            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            // 查询数据库中的数据
            List<Category> categoryList = categoryMapper.selectAll();
//            因为数据库返回的是category的list，而需要的是CategoryExcelVo,所以需要转化
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

            // 将从数据库中查询到的Category对象转换成CategoryExcelVo对象
            for(Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();

//                这里的工具类是把category类和categoryExcelVo中，相同的进行复制，前者复制到后者
//                把对象中相同的属性复制给另一个对象，要弄清楚复制的顺序
//                常规的，是通过get读取，再set写入到另一个中
                BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class);
                categoryExcelVoList.add(categoryExcelVo);
            }

            // 写出数据到浏览器端,获取核心输入流，doWrite，是数据库返回的数据
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importData(MultipartFile file) {
        // 使用EasyExcel解析数据
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class,new CategoryListener(categoryMapper)).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
