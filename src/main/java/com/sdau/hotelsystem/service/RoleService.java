package com.sdau.hotelsystem.service;

import com.sdau.hotelsystem.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface RoleService extends IService<Role> {
    List<Role> getByIds(List<Integer> roleIds);
}
