package com.sky.gulimall.gulimallsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.sky.common.to.es.SkuEsModel;
import com.sky.gulimall.gulimallsearch.constant.EsConstant;
import com.sky.gulimall.gulimallsearch.config.GulimallElasticSearchConfig;
import com.sky.gulimall.gulimallsearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ProductSaveServiceImpl
 * @Desc
 * @Author sunqi
 * @Date 2021/10/29
 **/
@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean productStarusUp(List<SkuEsModel> skuEsModels) throws IOException {
        //保存到es
        //1.给es中建立索引，product，建立好映射关系

        //2、给es中保存这些数据
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String s = JSON.toJSONString(skuEsModel);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, 
                GulimallElasticSearchConfig.COMMON_OPTIONS);
        //TODO 如果批量错误
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.error("商品上架完成:{}，返回数据{}",collect,bulk.toString());

        return b;
    }
}
