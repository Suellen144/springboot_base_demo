package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: elasticsearchClient配置类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.config
 * @Author: Suellen
 * @CreateDate: 2021/12/29 10:31
 */
@Configuration
public class ElasticsearchClientConfig  {

    @Bean
    public RestClient restClient(){
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();
        return restClient;
    }

    /**
     * 注册 rest高级客户端
     */
    /*@Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1",9200,"http")
                )
        );
        return client;
    }*/
}
