package com.sky.gulimall.gulimallware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName PurchaseDoneVo
 * @Desc
 * @Author sunqi
 * @Date 2021/9/29
 **/
@Data
public class PurchaseDoneVo {

    @NotNull
    private Long id;

    private List<PurchaseItemDoneVo> items;

}
