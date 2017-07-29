package com.lb.common.utils;

/**
 * 线程相关操作
 * @author LB
 * @date 2017/7/29 20:32
 */
public class ThreadHelper {
    /**
     * 获取所有线程
     * @return
     */
    public static Thread[] getThreadAll(){
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;

        while ( group != null ) {
            topGroup = group;
            group = group.getParent();
        }

        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);

        return list;
    }
}
