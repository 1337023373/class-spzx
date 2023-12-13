package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.manager.helper.MenuHelper;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

//    展示角色分配菜单,并根据其id展示对应菜单的权限
    @Override
    public Map<String, Object> getRoleMenuById(Long roleId) {
      List<SysMenu> sysMenuList =  sysMenuMapper.getSysMenu();
        List<SysMenu> sysMenusTree = MenuHelper.buildTree(sysMenuList);
        List<Long> sysRoleMenuId = sysRoleMenuMapper.getRoleMenu(roleId);

        Map<String, Object> map = new HashMap<>();
//        这里的key值要与发送axios请求后,赋值的data.xxx相同
        map.put("sysMenusTreeList", sysMenusTree);
        map.put("sysRoleMenuId", sysRoleMenuId);
        return map;
    }


//    提交分配菜单时,更新菜单权限
    @Override
    public void doAssignMenuId(AssginMenuDto assginMenuDto) {

//        更新菜单的完成方法是根据id先删除对应的数据,再进行添加
//        通过调用封装实体类,拿到对应的属性
        Long roleId = assginMenuDto.getRoleId();
        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
//        通过roleId去删除
       sysRoleMenuMapper.deleteRoleMenu(roleId);
        System.out.println("roleId = " + roleId);
//        再通过menuId添加
        if (!CollectionUtil.isEmpty(menuIdList)) {
            sysRoleMenuMapper.insertRoleMenu(assginMenuDto);
        }
    }
}
