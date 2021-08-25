package com.sky.gulimall.gulimallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.common.utils.PageUtils;
import com.sky.gulimall.gulimallware.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 16:56:21
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

