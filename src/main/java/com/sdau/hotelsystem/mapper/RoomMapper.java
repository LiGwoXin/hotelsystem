package com.sdau.hotelsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.sdau.hotelsystem.domain.Room
 */
public interface RoomMapper extends BaseMapper<Room> {

    IPage<Room> list(Page page, @Param(Constants.WRAPPER) Wrapper<Room> ew);
    List<Room> all();

    List<Integer> selectId();
}




