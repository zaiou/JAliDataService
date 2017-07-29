package com.lb.common.utils;

import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.ICallback;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.RecordBuilder;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取数据，支持多结果集
 * @author LB
 * @date 2017/7/29 20:14
 */
public class DbCallbackGetList implements ICallback {
    private String sql;
    private String connConfigName;
    private List<Object> params;
    private Map<Integer,List<Record>> resultList;

    /**
     *
     * @param sql
     * @param params sql参数
     * @param connConfigName 使用数据库配置名 mssql 或 pg 或 mysql
     */
    public DbCallbackGetList(String sql,List<Object> params,String connConfigName) {
        this.sql=sql;
        this.params=params;
        this.resultList=new HashMap<>();
        this.connConfigName=connConfigName;
    }

    @Override
    public Object call(Connection conn) throws SQLException {
        CallableStatement statement=conn.prepareCall(
                sql
                , ResultSet.TYPE_SCROLL_SENSITIVE
                ,ResultSet.CONCUR_READ_ONLY);
        if (params!=null) {
            for (int i = 0; i < this.params.size(); i++) {
                statement.setObject(i, params.get(i));
            }
        }
        boolean b=statement.execute();

        int i=0;
        while (b) {
            ResultSet resultSet=statement.getResultSet();
            List<Record> list= RecordBuilder.build(DbKit.getConfig(connConfigName), resultSet);
            resultList.put(i, list);
            b=statement.getMoreResults();
            i++;
        }
        return resultList;
    }
}
