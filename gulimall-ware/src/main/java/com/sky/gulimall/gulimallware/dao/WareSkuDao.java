package com.sky.gulimall.gulimallware.dao;

import com.sky.gulimall.gulimallware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品库存
 * 
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 16:56:20
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId,@Param("wareId") Long wareId,
                  @Param("skuNum") Integer skuNum);

    long getSkuStock(@Param("skuId") Long skuId);
}
