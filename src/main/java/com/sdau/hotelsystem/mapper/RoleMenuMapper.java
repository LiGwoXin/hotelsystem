package com.sdau.hotelsystem.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.sdau.hotelsystem.domain.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.sdau.hotelsystem.domain.RoleMenu
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Integer> selectMenuIdByRoleId(@Param("roleId") Integer roleId);
}




