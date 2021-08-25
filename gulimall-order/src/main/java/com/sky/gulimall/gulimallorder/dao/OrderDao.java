package com.sky.gulimall.gulimallorder.dao;

import com.sky.gulimall.gulimallorder.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 16:43:40
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
