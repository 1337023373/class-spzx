package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.base.Region;

import java.util.List;

public interface RegionMapper {
    List<Region> findByParentCode(Integer parentCode);
}
