package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "上传文件接口")
@RestController
@RequestMapping("/admin/system/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Operation(summary = "上传文件")
    @PostMapping("/fileUpload")
    public Result upload(MultipartFile file){
        //调用FileUploadService中删除文件的方法
        String fileUrl = fileUploadService.upload(file);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
