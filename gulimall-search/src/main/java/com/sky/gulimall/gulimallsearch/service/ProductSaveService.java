package com.sky.gulimall.gulimallsearch.service;

import com.sky.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName ProductSaveService
 * @Desc
 * @Author sunqi
 * @Date 2021/10/29
 **/
public interface ProductSaveService {

    public Boolean productStarusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
