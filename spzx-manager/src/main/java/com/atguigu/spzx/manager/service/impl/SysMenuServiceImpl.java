package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.service.util.AuthContextUtil;
import com.atguigu.spzx.manager.helper.MenuHelper;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;


@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getSysMenu() {
        List<SysMenu> sysMenuList = sysMenuMapper.getSysMenu();
//     通过引入树形菜单的工具类,把得到的数据放进工具类返回
        if (sysMenuList.isEmpty()) {
            return null;
        } else {
            List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList);
            return treeList;
        }
    }

    @Override
    public void addSysMenu(SysMenu sysMenu) {
        sysMenuMapper.addSysMenu(sysMenu);
    }

    @Override
    public void updateSysMenu(SysMenu sysMenu) {
        sysMenuMapper.updateSysMenu(sysMenu);
    }

    @Override
    public void removeSysMenu(Long id) {
        sysMenuMapper.removeSysMenu(id);
    }

    @Override
    public List<SysMenuVo> getSysRoleMenu() {
//        登录后就拿到id,并通过id展示对应的权限菜单,而这个id不需要单独传,通过拦截器里面的
//        存储在当前线程中的信息,可以拿到id
        SysUser sysUser = AuthContextUtil.get();
        Long sysUserId =  sysUser.getId();
        System.out.println(sysUserId);
        List<SysMenu> sysMenuList = sysMenuMapper.getSysRoleMenu(sysUserId);
//       把得到的集合数据转化为tree型数据
        List<SysMenu> sysMenuTree = MenuHelper.buildTree(sysMenuList);
        return this.buildMenus(sysMenuTree);
    }
    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
