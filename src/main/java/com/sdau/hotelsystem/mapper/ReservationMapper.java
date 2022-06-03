package com.sdau.hotelsystem.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Member;
import com.sdau.hotelsystem.domain.Reservation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sdau.hotelsystem.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.sdau.hotelsystem.domain.Reservation
 */
public interface ReservationMapper extends BaseMapper<Reservation> {

    IPage<Reservation> list(Page<Reservation> page, @Param(Constants.WRAPPER) Wrapper<Reservation> ew);

    String selectMemberByMemberId(@Param("memberId") Integer memberId);

    String selectMemberNameByCustomersName(@Param("customersName") String customersName);

    Member selectMemberByCustomersName(@Param("customersName") String customersName);
}




