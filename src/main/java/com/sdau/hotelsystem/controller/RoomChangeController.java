package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Department;
import com.sdau.hotelsystem.domain.RoomChange;
import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.service.DepartmentService;
import com.sdau.hotelsystem.service.RoomChangeService;
import com.sdau.hotelsystem.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Change")
public class RoomChangeController {
    @Autowired
    private RoomChangeService service;

    /**
     * 展示所有
     * @param param
     * @return
     */
    @PostMapping("list")
    public PageInfo list(SearchInfo param){
        ExcludeEmptyQueryWrapper<RoomChange> queryWrapper = new ExcludeEmptyQueryWrapper<>();
        queryWrapper.like("change_user",param.getSearch());
        Page page = new Page(param.getPage(), param.getLimit());
        IPage<RoomChange> iPage = service.page(page,queryWrapper);
        return new PageInfo(iPage.getTotal(),iPage.getRecords());
    }
}
