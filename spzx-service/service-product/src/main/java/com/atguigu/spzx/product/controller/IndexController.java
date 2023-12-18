package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.IndexVo;
import com.atguigu.spzx.product.service.CategotyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@Tag(name = "首页商品展示接口")
//配置了网关,就不用再配跨域注解了 ,直接通过网关转发
//@CrossOrigin(allowedHeaders = "*", allowCredentials = "true",originPatterns = "*")
public class IndexController {
    @Autowired
    private CategotyService categotyService;

    @GetMapping("/index")
    @Operation(summary = "获取首页数据")
    public Result Index() {
        IndexVo indexVo = categotyService.Index();
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }
}
