package com.atguigu.spzx.model.dto.system;

import lombok.Data;

@Data
public class SysOperLogDto {
    private String title;
    private String operName;
    private String createTimeBegin;
    private String createTimeEnd;
}
