package com.sdau.hotelsystem.service;

import com.sdau.hotelsystem.domain.RoomStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RoomStatusService extends IService<RoomStatus> {

    List getByIds(List<Integer> ids, Map param);
}
