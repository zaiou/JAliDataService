package com.lb.common.utils;

import com.jfinal.plugin.activerecord.DbPro;

/**
 * 多数据源
 * @author LB
 * @date 2017/7/29 20:06
 */
public class DbHandler {
    private DbPro db;

    public DbHandler(DbPro dbPro) {
        System.out.println("初始化 DbHandler");
        this.db=dbPro;
    }

    public DbPro getDb(){
        return this.db;
    }
}
