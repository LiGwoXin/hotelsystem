package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.RoleMenu;
import com.sdau.hotelsystem.service.RoleMenuService;
import com.sdau.hotelsystem.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */

@Transactional
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<RoleMenu> getByRoleIds(List<Integer> roleIds) {
        return roleMenuMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<Integer> getByRoleId(Integer id) {
        return roleMenuMapper.selectMenuIdByRoleId(id);
    }
}




