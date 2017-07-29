package com.lb.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件操作，针对java标准配置处理
 * @author LB
 * @date 2017/7/29 19:32
 */
public class ConfigUtil {

    private Properties prop=new Properties();

    /**
     * 初始配置信息
     * @param path 配置文件路径
     */
    public ConfigUtil(String path) {
        try {
            prop.load(new InputStreamReader(new FileInputStream(path),"UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取配置值
     * @param key 配置键名称
     * @return 配置值
     */
    public String getValue(String key){
        return prop.getProperty(key);
    }

    private static ConfigUtil config;

    /**
     * 导入配置
     * @param config
     */
    public static void setConfig(ConfigUtil config) {
        ConfigUtil.config = config;
    }

    /**
     * 获取配置值
     * @param key 配置键名称
     * @return 配置值
     */
    public static String getVal(String key){
        return config.getValue(key);
    }

}
