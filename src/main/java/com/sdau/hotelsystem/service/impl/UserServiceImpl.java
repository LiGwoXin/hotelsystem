package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.service.UserService;
import com.sdau.hotelsystem.mapper.UserMapper;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import com.sdau.hotelsystem.util.SearchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUserName(String userName) {
        return userMapper.selectOneByUsername(userName);
    }

    @Override
    public void banned(String userName) {
        userMapper.updateStatusByUsername((byte) 1,userName);
    }

    @Override
    public IPage<User> list(SearchInfo param) {
        QueryWrapper<User> wrapper = new ExcludeEmptyQueryWrapper<User>();
        wrapper.like("user.name",param.getSearch());
//        wrapper.eq("user.status",0);
        Page<User> page = new Page<User>(param.getPage(), param.getLimit());
        // 对多表查询后的结果进行count(*)
        page.setOptimizeCountSql(false);
        IPage<User> res = userMapper.list(page, wrapper);
        return res;
    }

    @Override
    public String getRole(Integer id) {
        return userMapper.selectRoleById(id);
    }

    @Override
    public void upload(Integer id, String imagePath) {
        userMapper.updateImgById(imagePath,id);
    }

    @Override
    public User getOneById(Integer id) {
        return userMapper.selectOneById(id);
    }
}




