package com.lb.common.cron;

import com.lb.common.utils.ThreadHelper;

/**
 * 任务调度管理
 * @author LB
 * @date 2017/7/29 20:31
 */
public class CronInit {
    /**
     * 关闭所有线程
     */
    public static void closeAll(){
        Thread[] list = ThreadHelper.getThreadAll();
        for (Thread thread : list) {
            String tmpName=thread.getName();
            if (tmpName.indexOf("=-=-=-=")>=0) {
                System.out.println("正在关闭线程："+tmpName);
                thread.interrupt();
            }
        }
    }
    /**
     * 添加任务调度计划
     * @param cronObj
     * @param delayed
     * @param period
     */
    public static void add(ICron cronObj, long delayed, long period){
        String threadName="=-=-=-="+cronObj.getClass().getName();
        System.out.println("正在启动线程："+threadName);
        Thread currentThread = new Thread(new CronHelper(cronObj,delayed,period,threadName),threadName);
        currentThread.start();
    }
}
