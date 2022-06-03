package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.RoomChange;
import com.sdau.hotelsystem.service.RoomChangeService;
import com.sdau.hotelsystem.mapper.RoomChangeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */

@Transactional
@Service
public class RoomChangeServiceImpl extends ServiceImpl<RoomChangeMapper, RoomChange>
    implements RoomChangeService{

}




