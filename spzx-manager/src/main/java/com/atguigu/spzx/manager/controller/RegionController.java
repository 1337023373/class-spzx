package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.RegionService;
import com.atguigu.spzx.model.entity.base.Region;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "地区信息接口")
@RestController
@RequestMapping("/admin/user/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @Operation(summary = "获取地区信息")
    @GetMapping("/findByParentCode/{parentCode}")
    public Result findByParentCode(@PathVariable Integer parentCode) {
       List<Region> regionList =  regionService.findByParentCode(parentCode);
        return Result.build(regionList, ResultCodeEnum.SUCCESS);
    }
}
