package com.sky.gulimall.gulimallsearch;

import com.alibaba.fastjson.JSON;
import com.sky.gulimall.gulimallsearch.config.GulimallElasticSearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
       // System.out.println(client);
    }

    /**
     *
     */
    @Test
    public void initTest(){
        System.out.println(client);
    }

    @Test
    public void indexData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");
        //indexRequest.source("userName","zhangsan");
        User user = new User();
        user.setUserName("zs");
        user.setAge(18);
        user.setGender("男");
        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);

//        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
//        xContentBuilder.startObject();
//        {
//            xContentBuilder.field("user","tom");
//            xContentBuilder.timeField("postDate",new Date());
//            xContentBuilder.field("message","try");
//        }
//        xContentBuilder.endObject();
        client.index(indexRequest,GulimallElasticSearchConfig.COMMON_OPTIONS);


    }


    /**
     *
     * @throws IOException
     */
    @Test
    public void searchData() throws IOException {
        //1.创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("customer");
        //指定DSL,检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构造检索条件
        searchSourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        System.out.println(searchSourceBuilder.toString());

        searchRequest.source(searchSourceBuilder);

        //执行检索
        SearchResponse search = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        //分析结果
        System.out.println(search.toString());

        //检验结果

    }

    @Data
    class User{
        private String userName;
        private String gender;
        private Integer age;
    }
}
