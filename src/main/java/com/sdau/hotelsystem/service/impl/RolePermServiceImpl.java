package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.RolePerm;
import com.sdau.hotelsystem.service.RolePermService;
import com.sdau.hotelsystem.mapper.RolePermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Transactional
@Service
public class RolePermServiceImpl extends ServiceImpl<RolePermMapper, RolePerm>
    implements RolePermService{

    @Autowired
    private RolePermMapper rolePermMapper;
    @Override
    public List<RolePerm> getByRoleIds(List<Integer> roleIds) {
        return rolePermMapper.selectBatchIds(roleIds);
    }
}




