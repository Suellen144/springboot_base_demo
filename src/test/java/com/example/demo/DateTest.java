package com.example.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTest;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserTestMapper;
import com.example.demo.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author zhk
 * @Date 2021/11/23 9:39
 * @Version 1.0
 **/
@SpringBootTest
@Slf4j
public class DateTest {

    @Resource
    UserMapper userMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    UserTestMapper userTestMapper;

    @Test
    public void huToolTest() {
        //date转成string （2021-11-30 10:36:08）
        String formatDateTime = DateUtil.formatDateTime(new Date());
        System.out.println(formatDateTime+"====="+new Date());
        //20211130
        System.out.println(DateUtil.format(DateUtil.date(), "yyyyMMdd"));
        //104043
        System.out.println(DateUtil.format(DateUtil.date(), "HHmmss"));

        //（2021-11-30 10:36:08）
        System.out.println(DateUtil.date());
        //（2021-11-30 10:36:08）
        System.out.println(DateTime.now().toString());

        //雪花算法生成id
        System.out.println(SnowFlakeUtils.getId());

        //32位uuid
        System.out.println(IdUtil.simpleUUID());
        //随机32位uuid
        System.out.println(RandomUtil.randomString(32));

        //-----------------------------------------

        //第一种：以AES算法
        String content = "zhkandand123@#";
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES,key);
        //加密
        byte[] encrypt = aes.encrypt(content);
        System.out.println("加密===="+Arrays.toString(encrypt));
        //解密
        byte[] decrypt = aes.decrypt(encrypt);
        System.out.println("解密===="+Arrays.toString(decrypt));

        //加密16进制表示
        String encryptHex = aes.encryptHex(content);
        System.out.println("AES加密16进制表示：" + encryptHex);   //46953def8ec02e21f7c9bb4405243a70
        //解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("AES解密为字符串：" + decryptStr);  //test中文
    }

    @Test
    public void arrayTest() {
        ArrayList<Integer> objects = new ArrayList<>(10000);
        objects.add(1);
        System.out.println(objects.size());
    }


    @Test
    public void selectMapsPage(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper./*select(User::getName).
                like(User::getName , "十").*/
                orderByDesc(User::getId);
        //isSearchCount：是否进行 count 查询，设置false后不会返回total
        //Page<Map<String , Object>> mapPage1 = new Page<>(1 , 10 , false);
        Page<User> mapPage = new Page<>(1 ,1000);
        if (mapPage.getSize() > 500) {
            String str = "limit %s offset %s";
            String lastLimit = String.format(str, mapPage.getSize(), (mapPage.getCurrent() - 1) * mapPage.getSize());
            userLambdaQueryWrapper.last(lastLimit);
            mapPage.setSize(-1);
        }
        IPage<User> mapIPage = userMapper.selectPage(mapPage , userLambdaQueryWrapper);
        System.out.println("总页数： "+mapIPage.getPages());
        System.out.println("总记录数： "+mapIPage.getTotal());
        System.out.println("size： "+mapIPage.getSize());
        System.out.println("Current： "+mapIPage.getCurrent());
        mapIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void resultTest() {
        String url = "http://localhost:9000/user/selectUserById?id={id}";
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",30);
        String forObject = restTemplate.getForObject(url,String.class,map);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        Object data = jsonObject.get("data");
        User user = JSON.parseObject(jsonObject.get("data").toString(), User.class);
        System.out.println(forObject);
        System.out.println("==="+user.toString());
    }

    @Test
    public void userTest2() {
        UserTest userTest = new UserTest();
        userTest.setName("测试");
        userTestMapper.insert(userTest);
    }

    @Test
    public void nullTest() {
        if(StringUtils.isBlank(null+"")){
            System.out.println(123);
        }
        if(StringUtils.equals("null",null+"")){
            System.out.println(456);
        }
        System.out.println(null+"");
        //时间戳
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        System.out.println(timestamp.toString());
    }
}
