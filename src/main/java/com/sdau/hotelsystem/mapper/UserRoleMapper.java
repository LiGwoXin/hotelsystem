package com.sdau.hotelsystem.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.sdau.hotelsystem.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.sdau.hotelsystem.domain.UserRole
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> getAllByUserId(@Param("userId") Long userId);
}




