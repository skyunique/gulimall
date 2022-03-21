package com.sky.gulimall.gulimallware.feign;

import com.sky.common.utils.R;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ProductFeignService
 * @Desc
 * @Author sunqi
 * @Date 2021/9/29
 **/
@Component
@FeignClient("gulimall-product")
public interface ProductFeignService {

    @RequestMapping("/gulimallproduct/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);


}
