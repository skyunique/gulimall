package com.sky.gulimall.gulimallproduct.service.impl;

import com.sky.gulimall.gulimallproduct.service.CategoryBrandRelationService;
import com.sky.gulimall.gulimallproduct.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.common.utils.PageUtils;
import com.sky.common.utils.Query;

import com.sky.gulimall.gulimallproduct.dao.CategoryDao;
import com.sky.gulimall.gulimallproduct.entity.CategoryEntity;
import com.sky.gulimall.gulimallproduct.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        List<CategoryEntity> collect = categoryEntities.stream()
                .filter(item -> item.getParentCid() == 0)
                .map(item -> {
                    item.setChildren(getChildrens(item, categoryEntities));
                    return item;
                })
                .collect(Collectors.toList());
        return collect;
    }

    private List<CategoryEntity> getChildrens(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        List<CategoryEntity> collect = categoryEntities.stream()
                .filter(item -> item.getParentCid() == categoryEntity.getCatId())

                .map(item -> {
                    item.setChildren(getChildrens(item, categoryEntities));
                    return item;
                })
                //排序
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort())
                            - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查当前的菜单是否被别的地方所引用
        baseMapper.deleteBatchIds(asList);
    }


    //使用递归查找该路径
    @Override
    public Long[] findCatelogPathById(Long categorygId) {
        List<Long> path = new LinkedList<>();
        findPath(categorygId,path);
        Collections.reverse(path);
        Long[] objects = path.toArray(new Long[path.size()]);
        return objects;
    }

    private void findPath(Long categorygId, List<Long> path) {
        if(categorygId != 0){
            path.add(categorygId);
            CategoryEntity byId = getById(categorygId);
            findPath(byId.getParentCid(),path);
        }
    }

    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }


    @Override
    public List<CategoryEntity> getLevel1Catagories() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

        return categoryEntities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        //1、查出所有1级分类
        List<CategoryEntity> level1Catagories = getLevel1Catagories();

        //2、封装数据
        Map<String,List<Catelog2Vo>> parent_cid = level1Catagories.stream().collect(Collectors.toMap(k -> k.getCatId().toString()
                , v -> {
                    //1、每一个的一级分类，查到这个一级分类的二级分类
                    List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
                    //2、封装上面的结果
                    List<Catelog2Vo> catelog2Vos = null;
                    if (categoryEntities != null) {

                        catelog2Vos = categoryEntities.stream().map(l2 -> {
                            Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                            //1、找当前二级分类的三级分类封装成vo
                            List<CategoryEntity> level3Catelog = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", l2.getCatId()));
                            if (level3Catelog != null) {
                                List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
                                    //2.封装成指定格式
                                    Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                                    return catelog3Vo;
                                }).collect(Collectors.toList());
                                catelog2Vo.setCatalog3List(collect);
                            }

                            return catelog2Vo;
                        }).collect(Collectors.toList());
                    }

                    return catelog2Vos;

                }));
                return parent_cid;


/*        List<CategoryEntity> categoryEntities = this.list(new QueryWrapper<CategoryEntity>().eq("cat_level", 2));
        List<Catelog2Vo> catalog2Vos = categoryEntities.stream().map(categoryEntity -> {
            List<CategoryEntity> level3 = this.list(
                    new QueryWrapper<CategoryEntity>()
                            .eq("parent_cid", categoryEntity.getCatId()));
            List<Catelog2Vo.Catelog3Vo> catalog3Vos = level3.stream().map(cat -> {
                return new Catelog2Vo.Catelog3Vo(
                        cat.getParentCid().toString(), cat.getCatId().toString(), cat.getName());
            }).collect(Collectors.toList());

            Catelog2Vo catelog2Vo = new Catelog2Vo(categoryEntity.getParentCid().toString(),
                    Collections.singletonList(catalog3Vos),categoryEntity.getCatId().toString(),categoryEntity.getName());

            return catelog2Vo;
        }).collect(Collectors.toList());
        Map<String,List<Catelog2Vo>> catalogMap = new HashMap<>();
        for (Catelog2Vo catalog2Vo : catalog2Vos) {
            List<Catelog2Vo> list = catalogMap.getOrDefault(catalog2Vo.getCatalog1Id(), new LinkedList<>());
            list.add(catalog2Vo);
            catalogMap.put(catalog2Vo.getCatalog1Id(),list);
        }

        return catalogMap;*/
    }
}