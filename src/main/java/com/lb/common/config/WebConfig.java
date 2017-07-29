package com.lb.common.config;

import com.jfinal.config.*;
import com.lb.common.cron.LoadCron;
import com.lb.common.utils.ConfigUtil;
import com.lb.common.utils.Fn;
import com.lb.interceptor.BaseActionInterceptor;
import com.lb.common.cron.CronInit;

/**
 * 系统初始配置
 * @author LB
 * @date 2017/7/29 19:26
 */
public class WebConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        System.out.println("初始化常量...");
        System.out.println("配置文件地址：" + Fn.getResource());
        ConfigUtil.setConfig(new ConfigUtil(Fn.getResource() + "/config.properties"));
        me.setDevMode("1".equals(ConfigUtil.getVal("jfinal.devmodel")));
    }

    @Override
    public void configRoute(Routes me) {
        System.out.println("初始化路由...");
        LoadRoute.load(me);
    }

    @Override
    public void configPlugin(Plugins me) {
        System.out.println("初始化数据配置");
        LoadDB.loadPlugin(me);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        LoadDB.loadDB();
        System.out.println("初始化拦截器...");
        me.add(new BaseActionInterceptor());
        System.out.println("初始化默认缓存...");
    }

    @Override
    public void configHandler(Handlers me) {
        System.out.println("初始化处理程序...");
        System.out.println("初始化线程...");
        CronInit.closeAll();
        LoadCron.load();
    }
}
