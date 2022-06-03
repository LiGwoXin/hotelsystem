package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.RoomStatus;
import com.sdau.hotelsystem.service.RoomStatusService;
import com.sdau.hotelsystem.mapper.RoomStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class RoomStatusServiceImpl extends ServiceImpl<RoomStatusMapper, RoomStatus>
    implements RoomStatusService{

    @Autowired
    private RoomStatusMapper mapper;

    @Override
    public List<Integer> getByIds(List<Integer> ids, Map param) {
        List<Integer> res = new ArrayList<>();
        for(Integer id:ids){
            param.put("id",id);
            Integer count = mapper.isNull(param);
            if(count==0){
                res.add(id);
            }
        }
        return res;
    }
}




