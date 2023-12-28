package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.RegionMapper;
import com.atguigu.spzx.manager.service.RegionService;
import com.atguigu.spzx.model.entity.base.Region;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {
    @Resource
    private RegionMapper regionMapper;
    @Override
    public List<Region> findByParentCode(Integer parentCode) {
       List<Region> regionList = regionMapper.findByParentCode(parentCode);
        return regionList;
    }
}
