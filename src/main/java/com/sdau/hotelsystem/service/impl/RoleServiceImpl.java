package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.Role;
import com.sdau.hotelsystem.service.RoleService;
import com.sdau.hotelsystem.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */

@Transactional
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getByIds(List<Integer> roleIds) {
        return roleMapper.selectBatchIds(roleIds);
    }
}




