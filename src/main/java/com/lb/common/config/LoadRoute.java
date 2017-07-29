package com.lb.common.config;

import com.jfinal.config.Routes;
import com.jfinal.ext.route.AutoBindRoutes;

/**
 * 设置控制器路由
 * @author LB
 * @date 2017/7/29 19:40
 */
public class LoadRoute {
    public static void load(Routes me){
        Routes a=new AutoBindRoutes();
        me.add(a);
    }
}
