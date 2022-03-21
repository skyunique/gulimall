package com.sky.gulimall.gulimallproduct.feign;

import com.sky.common.to.es.SkuEsModel;
import com.sky.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName SearchFeignService
 * @Desc
 * @Author sunqi
 * @Date 2021/10/28
 **/
@Component
@FeignClient("gulimall-search")
public interface SearchFeignService {

    @RequestMapping("/search/save/product")
    public R productStartsUp(@RequestBody List<SkuEsModel> skuEsModelList);
}
