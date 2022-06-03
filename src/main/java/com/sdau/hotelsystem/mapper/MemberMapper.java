package com.sdau.hotelsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sdau.hotelsystem.domain.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sdau.hotelsystem.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.sdau.hotelsystem.domain.Member
 */
public interface MemberMapper extends BaseMapper<Member> {


    IPage<Member> list(Page<Member> page, @Param(Constants.WRAPPER) Wrapper<Member> ew);
}




