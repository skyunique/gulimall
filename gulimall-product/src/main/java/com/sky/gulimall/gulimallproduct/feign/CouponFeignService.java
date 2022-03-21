package com.sky.gulimall.gulimallproduct.feign;

import com.sky.common.to.SkuReductionTo;
import com.sky.common.to.SpuBoundTo;
import com.sky.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName CouponFeignService
 * @Desc
 * @Author sunqi
 * @Date 2021/9/26
 **/
@Component
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @PostMapping("/gulimallcoupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/gulimallcoupon/skufullreduction/saveinfo")
    R saveSkuRedution(@RequestBody SkuReductionTo skuReductionTo);
}
