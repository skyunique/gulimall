package com.sky.gulimall.gulimallproduct.feign;

import com.sky.common.to.SkuHasStockVo;
import com.sky.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName WareFeignService
 * @Desc
 * @Author sunqi
 * @Date 2021/10/28
 **/
@Component
@FeignClient("gulimall-ware")
public interface WareFeignService {

    @PostMapping("/gulimallware/waresku/hasStock")
    public R getSkusHasStock(@RequestBody List<Long> skuIds);


}
