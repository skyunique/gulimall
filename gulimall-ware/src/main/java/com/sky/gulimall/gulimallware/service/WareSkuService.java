package com.sky.gulimall.gulimallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.common.utils.PageUtils;
import com.sky.gulimall.gulimallware.entity.WareSkuEntity;
import com.sky.gulimall.gulimallware.vo.SkuHasStockVo;

import java.util.List;
import java.util.Map;

/**
 * εεεΊε­
 *
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 16:56:20
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);
}

