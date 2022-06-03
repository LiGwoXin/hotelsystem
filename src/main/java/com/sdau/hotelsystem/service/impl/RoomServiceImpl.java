package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.Room;
import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.service.RoomService;
import com.sdau.hotelsystem.mapper.RoomMapper;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Transactional
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room>
    implements RoomService{

    @Autowired
    private RoomMapper mapper;

    @Override
    public IPage<Room> list(Page page, ExcludeEmptyQueryWrapper<Room> queryWrapper) {
        page.setOptimizeCountSql(false);
        IPage<Room> res = mapper.list(page, queryWrapper);
        return res;
    }

    @Override
    public List<Room> all() {
        return mapper.all();
    }

    @Override
    public List<Integer> listIds() {
        return mapper.selectId();
    }

    @Override
    public List<Room> getByIds(List roomIds) {
        return mapper.selectBatchIds(roomIds);
    }
}




