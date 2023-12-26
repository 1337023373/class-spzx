package com.atguigu.spzx.model.dto.user;

import lombok.Data;

@Data
public class UserInfoDto {
	
	private String keyword;
	private Integer status;
	private String createTimeBegin;
	private String createTimeEnd;

}

