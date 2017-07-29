package com.lb.common.cron;

/**
 * 任务调度接口（业务对象需要继承此类）
 * @author LB
 * @date 2017/7/29 20:33
 */
public interface ICron {
    /**
     * 执行业务
     */
    public void run();
}
