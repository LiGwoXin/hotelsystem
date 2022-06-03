package com.sdau.hotelsystem.service;

import com.sdau.hotelsystem.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getByIds(List<Integer> menuIds);

    List<Menu> initMenu(List<Integer> menuIds);
    List<Menu> listMenu(List<Menu> menus,List<Integer> rmIds);
    List<Integer> getIds();
}
