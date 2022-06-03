package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.Menu;
import com.sdau.hotelsystem.domain.RoleMenu;
import com.sdau.hotelsystem.mapper.RoleMenuMapper;
import com.sdau.hotelsystem.service.MenuService;
import com.sdau.hotelsystem.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@Transactional
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> getByIds(List<Integer> menuIds) {
        return menuMapper.selectBatchIds(menuIds);
    }

    @Override
    public List<Menu> initMenu(List<Integer> menuIds) {
        List<Menu> menus = new ArrayList<>();
        menuIds.forEach(id -> {
            Menu menu = menuMapper.selectById(id);
            if (menu.getHasChild() != 0) {
                List<Menu> childs = menuMapper.selectAllByParentId(menu.getId());
                menu.setChildren(childs);
            }
            if(menu.getParentId()==0) {
                menus.add(menu);
            }
        });
        return menus;
    }

    @Override
    public List<Menu> listMenu(List<Menu> menuList,List<Integer> rmIds) {
        List<Menu> menus = new ArrayList<>();
        menuList.forEach(menu -> {
            for(Integer rmId : rmIds){
                if(rmId == menu.getId()){
                    menu.setChecked("true");
                    break;
                }
            }
            if (menu.getHasChild() != 0) {
                List<Menu> childs = menuMapper.selectAllByParentId(menu.getId());
                menu.setChildren(childs);
            }

            if(menu.getParentId()==0) {
                menus.add(menu);
            }
        });
        return menus;
    }

    @Override
    public List<Integer> getIds() {
        return menuMapper.selectId();
    }
}




