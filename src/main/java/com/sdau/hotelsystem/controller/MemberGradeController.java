package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.MemberGrade;
import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.service.MemberGradeService;
import com.sdau.hotelsystem.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("MemberGrade")
public class MemberGradeController {

    @Autowired
    private MemberGradeService service;

    /**
     * 展示所有会员等级  分页
     * @param param
     * @return
     */
    @PostMapping("list")
    public PageInfo list(SearchInfo param){
        ExcludeEmptyQueryWrapper<MemberGrade> queryWrapper = new ExcludeEmptyQueryWrapper<>();
        queryWrapper.like("name",param.getSearch());
        Page page = new Page(param.getPage(), param.getLimit());
        IPage<MemberGrade> grades = service.page(page);
        return new PageInfo(grades.getTotal(),grades.getRecords());
    }

    /**
     * 展示所有会员等级
     * @param
     * @return
     */
    @PostMapping("all")
    public Response all(){

        List<MemberGrade> grades = service.list();
        return ResponseUtil.makeSuccess(grades);
    }

    /**
     * 添加或编辑会员等级
     * @param grade
     * @return
     */
    @PostMapping("save")
    public Response save(MemberGrade grade){
        if(grade.getId()==null){//添加
            service.save(grade);
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            service.updateById(grade);
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
     * 判断等级是否存在
     * @param name
     * @return
     */
    @PostMapping("isExist")
    public Response isExist(String name){
        MemberGrade result = service.getOne(new QueryWrapper<MemberGrade>().eq("name",name));
        if(result==null){
            return ResponseUtil.makeSuccess();
        }else{
            return ResponseUtil.makeFail();
        }
    }
}
