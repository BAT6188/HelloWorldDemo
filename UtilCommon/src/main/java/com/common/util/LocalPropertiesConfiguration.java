package com.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by zhouhaiming on 2017-5-10 20:32
 * Email: dg_chow@163.com
 *
 * @Description: properties读取工具类
 */
public class LocalPropertiesConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(LocalPropertiesConfiguration.class);

    private static ConcurrentMap<String, String> configMap = new ConcurrentHashMap<>();
    private static Properties prop = new Properties();

    public LocalPropertiesConfiguration() {
    }

    public LocalPropertiesConfiguration(String propertiesFilePath) {
        addResource(propertiesFilePath);
    }

    public synchronized void addResource(String configPath) {
        try (InputStream is = new FileInputStream(configPath)) {
            prop.load(is);
        } catch (IOException e) {
            LOG.error("读取" + configPath + "路径下配置文件失败");
            throw new RuntimeException("读取" + configPath + "路径下配置文件失败");
        }
    }

    public String getStringValueByKey(String key) {
        key = key.trim();
        if (!configMap.containsKey(key)) {
            if (prop.getProperty(key) != null) {
                configMap.put(key, prop.getProperty(key));
            }
        }
        return configMap.get(key);
    }

    public Integer getIntegerValueByKey(String key) {
        key = key.trim();
        if (!configMap.containsKey(key)) {
            if (prop.getProperty(key) != null) {
                configMap.put(key, prop.getProperty(key));
            }
        }
        return Integer.valueOf(configMap.get(key));
    }

    public boolean getBooleanValueByKey(String key) {
        key = key.trim();
        if (!configMap.containsKey(key)) {
            if (prop.getProperty(key) != null) {
                configMap.put(key, prop.getProperty(key));
            }
        }
        return Boolean.valueOf(configMap.get(key));
    }
}
