package com.atguigu.spzx.model.entity.user;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户浏览商品历史实体类")
public class UserBrowseHistory extends BaseEntity {

    @Schema(description = "唯一标识")
    private Long id ;

    @Schema(description = "用户id")
    private Long userId ;

    @Schema(description = "商品id")
    private Long skuId ;


}
