package com.atguigu.spzx.common.service.exception;


import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiguException extends RuntimeException {
    private Integer code;
    private String message;

    public GuiguException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
