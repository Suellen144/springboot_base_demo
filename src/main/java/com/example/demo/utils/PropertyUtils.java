package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: Property工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2022/1/5 14:20
 */
@Slf4j
public class PropertyUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        logger.info("start to load properties.......");
        props = new Properties();
        InputStream in = null;
        try {

            in = PropertyUtils.class.getClassLoader().
                    getResourceAsStream("application.yml");
            props.load(in);
            //logger.info(name);
        } catch (FileNotFoundException e) {
            logger.error("properties not found!");
        } catch (IOException e) {
            logger.error("IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties close Exception!");
            }
        }
        // logger.info(props);
        logger.info("load properties over...........");
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }
}
