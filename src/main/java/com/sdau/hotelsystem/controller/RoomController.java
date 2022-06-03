package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Room;
import com.sdau.hotelsystem.domain.RoomStatus;
import com.sdau.hotelsystem.domain.RoomType;
import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.service.RoomService;
import com.sdau.hotelsystem.service.RoomStatusService;
import com.sdau.hotelsystem.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("Room")
public class RoomController {
    @Autowired
    private RoomService service;
    @Autowired
    private RoomStatusService roomStatusService;

    /**
     * 展示所有 分页
     * @param param
     * @return
     */
    @PostMapping("list")
    public PageInfo list(SearchInfo param){
        ExcludeEmptyQueryWrapper<Room> queryWrapper = new ExcludeEmptyQueryWrapper<>();
        queryWrapper.like("num",param.getSearch());
        Page page = new Page(param.getPage(), param.getLimit());
        IPage<Room> roomIPage = service.list(page,queryWrapper);
        return new PageInfo(roomIPage.getTotal(),roomIPage.getRecords());
    }
    /**
     * 展示所有
     * @param
     * @return
     */
    @PostMapping("all")
    public Response all(){
        List<Room> rooms = service.all();
        return ResponseUtil.makeSuccess(rooms);
    }

    /**
     * 添加或编辑
     * @param room
     * @return
     */
    @PostMapping("save")
    public Response save(Room room){
        if(room.getId()==null){//添加
            service.save(room);
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            service.updateById(room);
            return ResponseUtil.makeSuccess(null,"编辑成功！");
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("del")
    public Response delete(Integer id){
        try{
            service.removeById(id);
            return ResponseUtil.makeSuccess();
        }catch (Exception e){ //存在约束
            return ResponseUtil.makeError(ErrorCode.DEL_ERROR);
        }
    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("dels")
    public Response delete(@RequestBody List<Integer> ids){
        try{
            service.removeBatchByIds(ids);
            return ResponseUtil.makeSuccess();
        }catch (Exception e){ //存在约束
            return ResponseUtil.makeError(ErrorCode.DEL_ERROR);
        }
    }

    /**
     * 判断部门是否存在
     * @param num
     * @return
     */
    @PostMapping("isExist")
    public Response isExist(String num){
        Room result = service.getOne(new QueryWrapper<Room>().eq("num",num));
        if(result==null){
            return ResponseUtil.makeSuccess();
        }else{
            return ResponseUtil.makeFail();
        }
    }
    /**
     * @param
     * @return
     */
    @PostMapping("info")
    public Response info(Integer roomId){
        List<RoomStatus> result = roomStatusService.list(new QueryWrapper<RoomStatus>().eq("room_id",roomId));
        if(result!=null){
            return ResponseUtil.makeSuccess(result);
        }else{
            return ResponseUtil.makeFail();
        }
    }
}
