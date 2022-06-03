package com.sdau.hotelsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdau.hotelsystem.domain.Member;
import com.sdau.hotelsystem.service.MemberService;
import com.sdau.hotelsystem.mapper.MemberMapper;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import com.sdau.hotelsystem.util.SearchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public IPage<Member> list(SearchInfo param) {
        QueryWrapper<Member> wrapper = new ExcludeEmptyQueryWrapper<>();
        wrapper.like("member.name",param.getSearch());
//        wrapper.eq("user.status",0);
        Page<Member> page = new Page<>(param.getPage(), param.getLimit());
        // 对多表查询后的结果进行count(*)
        page.setOptimizeCountSql(false);
        IPage<Member> res = memberMapper.list(page, wrapper);
        return res;
    }
}




