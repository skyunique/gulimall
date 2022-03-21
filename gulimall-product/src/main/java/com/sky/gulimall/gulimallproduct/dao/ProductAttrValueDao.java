package com.sky.gulimall.gulimallproduct.dao;

import com.sky.gulimall.gulimallproduct.entity.ProductAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu属性值
 * 
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 14:19:37
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {

    List<ProductAttrValueEntity> getAttrBySpuId(@Param("spuId") Long spuId);
}
