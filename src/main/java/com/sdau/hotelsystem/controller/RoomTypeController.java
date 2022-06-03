package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Department;
import com.sdau.hotelsystem.domain.RoomType;
import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.service.RoomTypeService;
import com.sdau.hotelsystem.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService service;

    /**
     * index
     * @param param
     * @return
     */
    @PostMapping("list")
    public PageInfo list(SearchInfo param){
        ExcludeEmptyQueryWrapper<RoomType> queryWrapper = new ExcludeEmptyQueryWrapper<>();
        queryWrapper.like("name",param.getSearch());
        Page page = new Page(param.getPage(), param.getLimit());
        IPage<RoomType> roomTypeIPage = service.page(page,queryWrapper);
        return new PageInfo(roomTypeIPage.getTotal(),roomTypeIPage.getRecords());
    }

    /**
     * 展示所有
     * @return
     */
    @PostMapping("all")
    public Response getAll(){
        List<RoomType> list = service.list();
        return ResponseUtil.makeSuccess(list);
    }

    /**
     * 添加或编辑部门
     * @param roomType
     * @return
     */
    @PostMapping("save")
    public Response save(RoomType roomType){
        if(roomType.getId()==null){//添加
            service.save(roomType);
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            service.updateById(roomType);
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
     * @param name
     * @return
     */
    @PostMapping("isExist")
    public Response isExist(String name){
        RoomType result = service.getOne(new QueryWrapper<RoomType>().eq("name",name));
        if(result==null){
            return ResponseUtil.makeSuccess();
        }else{
            return ResponseUtil.makeFail();
        }
    }
}
