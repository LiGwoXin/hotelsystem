package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Department;
import com.sdau.hotelsystem.domain.MemberGrade;
import com.sdau.hotelsystem.domain.Role;
import com.sdau.hotelsystem.domain.RoomType;
import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.service.MemberGradeService;
import com.sdau.hotelsystem.service.RoleService;
import com.sdau.hotelsystem.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Role")
public class RoleController {

    @Autowired
    private RoleService service;

    /**
     * 分页展示所有角色
     * @param param
     * @return
     */
    @RequestMapping("list")
    public PageInfo list(SearchInfo param){
        ExcludeEmptyQueryWrapper<Role> queryWrapper = new ExcludeEmptyQueryWrapper<>();
        queryWrapper.like("name",param.getSearch());
        Page page = new Page(param.getPage(), param.getLimit());
        IPage<Role> roles = service.page(page,queryWrapper);
        return new PageInfo(roles.getTotal(),roles.getRecords());
    }

    /**
     * 展示所有
     * @return
     */
    @PostMapping("all")
    public Response getAll(){
        List<Role> list = service.list();
        return ResponseUtil.makeSuccess(list);
    }

    /**
     * 添加或编辑角色
     * @param role
     * @return
     */
    @RequestMapping("save")
    public Response save(Role role){
        if(role.getId()==null){//添加
            service.save(role);
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            service.updateById(role);
            return ResponseUtil.makeSuccess(null,"编辑成功！");
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("del")
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
    @RequestMapping("dels")
    public Response delete(@RequestBody List<Integer> ids){
        try{
            service.removeBatchByIds(ids);
            return ResponseUtil.makeSuccess();
        }catch (Exception e){ //存在约束
            return ResponseUtil.makeError(ErrorCode.DEL_ERROR);
        }
    }

    /**
     * 判断角色是否存在
     * @param name
     * @return
     */
    @RequestMapping("isExist")
    public Response isExist(String name){
        Role result = service.getOne(new QueryWrapper<Role>().eq("name",name));
        if(result==null){
            return ResponseUtil.makeSuccess();
        }else{
            return ResponseUtil.makeFail();
        }
    }
}
