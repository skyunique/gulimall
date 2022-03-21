package com.sky.gulimall.gulimallware.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName MergeVo
 * @Desc
 * @Author sunqi
 * @Date 2021/9/29
 **/
@Data
public class MergeVo {

    private Long purchaseId;  //整单id

    private List<Long> items;
}
