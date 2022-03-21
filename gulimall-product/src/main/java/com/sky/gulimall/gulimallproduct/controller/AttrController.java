package com.sky.gulimall.gulimallproduct.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.sky.gulimall.gulimallproduct.entity.ProductAttrValueEntity;
import com.sky.gulimall.gulimallproduct.service.ProductAttrValueService;
import com.sky.gulimall.gulimallproduct.vo.AttrGroupRelationVo;
import com.sky.gulimall.gulimallproduct.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sky.gulimall.gulimallproduct.entity.AttrEntity;
import com.sky.gulimall.gulimallproduct.service.AttrService;
import com.sky.common.utils.PageUtils;
import com.sky.common.utils.R;



/**
 * 商品属性
 *
 * @author sky
 * @email sky@gmail.com
 * @date 2021-08-24 14:19:37
 */
@RestController
@RequestMapping("gulimallproduct/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    // /product/attr/base/listforspu/{spuId}
    @GetMapping("/base/listforspu/{spuId}")
    public R listSpuInfo(@PathVariable("spuId")Long spuId){
        List<ProductAttrValueEntity> productAttrValueEntities = productAttrValueService.getAttrBySpuId(spuId);
        return R.ok().put("data",productAttrValueEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("gulimallproduct:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }
    @RequestMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String,Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType") String type){
      PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("gulimallproduct:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		AttrVo attr = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("gulimallproduct:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    ///product/attr/update/{spuId}
    @RequestMapping("/update/{spuId}")
    public R update(@RequestBody List<ProductAttrValueEntity> attrs,@PathVariable("spuId")Long spuId){
        productAttrValueService.saveProductAttrBySpuId(attrs,spuId);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("gulimallproduct:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("gulimallproduct:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
