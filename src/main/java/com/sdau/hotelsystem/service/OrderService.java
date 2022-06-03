package com.sdau.hotelsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sdau.hotelsystem.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sdau.hotelsystem.util.SearchInfo;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface OrderService extends IService<Order> {

    String getMember(Integer memberId);

    IPage<Order> list(SearchInfo param);

    String getMemberByname(String customersName);

    void insertSelective(Order order);

    List<Map> incomeday(Map params);

    List<Map> incomemonth(Map params);

    List<Map> incomeyear(Map params);

    void updateStatusById(Byte status, Integer id);
}
