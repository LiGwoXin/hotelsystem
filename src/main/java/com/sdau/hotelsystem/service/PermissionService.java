package com.sdau.hotelsystem.service;

import com.sdau.hotelsystem.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> getByPermissionIds(List<Integer> permissionIds);
}
