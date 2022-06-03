package com.sdau.hotelsystem.service;

import com.sdau.hotelsystem.domain.RolePerm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface RolePermService extends IService<RolePerm> {

    List<RolePerm> getByRoleIds(List<Integer> roleIds);
}
