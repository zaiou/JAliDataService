package com.lb.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.lb.common.utils.JsonHelper;
import com.lb.common.utils.RD;

/**
 * 全局动作拦截器
 * @author LB
 * @date 2017/7/29 20:22
 */
public class BaseActionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        try
        {
            inv.invoke();
        }
        catch (Exception e) {
            String msg = e.getMessage() + ",className:" + e.getClass().getName();
            RD rd = RD.getInstance(-1, msg, "url:" + inv.getController().getRequest().getRequestURI());
            System.out.println("已将此错误返回到前端，并且状态为-1:" + e.getMessage());
            System.out.println("前端内容：" + JsonHelper.generalToJson(rd.getMap()));
            System.out.println("类型：" + e.getClass().getName());
            System.out.println(e.getStackTrace().toString());
            rd.renderJson(inv.getController());
            e.printStackTrace();
        }
    }
}
