package com.sky.gulimall.gulimallproduct.dao;

import com.sky.gulimall.gulimallproduct.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 14:19:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
