package com.lb.common.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Plugins;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.lb.common.utils.CacheHelper;
import com.lb.common.utils.ConfigUtil;
import com.lb.common.utils.DbHelper;

/**
 * 初始化数据配置
 * @author LB
 * @date 2017/7/29 19:42
 */
public class LoadDB {
    //交易数据库
    private static DruidPlugin dataSourceOrder;
    //商品数据库
    private static DruidPlugin dataSourceProduct;

    public static void loadPlugin(Plugins me){
        System.out.println("初始化 EhCache...");
        new EhCachePlugin().start();

        // ------------------------交易数据库----------------------------
        dataSourceOrder = new DruidPlugin(ConfigUtil.getVal("jdbc_order.url"), ConfigUtil.getVal("jdbc_order.username"),
                ConfigUtil.getVal("jdbc_order.password"), ConfigUtil.getVal("jdbc_order.driver"));
        //启用druidsql监控
        dataSourceOrder.addFilter(new StatFilter());
        dataSourceOrder.addFilter(new WallFilter());
        me.add(dataSourceOrder);
        //dataSourcePG.start();
        ActiveRecordPlugin arpOrder = new ActiveRecordPlugin("order", dataSourceOrder);
        arpOrder.setShowSql("1".equals(ConfigUtil.getVal("jdbc_order.showsql")));
        arpOrder.setDialect(new MysqlDialect() );
        LoadMapping.loadOrder(arpOrder);


        // -------------------------商品数据库----------------------------
        dataSourceProduct = new DruidPlugin(ConfigUtil.getVal("jdbc_product.url"), ConfigUtil.getVal("jdbc_product.username"),
                ConfigUtil.getVal("jdbc_product.password"), ConfigUtil.getVal("jdbc_product.driver"));
        //启用druidsql监控
        dataSourceProduct.addFilter(new StatFilter());
        dataSourceProduct.addFilter(new WallFilter());
        me.add(dataSourceProduct);
        // pg设置数据操作
        ActiveRecordPlugin arpProduct = new ActiveRecordPlugin("product", dataSourceProduct);
        arpProduct.setShowSql("1".equals(ConfigUtil.getVal("jdbc_product.showsql")));
        arpProduct.setDialect(new MysqlDialect());
        LoadMapping.loadProduct(arpProduct);

        me.add(arpOrder);
        me.add(arpProduct);

        //缓存
        CacheHelper.addCache("712c672b38080b1b1637");
    }

    /**
     * 多数据源
     */
    public static void loadDB(){
        System.out.println("初始化数据库...");
        DbHelper.load(new DbPro("order"),new DbPro("product"));
    }
}
