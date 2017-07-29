package com.lb.common.cron;

import com.lb.common.utils.Fn;

/**
 * 任务调度功能
 * @author LB
 * @date 2017/7/29 20:34
 */
public class CronHelper extends Thread  {
    private ICron ic;
    private long delayed;
    private long period;
    private String threadName;

    /**
     *
     * @param ic
     *            任务调度对象（业务对象）
     * @param delayed
     *            延时执行线程（毫秒）
     * @param period
     *            线程执行周期（毫秒）
     */
    public CronHelper(ICron ic, long delayed, long period, String threadName) {
        this.ic = ic;
        this.delayed = delayed;
        this.period = period;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("thread start(" + this.threadName + "):" + Fn.getNowStr());
        try {
            Thread.sleep(this.delayed);// 线程开始时的延时时间
        } catch (InterruptedException e) {
            System.out.println("error and stop thread(" + this.threadName + "):" + e.getMessage());
            return;
        }
        while (!isInterrupted()) {
            try {
                ic.run();// 线程开始执行
                System.out.println("thread(" + this.threadName + ")" + Fn.getNowStr() + ":success ");
            } catch (Exception e) {
                System.out.println("thread error(" + this.threadName + ")" + Fn.getNowStr() + ":" + e.getMessage());
            }

            try {
                Thread.sleep(this.period);
            } catch (InterruptedException e) {
                System.out.println(
                        "error and stop thread(" + this.threadName + ")" + Fn.getNowStr() + ":" + e.getMessage());
                return;
            }
        }
    }
}
