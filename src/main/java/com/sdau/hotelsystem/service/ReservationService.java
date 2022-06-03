package com.sdau.hotelsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sdau.hotelsystem.domain.Member;
import com.sdau.hotelsystem.domain.Reservation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sdau.hotelsystem.util.SearchInfo;

/**
 *
 */
public interface ReservationService extends IService<Reservation> {

    IPage<Reservation> list(SearchInfo param);

    String getMember(Integer memberId);

    String getMemberNameByname(String customersName);
}
