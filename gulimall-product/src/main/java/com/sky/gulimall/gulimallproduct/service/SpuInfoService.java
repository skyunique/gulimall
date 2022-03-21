package com.sky.gulimall.gulimallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.common.utils.PageUtils;
import com.sky.gulimall.gulimallproduct.entity.SpuInfoDescEntity;
import com.sky.gulimall.gulimallproduct.entity.SpuInfoEntity;
import com.sky.gulimall.gulimallproduct.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 14:19:36
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    /**
     * 上架接口
     * @param spuId
     */
    void spuUp(Long spuId);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

