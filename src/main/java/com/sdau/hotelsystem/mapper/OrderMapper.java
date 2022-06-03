package com.sdau.hotelsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sdau.hotelsystem.domain.Reservation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Entity com.sdau.hotelsystem.domain.Order
 */
public interface OrderMapper extends BaseMapper<Order> {
    String selectMemberByMemberId(@Param("memberId") Integer memberId);
    IPage<Order> list(Page<Order> page, @Param(Constants.WRAPPER) Wrapper<Order> ew);
    String selectMemberByCustomersName(@Param("customersName") String customersName);

    int insertSelective(Order order);

    List<Map> incomeday(Map params);
    List<Map> incomemonth(Map params);
    List<Map> incomeyear(Map params);

    int updateStatusById(@Param("status") Byte status, @Param("id") Integer id);
}




