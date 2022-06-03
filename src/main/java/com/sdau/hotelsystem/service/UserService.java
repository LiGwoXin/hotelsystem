package com.sdau.hotelsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sdau.hotelsystem.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sdau.hotelsystem.util.SearchInfo;

/**
 *
 */
public interface UserService extends IService<User> {

    User getByUserName(String userName);

    void banned(String userName);

//    IPage<UserDepartment> list(SearchInfo param);
    IPage<User> list(SearchInfo param);

    String getRole(Integer id);

    void upload(Integer id, String imagePath);

    User getOneById(Integer id);
}
