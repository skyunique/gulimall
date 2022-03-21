package com.sky.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName SkuReductionTo
 * @Desc
 * @Author sunqi
 * @Date 2021/9/26
 **/
@Data
public class SkuReductionTo {

    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;   //运算不会丢失相应的精度
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
