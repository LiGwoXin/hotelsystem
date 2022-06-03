package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.MemberGrade;
import com.sdau.hotelsystem.service.MemberGradeService;
import com.sdau.hotelsystem.mapper.MemberGradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */

@Transactional
@Service
public class MemberGradeServiceImpl extends ServiceImpl<MemberGradeMapper, MemberGrade>
    implements MemberGradeService{

}




