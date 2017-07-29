package com.lb.common.utils;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模型相关功能
 * @author LB
 * @date 2017/7/29 20:27
 */
public class MapHelper {
    /** Map<String, Object>字符串转Model
     * @param maps json字符串
     * @param modelType 转换的类型
     * @return
     */
    public static Model<?> mapToModel(Map<String, Object> maps, Class<? extends Model<?>> modelType) {
        try {
            Model<?> m = modelType.newInstance();
            final Table tableInfo = TableMapping.me().getTable(modelType);
            try {
                // 循环json第一层
                for (Map.Entry<String, Object> entryJson : maps.entrySet()) {
                    //System.out.println(entryJson.getKey() + " = " + entryJson.getValue());
                    String jsonKey = entryJson.getKey();
                    String jsonVal = entryJson.getValue().toString();

                    // 循环表字段
                    if (tableInfo.hasColumnLabel(jsonKey)) {
                        if (jsonVal == null) {
                            m.set(jsonKey, null);
                        } else {
                            final Class<?> valType = tableInfo.getColumnType(jsonKey);
                            m.set(jsonKey, DBTypeConverter.convert(valType, jsonVal));
                        }
                    }
                }
                return m;

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Model转Map
     * @param model
     * @return
     */
    public static Map<String,Object> modelToMap(Model<?> model){
        return model.toRecord().getColumns();
    }

    /**
     * Record转Map
     * @param record
     * @return
     */
    public static Map<String,Object> recordToMap(Record record){
        return record.getColumns();
    }

    /**
     * List<Model<?>>转List<Map<String,Object>>
     * @param listModel
     * @return
     */
    public static List<Map<String,Object>> modelListToMapList(List<Model<?>> listModel){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for (Model<?> model : listModel) {
            list.add(model.toRecord().getColumns());
        }
        return list;
    }

    /**
     * map键值对转换为模型类
     * @param mapList
     * @param modelType
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Model> mapListToModelList(List<Map<String,Object>> mapList, Class<? extends Model<?>> modelType){
        List<Model> list=new ArrayList<Model>();
        for (Map<String,Object> model : mapList) {
            list.add(mapToModel(model,modelType));
        }
        return list;
    }

    /**
     * List<Record>转List<Map<String,Object>>
     * @param listModel
     * @return
     */
    public static List<Map<String,Object>> recordListMapList(List<Record> listModel){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for (Record record : listModel) {
            list.add(record.getColumns());
        }
        return list;
    }
}
