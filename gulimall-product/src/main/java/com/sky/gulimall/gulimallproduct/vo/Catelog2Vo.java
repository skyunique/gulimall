package com.sky.gulimall.gulimallproduct.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName Catelog2Vo
 * @Desc
 * @Author sunqi
 * @Date 2021/11/8
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catelog2Vo {

    private String catalog1Id;

    private List<Catelog3Vo> catalog3List;

    private String id;

    private String name;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Catelog3Vo{ //3级分类VO

        private String catelog2Id;//父分类

        private String id;

        private String name;

    }
}
