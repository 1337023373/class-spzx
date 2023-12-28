package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.base.Region;

import java.util.List;

public interface RegionService {
    List<Region> findByParentCode(Integer parentCode);
}
