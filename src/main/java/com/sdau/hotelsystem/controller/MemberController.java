package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sdau.hotelsystem.domain.Member;
import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.service.MemberService;
import com.sdau.hotelsystem.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Member")
public class MemberController {
    @Autowired
    private MemberService service;

    /**
     * 展示所有会员信息
     * @param param
     * @return
     */
    @PostMapping("list")
    public PageInfo list(SearchInfo param){
        IPage<Member> members = service.list(param);
        return new PageInfo(members.getTotal(),members.getRecords());
    }

    /**
     * 添加或编辑会员
     * @param member
     * @return
     */
    @PostMapping("save")
    public Response save(Member member){
        if(member.getId()==null){//添加
            member.setNum(NumUtils.getNum(10)); //生成10位卡号
            service.save(member);
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            service.updateById(member);
            return ResponseUtil.makeSuccess(null,"编辑成功！");
        }
    }

    /**
     * 逻辑删除，即离职禁止登陆
     * @param id
     * @return
     */
    @PostMapping("del")
    public Response delete(Integer id){
        service.update(new UpdateWrapper<Member>()
                .set("status",1)
                .eq("id",id));
        return ResponseUtil.makeSuccess();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("dels")
    public Response delete(@RequestBody List<Integer> ids){
        for(Integer id:ids){
            service.update(new UpdateWrapper<Member>()
                    .set("status",1)
                    .eq("id",id));
        }
        return ResponseUtil.makeSuccess();
    }

    /**
     * 判断会员是否存在
     * @param name
     * @return
     */
    @PostMapping("isExist")
    public Response isExist(String name){
        Member result = service.getOne(new QueryWrapper<Member>()
                                            .eq("name",name));
        if(result==null){
            return ResponseUtil.makeSuccess();
        }else{
            return ResponseUtil.makeFail();
        }
    }
}
