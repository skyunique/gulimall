package com.sky.gulimall.gulimallsearch.controller;

import com.sky.common.exception.BizCodeEnum;
import com.sky.common.to.es.SkuEsModel;
import com.sky.common.utils.R;
import com.sky.gulimall.gulimallsearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName ElasticSaveController
 * @Desc
 * @Author sunqi
 * @Date 2021/10/28
 **/
@Slf4j
@RestController
@RequestMapping("search/save")
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;

    @RequestMapping("/product")
   public R productStartsUp(@RequestBody List<SkuEsModel> skuEsModelList)
    {
        boolean b = false;
        try {
            b = productSaveService.productStarusUp(skuEsModelList);
        } catch (IOException e) {
            log.error("ElasticSaveController商品上架错误:{}",e);
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),
                    BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if(!b){
            return  R.ok();
        } else {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),
                    BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());

        }
    }

}
