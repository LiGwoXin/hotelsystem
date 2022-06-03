package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.Permission;
import com.sdau.hotelsystem.service.PermissionService;
import com.sdau.hotelsystem.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */

@Transactional
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getByPermissionIds(List<Integer> permissionIds) {
        return permissionMapper.selectBatchIds(permissionIds);
    }
}




