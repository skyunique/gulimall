package com.sky.gulimall.gulimallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.common.utils.PageUtils;
import com.sky.gulimall.gulimallproduct.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 14:19:37
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

