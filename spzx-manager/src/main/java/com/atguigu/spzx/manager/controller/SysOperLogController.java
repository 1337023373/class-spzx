package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysOperLogService;
import com.atguigu.spzx.model.dto.system.SysOperLogDto;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "操作日志接口")
@RestController
@RequestMapping("/admin/system/sysOperLog")
public class SysOperLogController {
    @Autowired
    private SysOperLogService sysOperLogService;

    @Operation(summary = "分页带条件查询")
    @GetMapping("/{current}/{size}")
    public Result getPageList(@PathVariable Integer current, @PathVariable Integer size, SysOperLogDto sysOperLogDto) {
        PageInfo<SysOperLog> sysOperLogPageInfo = sysOperLogService.getPageList(current, size, sysOperLogDto);
        return Result.build(sysOperLogPageInfo, ResultCodeEnum.SUCCESS);
    }
}
