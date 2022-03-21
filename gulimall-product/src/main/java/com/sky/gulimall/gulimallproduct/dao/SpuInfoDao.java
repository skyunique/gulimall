package com.sky.gulimall.gulimallproduct.dao;

import com.sky.gulimall.gulimallproduct.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 * 
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 14:19:36
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {


    void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
