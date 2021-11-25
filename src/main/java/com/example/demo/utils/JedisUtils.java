package com.example.demo.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhk
 * @Date 2021/11/21 22:06
 * @Version 1.0
 **/

@Component
public class JedisUtils {

    @Resource
    private JedisPool jedisPool;

    /**
     * 向Redis中存值，永久有效
     */
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception ignored) {
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 根据传入Key获取指定Value
     */
    public String get(String key) {
        Jedis jedis = null;
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            return "0";
        } finally {
            assert jedis != null;
            jedis.close();
        }
        return value;
    }

    /**
     * 校验Key值是否存在
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 删除指定Key-Value
     */
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            return 0L;
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 删除一个或多个key
     *
     * @param key 一个或多个key
     */
    public Long del(String... key) {
        Jedis jedis = null;
        Long delNum = 0L;
        try {
            jedis = jedisPool.getResource();
            delNum =jedis.del(key);
        }finally {
            assert jedis != null;
            jedis.close();
        }
        return delNum;
    }

    /**
     * 批量删除
     * @param keyList 要删除的key的集合
     */
    public void batchDel(List<String> keyList){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            keyList.forEach(pipeline::del);
            pipeline.sync();
        }finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 设置某个key的过期时间，单位秒
     *
     * @param key key
     * @param seconds 过期时间秒
     */
    public void expire(String key, Integer seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, seconds);
        }finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 查看某个key还有几秒过期，-1表示永不过期 ，-2表示已过期
     *
     * @param key key
     */
    public Long timeToLive(String key) {
        Jedis jedis = null;
        Long ttl;
        try {
            jedis = jedisPool.getResource();
            ttl = jedis.ttl(key);
        }finally {
            assert jedis != null;
            jedis.close();
        }
        return ttl;
    }

    /**
     * 查看某个key对应的value的类型
     *
     * @param key key
     */
    public String type(String key) {
        Jedis jedis = null;
        String type;
        try {
            jedis = jedisPool.getResource();
            type = jedis.type(key);
        }finally {
            assert jedis != null;
            jedis.close();
        }
        return type;
    }

    /**
     * 字符串后追加内容
     *
     * @param key key
     * @param appendContent 要追加的内容
     */
    public void append(String key, String appendContent) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.append(key, appendContent);
        }finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 返回key的value的长度
     *
     * @param key key
     */
    public Long strlen(String key) {
        Jedis jedis = null;
        Long strLen = 0L;
        try {
            jedis = jedisPool.getResource();
            strLen = jedis.strlen(key);
        }finally {
            assert jedis != null;
            jedis.close();
        }
        return strLen;
    }

    /**
     * value 加increment
     *
     * @param key key
     * @param increment 加几
     */
    public Long incrBy(String key, int increment) {
        Jedis jedis = null;
        Long incrByResult = 0L;
        try {
            jedis = jedisPool.getResource();
            incrByResult = jedis.incrBy(key, increment);
        }finally {
            assert jedis != null;
            jedis.close();
        }
        return incrByResult;
    }

    /**
     * value 减increment
     *
     * @param key key
     * @param increment 减几
     */
    public Long decrBy(String key, int increment) {
        Jedis jedis = null;
        Long decrByResult = 0L;
        try {
            jedis = jedisPool.getResource();
            decrByResult = jedis.decrBy(key, increment);
        }finally {
            assert jedis != null;
            jedis.close();
        }
        return decrByResult;
    }

    /**
     * 给某个key设置过期时间和value，成功返回OK
     *
     * @param key key
     * @param seconds 过期时间秒
     * @param value 设置的值
     * @return
     */
    public String setEx(String key, int seconds, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, seconds, value);
        }finally {
            assert jedis != null;
            jedis.close();
        }
        return result;
    }

    /**
     * 分布式锁
     * @param key key
     * @param value value
     * @param time 锁的超时时间，单位：秒
     *
     * @return 获取锁成功返回"OK"，失败返回null
     */
    public String getDistributedLock(String key,String value,Integer time){
        Jedis jedis = null;
        String ret = "";
        try {
            jedis = jedisPool.getResource();

            ret = jedis.set(key, value, new SetParams().nx().ex(time));
            return ret;
        } catch (Exception e) {
            return null;
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }
}
