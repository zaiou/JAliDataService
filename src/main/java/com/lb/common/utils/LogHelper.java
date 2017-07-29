package com.lb.common.utils;

import org.apache.log4j.Logger;

/**
 * 日志包装
 * @author LB
 * @date 2017/7/29 20:26
 */
public class LogHelper {
    /**
     * 记录日志并抛出异常
     * @param msg
     * @param clazz
     */
    public static void error(String msg, Class<?> clazz) {
        Logger log = Logger.getLogger(clazz);
        log.error(msg);
        try {
            throw new Exception("四喜-数据宝-日志：" + msg);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 记录日志并抛出异常
     * @param msg
     * @param e
     * @param clazz
     */
    public static void error(String msg,Exception e, Class<?> clazz) {
        Logger log = Logger.getLogger(clazz);
        log.error("四喜-数据宝-日志："+msg);
        e.printStackTrace();
    }

    /**
     * 调试日志
     * @param msg
     * @param clazz
     */
    public static void debug(String msg , Class<?> clazz){
        Logger log = Logger.getLogger(clazz);
        log.debug(msg);
    }
}
