package com.sky.gulimall.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sky.common.constant.ProductConstant;
import com.sky.gulimall.gulimallproduct.dao.AttrAttrgroupRelationDao;
import com.sky.gulimall.gulimallproduct.dao.AttrGroupDao;
import com.sky.gulimall.gulimallproduct.dao.CategoryDao;
import com.sky.gulimall.gulimallproduct.entity.AttrAttrgroupRelationEntity;
import com.sky.gulimall.gulimallproduct.entity.AttrGroupEntity;
import com.sky.gulimall.gulimallproduct.entity.CategoryEntity;
import com.sky.gulimall.gulimallproduct.service.CategoryService;
import com.sky.gulimall.gulimallproduct.vo.AttrGroupRelationVo;
import com.sky.gulimall.gulimallproduct.vo.AttrRespVo;
import com.sky.gulimall.gulimallproduct.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.common.utils.PageUtils;
import com.sky.common.utils.Query;

import com.sky.gulimall.gulimallproduct.dao.AttrDao;
import com.sky.gulimall.gulimallproduct.entity.AttrEntity;
import com.sky.gulimall.gulimallproduct.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
       // 1.保存基本数据
//        attrEntity = (AttrEntity) Converter.INSTANCE.s2t(attr);
        this.save(attrEntity);
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                && attr.getAttrGroupId() != null){
            // 2、保存关联关系
            // 如果分组id不为空，说明是规格参数而不是销售属性，则对属性-分组表进行更新
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }


    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> objectQueryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type)? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if(catelogId != 0){
            objectQueryWrapper.eq("catelog_id",catelogId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            objectQueryWrapper.and((wrapper) ->{
               wrapper.eq("attr_id",key)
               .or().like("attr_name",key);
            });
        }



        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                objectQueryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attr_id = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId() != null ) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(attr_id);
        return pageUtils;
    }


    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity entity = new AttrEntity();
        BeanUtils.copyProperties(attr,entity);
        this.baseMapper.updateById(entity);
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //只有当属性分组不为空时，说明更新的是规则参数，则需要更新关联表
            if (null != attr.getAttrGroupId()) {
                //查询属性-分组名对应关系
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
                attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
                attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
                Integer c = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
                //在关联表中已有该属性分组数据时进行更新，否则插入新数据
                if (c > 0) {
                    relationDao.update(attrAttrgroupRelationEntity,
                            new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
                } else {

                    relationDao.insert(attrAttrgroupRelationEntity);
                }
            }
        }

    }

    @Transactional
    @Override
    public AttrVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.baseMapper.selectById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,attrRespVo);

        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //查询并设置分组名
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            //如果分组id不为空，则查出分组名
            if (attrAttrgroupRelationEntity != null
                    && attrAttrgroupRelationEntity.getAttrGroupId() != null ){
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(new QueryWrapper<AttrGroupEntity>().eq("attr_group_id", attrAttrgroupRelationEntity.getAttrGroupId()));
                //设置分组名
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                attrRespVo.setAttrGroupId(attrGroupEntity.getAttrGroupId());

            }
        }

        //查询到分类信息
        CategoryEntity categoryEntity = categoryDao.selectOne(new QueryWrapper<CategoryEntity>().eq("cat_id", attrEntity.getCatelogId()));
        //设置 分类名
        attrRespVo.setCatelogName(categoryEntity.getName());
        //查询并设置分类路径
        Long[] catelogPathById = categoryService.findCatelogPathById(categoryEntity.getCatId());
        attrRespVo.setCatelogPath(catelogPathById);

        return attrRespVo;
    }

    /**
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> enties = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> collect = enties.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect)){
            return new ArrayList<AttrEntity>();
        }
        Collection<AttrEntity> attrEntities = this.listByIds(collect);
        if(CollectionUtils.isEmpty(attrEntities)){
           return new ArrayList<AttrEntity>();
        }
        return (List<AttrEntity>) attrEntities;
    }


    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
       // relationDao.delete(new QueryWrapper<>().eq("attr_id",1L).eq("attr_group_is"))
        List<AttrAttrgroupRelationEntity> collect = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        relationDao.deleteBatchRelation(collect);
    }


    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1.当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //2.当前分组只能关联别的分组没有引用的属性
        List<AttrGroupEntity> attrList = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id",catelogId));
        List<Long> collect = attrList.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        List<AttrAttrgroupRelationEntity> groupIds = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));

        List<Long> attrIds = groupIds.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //从当前分类的所有属性中移除这些属性
        QueryWrapper<AttrEntity> attrEntityQueryWrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(attrIds != null && attrIds.size()>0){

            attrEntityQueryWrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            attrEntityQueryWrapper.and((w) -> {
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), attrEntityQueryWrapper);
        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }


    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIds) {
//        List<AttrEntity> attrEntities = this.baseMapper.selectBatchIds(attrIds);
//        List<Long> collect = attrEntities.stream().map(attr -> {
//            return attr.getCatelogId();
//        }).collect(Collectors.toList());

//       List<Long> selectSearchAttrIds = this.baseMapper.selectSearchAttrIds(attrIds);


        return baseMapper.selectSearchAttrIds(attrIds);
    }
}