package com.sky.gulimall.gulimallware.vo;

import lombok.Data;

/**
 * @ClassName PurchaseItemDoneVo
 * @Desc
 * @Author sunqi
 * @Date 2021/9/29
 **/
@Data
public class PurchaseItemDoneVo {

    private Long itemId;

    private Integer status;

    private String reason;
}
