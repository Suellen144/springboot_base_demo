package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @Description: Elasticsearch测试
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo
 * @Author: Suellen
 * @CreateDate: 2021/12/29 10:38
 */
@SpringBootTest
@Slf4j
public class ElasticsearchTest {

/*    @Resource
    RestHighLevelClient restHighLevelClient;*/

    @Test
    public void createIndexTest() throws IOException {
        log.info("123 {}","456");
        /*CreateIndexRequest request = new CreateIndexRequest("Suellen");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());// 查看是否创建成功
        System.out.println(response);// 查看返回对象
        client.close();*/
        System.out.println("777");
    }
}
