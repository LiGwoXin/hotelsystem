package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.RoomType;
import com.sdau.hotelsystem.service.RoomTypeService;
import com.sdau.hotelsystem.mapper.RoomTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType>
    implements RoomTypeService{

}




