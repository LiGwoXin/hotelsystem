package com.sdau.hotelsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Room;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;

import java.util.List;

/**
 *
 */
public interface RoomService extends IService<Room> {

    IPage<Room> list(Page page, ExcludeEmptyQueryWrapper<Room> queryWrapper);

    List<Room> all();

    List<Integer> listIds();

    List<Room> getByIds(List roomIds);
}
