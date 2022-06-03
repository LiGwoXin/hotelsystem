package com.sdau.hotelsystem.service;

import com.sdau.hotelsystem.domain.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface RoleMenuService extends IService<RoleMenu> {

    List<RoleMenu> getByRoleIds(List<Integer> roleIds);

    List<Integer> getByRoleId(Integer id);
}
