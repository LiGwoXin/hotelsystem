package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sdau.hotelsystem.domain.*;
import com.sdau.hotelsystem.service.*;
import com.sdau.hotelsystem.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Order")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RoomStatusService roomStatusService;

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomChangeService roomChangeService;

    /**
     * 展示所有信息
     * @param param
     * @return
     */
    @RequestMapping("list")
    public PageInfo list(SearchInfo param){
        IPage<Order> orders = service.list(param);
        List<Order> res = orders.getRecords();
        for (Order order : res){
            String member = service.getMember(order.getMemberId());
            if(member==null){
                member="普通用户";
            }
            order.setMember(member);
        }
        return new PageInfo(orders.getTotal(),res);
    }

    /**
     * 添加
     * @param order
     * @return
     */
    @RequestMapping("save")
    public Response save(Order order,Integer reservationId){
        Member member = memberService.getOne(new QueryWrapper<Member>()
                .eq("name",order.getCustomersName()));
        if(member==null){
            order.setMemberId(0);
        }else{
            order.setMemberId(member.getId());
        }
        if(reservationId!=null){
            reservationService.update(new UpdateWrapper<Reservation>()
                    .set("status",1)
                    .eq("id",reservationId));
            roomStatusService.remove(new QueryWrapper<RoomStatus>()
                    .eq("reservation_id",reservationId));
        }
        if(order.getId()==null){//添加
            order.setCreateTime(new Date());
            service.insertSelective(order);
            Date intime=order.getCheckInTime();
            Date outtime = order.getCheckOutTime();
            while(intime.before(outtime)){
                RoomStatus roomStatus = new RoomStatus();
                roomStatus.setRoomId(order.getRoomId());
                roomStatus.setOrderId(order.getId());
                roomStatus.setStatus((byte) 1);
                roomStatus.setTime(intime);
                roomStatusService.save(roomStatus);
                intime = DateUtils.getNextDay(intime);
            }
            roomService.update(new UpdateWrapper<Room>() //入住
                    .set("status",2)
                    .eq("id",order.getRoomId()));
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            service.updateById(order);
            return ResponseUtil.makeSuccess(null,"编辑成功！");
        }
    }

    /**
     * 退房
     * @param id
     * @return
     */
    @RequestMapping("del")
    public Response delete(Integer id,Integer roomId){

        service.updateStatusById((byte) 1,id);

        roomService.update(new UpdateWrapper<Room>() //未打扫卫生
                .set("status",2)
                .eq("id",roomId));
        return ResponseUtil.makeSuccess();
    }

    /**
     * 换房
     * @param id
     * @return
     */
    @RequestMapping("change")
    public Response change(Integer id,Integer roomId,Integer oldRoom,String remark){
        service.update(new UpdateWrapper<Order>()
                .set("room_id",roomId)
                .set("remark",remark)
                .eq("id",id));
        roomStatusService.update(new UpdateWrapper<RoomStatus>()
                .set("room_id",roomId)
                .eq("order_id",id));
        roomService.update(new UpdateWrapper<Room>() //新房间 入住
                .set("status",2)
                .eq("id",roomId));
        roomService.update(new UpdateWrapper<Room>() //旧房间 维修
                .set("status",3)
                .eq("id",oldRoom));
        //换房表
        RoomChange change = new RoomChange();
        change.setOrderId(id);
        Room alter = roomService.getById(roomId);
        Room original = roomService.getById(oldRoom);
        change.setAlterRoom(alter.getNum());
        change.setOriginalRoom(original.getNum());
        change.setRemark(remark);
        roomChangeService.save(change);
        return ResponseUtil.makeSuccess();
    }

    /**
     * 获取空房间
     * @param
     * @return
     */
    @RequestMapping("isNull")
    public Response isExist(@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") @DateTimeFormat(pattern="yyyy-MM-dd") Date inTime, @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") @DateTimeFormat(pattern="yyyy-MM-dd")Date outTime){
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

    @RequestMapping("incomeday")
    public List<Map> incomeday(@RequestParam Map params){
        return service.incomeday(params);
    }
    @RequestMapping("incomemonth")
    public List<Map> incomemonth(@RequestParam Map params){
        return service.incomemonth(params);
    }
    @RequestMapping("incomeyear")
    public List<Map> incomeyear(@RequestParam Map params){
        return service.incomeyear(params);
    }
}
