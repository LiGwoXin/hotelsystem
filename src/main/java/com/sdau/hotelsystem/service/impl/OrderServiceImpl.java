package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.Order;
import com.sdau.hotelsystem.domain.Reservation;
import com.sdau.hotelsystem.service.OrderService;
import com.sdau.hotelsystem.mapper.OrderMapper;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import com.sdau.hotelsystem.util.SearchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Transactional
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Autowired
    private OrderMapper mapper;
    @Override
    public String getMember(Integer memberId) {
        return mapper.selectMemberByMemberId(memberId);

    }

    @Override
    public IPage<Order> list(SearchInfo param) {
        QueryWrapper<Order> wrapper = new ExcludeEmptyQueryWrapper<>();
        wrapper.like("order.customers_name",param.getSearch());
//        wrapper.eq("user.status",0);
        Page<Order> page = new Page<>(param.getPage(), param.getLimit());
        // 对多表查询后的结果进行count(*)
        page.setOptimizeCountSql(false);
        IPage<Order> res = mapper.list(page, wrapper);
        return res;
    }

    @Override
    public String getMemberByname(String customersName) {
        return mapper.selectMemberByCustomersName(customersName);
    }

    @Override
    public void insertSelective(Order order) {
        mapper.insertSelective(order);
    }

    public List<Map> incomeday(Map params){
        return mapper.incomeday(params);
    }
    public List<Map> incomemonth(Map params){
        return mapper.incomemonth(params);
    }

    @Override
    public List<Map> incomeyear(Map params) {
        return mapper.incomeyear(params);
    }

    @Override
    public void updateStatusById(Byte status, Integer id) {
        mapper.updateStatusById(status,id);
    }
}




