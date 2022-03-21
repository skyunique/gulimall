package com.sky.common.to;

import lombok.Data;

/**
 * @ClassName SkuHasStockVo
 * @Desc
 * @Author sunqi
 * @Date 2021/10/28
 **/
@Data
public class SkuHasStockVo {

    private Long skuId;

    private Boolean hasStock;
}
