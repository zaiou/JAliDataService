package com.lb.common.utils;

import com.jfinal.plugin.ehcache.CacheKit;
import net.sf.ehcache.Element;

import java.util.function.Consumer;

/**
 * 缓存处理
 * @author LB
 * @date 2017/7/29 19:59
 */
public class CacheHelper {
    private static final String cacheName = "cache_sjb";

    /**
     * 注意：此方法只在初始化程序调用，其它情况不允许调用此方法
     */
    public static void addCache(String guid) {
        if ("712c672b38080b1b1632".equals(guid)) {
            CacheKit.getCacheManager().addCacheIfAbsent(cacheName);
        }
    }

    /**
     * 将数据添加到缓存
     *
     * @param key
     * @param o
     * @param cacheSecond
     *            缓存时间/秒
     */
    public static void add(String key, Object o, int cacheSecond) {
        Element e = new Element(key, o, false, cacheSecond, cacheSecond);
        CacheKit.getCacheManager().getCache(cacheName).put(e);
    }

    /**
     * 获取缓存数据（不支持并发）
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return CacheKit.get(cacheName, key);
    }

    /**
     * 获取缓存数据，当缓存数据为null时，自动添加数据到缓存（支持并发，多线程），获取缓存数据时，推荐使用此方式
     *
     * @param cacName
     * @param objLock
     *            缓存锁，请使用static做为缓存锁，如：static Object objLock = new Object()
     * @param action
     *            可以是匿名方法 如：x -> x.data=从数据库获取的数据
     * @param cacheSecond
     *            缓存时间/秒
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMultithread(String cacName, Object objLock, Consumer<? super CacheAssist> action,
                                       int cacheSecond) {
        Object data = CacheHelper.get(cacName);
        CacheAssist ca = new CacheAssist();
        if (data == null) {
            synchronized (objLock) {// 防止同一时刻（即并发时）进入DB查询
                data = CacheHelper.get(cacName);
                if (data == null) {// 之前sync等待需再次判断，以防再次去读取数据库
                    action.accept(ca);
                    data = ca.data;
                    CacheHelper.add(cacName, data, cacheSecond);
                }
            }
        }
        return (T) data;
    }
}
