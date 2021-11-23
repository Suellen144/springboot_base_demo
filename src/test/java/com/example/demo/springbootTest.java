package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@PropertySource("classpath:application.yml")
public class springbootTest {

    @Resource
    DataSource dataSource;
    @Resource
    private UserMapper userMapper;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

    @Test
    public void druidTest () throws SQLException {
        //看一下默认数据源
        System.out.println(dataSource.getClass());
        //获取连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        //关闭连接
        connection.close();
    }

    @Test
    public void selectUserList(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void springbootVersion(){
        System.out.println(SpringBootVersion.getVersion());
    }

    @Test
    public void redisTest(){
        System.out.println(host+"===="+port);
    }
}
