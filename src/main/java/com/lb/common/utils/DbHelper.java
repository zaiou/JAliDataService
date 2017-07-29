package com.lb.common.utils;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

/**
 * @author LB
 * @date 2017/7/29 20:02
 */
public class DbHelper {
    private static DbHandler order=null;
    private static DbHandler product=null;

    /**
     * 初始化
     * 2017年2月23日 下午1:28:19
     * @param DBOrder		交易
     * @param DBProduct		商品
     */
    public static void load(DbPro DBOrder, DbPro DBProduct){
        if (order==null) {
            order=new DbHandler(DBOrder);
        }
        if(DBProduct==null){
            order = new DbHandler(DBProduct);
        }
    }

    /**
     * 交易
     * 2017年2月23日 下午1:28:03
     * @return
     */
    public static DbPro getOrder(){
        return order.getDb();
    }

    /**
     * 商品
     * 2017年2月23日 下午1:27:30
     * @return
     */
    public static DbPro getProduct(){
        return product.getDb();
    }



    @SuppressWarnings("unchecked")
    private static Map<Integer,List<Record>> find(String sql, List<Object> params, String connConfigName){
        DbCallbackGetList db=new DbCallbackGetList(sql, params,connConfigName);
        return (Map<Integer,List<Record>>) DbHelper.getOrder().execute(db);
    }

    /**
     * 支持多结果集
     * @param sql
     * @param params
     * @return
     */
    public static Map<Integer,List<Record>> findPG(String sql,List<Object> params){
        return find(sql,params,"order");
    }

}
