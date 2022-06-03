package com.sdau.hotelsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sdau.hotelsystem.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sdau.hotelsystem.util.SearchInfo;

/**
 *
 */
public interface MemberService extends IService<Member> {

    IPage<Member> list(SearchInfo param);
}
