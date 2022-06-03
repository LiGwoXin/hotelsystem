package com.sdau.hotelsystem.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.sdau.hotelsystem.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.sdau.hotelsystem.domain.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectAllByParentId(@Param("parentId") Integer parentId);

    List<Integer> selectId();
}




