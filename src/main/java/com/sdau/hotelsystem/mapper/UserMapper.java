package com.sdau.hotelsystem.mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sdau.hotelsystem.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.sdau.hotelsystem.domain.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectOneByUsername(@Param("username") String username);

    int updateStatusByUsername(@Param("status") Byte status, @Param("username") String username);

//    IPage<UserDepartment> list(Page<UserDepartment> page, @Param(Constants.WRAPPER) Wrapper<UserDepartment> ew);
    IPage<User> list(Page<User> page, @Param(Constants.WRAPPER) Wrapper<User> ew);

    String selectRoleById(@Param("id") Integer id);

    int updateImgById(@Param("img") String img, @Param("id") Integer id);

    User selectOneById(@Param("id") Integer id);
}




