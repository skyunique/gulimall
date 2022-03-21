package com.sky.gulimall.gulimallsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName GulimallElasticSearchConfig
 * @Desc
 * @Author sunqi
 * @Date 2021/10/9
 **/
@Configuration
public class GulimallElasticSearchConfig {


    public static final RequestOptions COMMON_OPTIONS = RequestOptions.DEFAULT;

    @Value("${elasticsearch.ip}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient esRestClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, scheme)));

        return client;
    }

}
