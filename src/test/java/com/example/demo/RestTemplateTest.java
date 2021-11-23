package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author zhk
 * @Date 2021/11/22 17:28
 * @Version 1.0
 **/

@SpringBootTest
public class RestTemplateTest {

    @Resource
    private RestTemplate restTemplate;

    //url(get请求  可拼接路径参数)
    @Test
    public  void  getForObject(){
        //中国城市天气Api
        String url = "http://api.k780.com/?app=weather.today&cityId=101280101&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println(forObject);
    }

    @Test
    public void restTemplateTransferFile(){
        final String filePath = "E:";
        final String fileName = "testFile.txt";
        final String url = "http://localhost:9000/file/upload";

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        //设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(filePath+"/"+fileName);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        form.add("filename",fileName);

        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        String s = restTemplate.postForObject(url, files, String.class);
        System.out.println(s);
    }
}
