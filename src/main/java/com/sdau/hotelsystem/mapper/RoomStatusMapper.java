package com.sdau.hotelsystem.mapper;

import com.sdau.hotelsystem.domain.RoomStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * @Entity com.sdau.hotelsystem.domain.RoomStatus
 */
public interface RoomStatusMapper extends BaseMapper<RoomStatus> {

    Integer isNull(Map param);
}




