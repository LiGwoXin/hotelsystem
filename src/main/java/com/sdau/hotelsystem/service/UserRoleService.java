package com.sdau.hotelsystem.service;

import com.sdau.hotelsystem.domain.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface UserRoleService extends IService<UserRole> {

    List<UserRole> getByUserId(Integer id);
}
