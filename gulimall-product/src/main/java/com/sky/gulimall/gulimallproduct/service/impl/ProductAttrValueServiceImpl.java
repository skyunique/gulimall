package com.sky.gulimall.gulimallproduct.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.common.utils.PageUtils;
import com.sky.common.utils.Query;

import com.sky.gulimall.gulimallproduct.dao.ProductAttrValueDao;
import com.sky.gulimall.gulimallproduct.entity.ProductAttrValueEntity;
import com.sky.gulimall.gulimallproduct.service.ProductAttrValueService;
import org.springframework.util.CollectionUtils;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public void saveProductAttr(List<ProductAttrValueEntity> proAttrValues) {
        this.saveBatch(proAttrValues);
    }


    @Override
    public List<ProductAttrValueEntity> getAttrBySpuId(Long spuId) {
        List<ProductAttrValueEntity> attrBySpuId = productAttrValueDao.getAttrBySpuId(spuId);
        if(CollectionUtils.isEmpty(attrBySpuId)){
            return new ArrayList<ProductAttrValueEntity>();
        }
        return attrBySpuId;
    }


    @Override
    public void saveProductAttrBySpuId(List<ProductAttrValueEntity> attrs, Long spuId) {
        List<ProductAttrValueEntity> attrValueEntities = productAttrValueDao.getAttrBySpuId(spuId);
        attrValueEntities.forEach(item ->{
            Long attrId = item.getAttrId();
            attrs.forEach(attr -> {
                if(Objects.equals(attr.getAttrId(), attrId)){
                    item.setQuickShow(attr.getQuickShow());
                    item.setAttrName(attr.getAttrName());
                    item.setAttrValue(attr.getAttrValue());
                    item.setSpuId(spuId);
                    this.updateById(item);
                }
            });
        });
    }
}