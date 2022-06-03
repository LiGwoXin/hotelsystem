package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.Member;
import com.sdau.hotelsystem.domain.Reservation;
import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.service.ReservationService;
import com.sdau.hotelsystem.mapper.ReservationMapper;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import com.sdau.hotelsystem.util.SearchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */

@Transactional
@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation>
    implements ReservationService{

    @Autowired
    private ReservationMapper mapper;
    @Override
    public IPage<Reservation> list(SearchInfo param) {
        QueryWrapper<Reservation> wrapper = new ExcludeEmptyQueryWrapper<>();
        wrapper.like("reservation.customers_name",param.getSearch());
//        wrapper.eq("user.status",0);
        Page<Reservation> page = new Page<>(param.getPage(), param.getLimit());
        // 对多表查询后的结果进行count(*)
        page.setOptimizeCountSql(false);
        IPage<Reservation> res = mapper.list(page, wrapper);
        return res;
    }

    @Override
    public String getMember(Integer memberId) {
        return mapper.selectMemberByMemberId(memberId);
    }

    @Override
    public String getMemberNameByname(String customersName) {
        return mapper.selectMemberNameByCustomersName(customersName);
    }

}




