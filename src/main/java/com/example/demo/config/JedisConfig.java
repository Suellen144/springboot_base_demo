package com.example.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author zhk
 * @Date 2021/11/21 22:22
 * @Version 1.0
 **/

@Configuration
@PropertySource("classpath:application.yml")
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active:8}")
    private Integer maxActive;
    @Value("${spring.redis.jedis.pool.max-idle:8}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait:1000}")
    private Long maxWait;
    @Value("${spring.redis.jedis.pool.min-idle:0}")
    private Integer minIdle;
    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        //连接池最大空闲连接数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //最大等待时间(1000即一秒)
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        //连接池最大连接数
        jedisPoolConfig.setMaxTotal(maxActive);
        //连接池确保最小空闲连接数
        jedisPoolConfig.setMinIdle(minIdle);
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(false);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(false);
        return new JedisPool(jedisPoolConfig,host, port, timeout);
    }
}
