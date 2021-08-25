package com.sky.gulimall.gulimallmember.dao;

import com.sky.gulimall.gulimallmember.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 16:50:50
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
