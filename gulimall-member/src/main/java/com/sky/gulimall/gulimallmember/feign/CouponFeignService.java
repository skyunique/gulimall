package com.sky.gulimall.gulimallmember.feign;

import com.sky.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName CouponFeignService
 * @Desc 使用openfeign 远程调用别的微服务
 * @Author sunqi
 * @Date 2021/8/25
 **/
@FeignClient("gulimall-coupon")
@Service
public interface CouponFeignService {

    @RequestMapping("/gulimallcoupon/coupon/member/list")
    public R memberCoupons();
}
