package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.CardType;
import com.sdau.hotelsystem.service.CardTypeService;
import com.sdau.hotelsystem.mapper.CardTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
@Service
public class CardTypeServiceImpl extends ServiceImpl<CardTypeMapper, CardType>
    implements CardTypeService{

}




