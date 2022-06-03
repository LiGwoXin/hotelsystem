package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sdau.hotelsystem.domain.*;
import com.sdau.hotelsystem.service.*;
import com.sdau.hotelsystem.util.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @Autowired
    private RoomStatusService roomStatusService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 展示所有预约信息
     * @param param
     * @return
     */
    @RequestMapping("list")
    public PageInfo list(SearchInfo param){
        IPage<Reservation> reservations = service.list(param);
        List<Reservation> res = reservations.getRecords();
        for (Reservation reservation : res){
            String member = service.getMember(reservation.getMemberId());
            if(member==null){
                member="普通用户";
            }
            reservation.setMember(member);
        }
        return new PageInfo(reservations.getTotal(),res);
    }


    /**
     * 添加预约信息
     * @param reservation
     * @return
     */
    @RequestMapping("save")
    public Response save(Reservation reservation){
        Member member = memberService.getOne(new QueryWrapper<Member>()
        .eq("name",reservation.getCustomersName()));
        if(member==null){
            reservation.setMemberId(0);
        }else{
            reservation.setMemberId(member.getId());
        }
        if(reservation.getId()==null){//添加
            service.save(reservation);
            Date intime=reservation.getCheckInTime();
            Date outtime = reservation.getCheckOutTime();
            while(intime.before(outtime)){
                RoomStatus roomStatus = new RoomStatus(reservation.getRoomId(), reservation.getId(), (byte) 0);
                roomStatus.setTime(intime);
                roomStatusService.save(roomStatus);
                intime = DateUtils.getNextDay(intime);
            }
            roomService.update(new UpdateWrapper<Room>() //预约
                    .set("status",1)
                    .eq("id",reservation.getRoomId()));
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            service.updateById(reservation);
            return ResponseUtil.makeSuccess(null,"编辑成功！");
        }
    }

    /**
     * 取消
     * @param id
     * @return
     */
    @RequestMapping("del")
    public Response delete(Integer id,Integer roomId){
        service.update(new UpdateWrapper<Reservation>()
                .set("status",2)
                .eq("id",id));
        roomStatusService.remove(new QueryWrapper<RoomStatus>()
                                    .eq("reservation_id",id));
        roomService.update(new UpdateWrapper<Room>() //
                .set("status",0)
                .eq("id",roomId));
        return ResponseUtil.makeSuccess();
    }

    @RequestMapping("checkIn")
    public Response checkIn(Integer id){
        service.update(new UpdateWrapper<Reservation>()
                .set("status",1)
                .eq("id",id));
        return ResponseUtil.makeSuccess();
    }

    /**
     * 获取空房间
     * @param
     * @return
     */
    @RequestMapping("isNull")
    public Response isExist(@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") @DateTimeFormat(pattern="yyyy-MM-dd")Date inTime, @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") @DateTimeFormat(pattern="yyyy-MM-dd")Date outTime){
        Map<String,Date> param = new HashMap<>();
        param.put("intime",inTime);
        param.put("outtime",outTime);
        List<Integer> ids = roomService.listIds(); //所有房间id
        List roomIds = roomStatusService.getByIds(ids, param); //所有空闲房间id
        if(roomIds.size()==0){
            return ResponseUtil.makeFail();
        }
        List<Room> rooms = roomService.getByIds(roomIds);
        return ResponseUtil.makeSuccess(rooms);
    }
}
