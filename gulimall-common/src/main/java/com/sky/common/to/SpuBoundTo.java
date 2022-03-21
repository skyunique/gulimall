package com.sky.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName SpuBoundTo
 * @Desc
 * @Author sunqi
 * @Date 2021/9/26
 **/
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
