package com.lb.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json辅助
 * @author LB
 * @date 2017/7/29 20:25
 */
public class JsonHelper {
    /** json转object
     * @param jsonStr json字符串
     * @param cla 转成何类种
     * @return json转换失败返回Null
     */
    public static <T> T toObject(String jsonStr,Class<T> cla){
        ObjectMapper objectMapper=new ObjectMapper();

        try {
            return objectMapper.readValue(jsonStr, cla);
        } catch (IOException e) {
            LogHelper.error(e.getMessage(),e, JsonHelper.class);
        }

        return null;
    }

    /**
     * object转json，若嵌套有Record或Model，将会转化失败或不是想要的结束
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj){
        if (obj instanceof Model) {
            return Json.getJson().toJson(((Model<?>) obj).toRecord().getColumns());
        }else if (obj instanceof Record) {
            return Json.getJson().toJson(((Record) obj).getColumns());
        }else if(obj instanceof List){
            return listToJson((List<?>) obj);
        }else{
            return Json.getJson().toJson(obj);
        }
    }

    /**
     * json字符串转Model
     *
     * @param jsonStr
     *            json字符串
     * @param modelType
     *            转换的类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Model<?> toModel(String jsonStr, Class<? extends Model<?>> modelType) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> maps;
        try {
            maps = objectMapper.readValue(jsonStr, Map.class);
            return MapHelper.mapToModel(maps, modelType);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Model转Json(解决使用jackson无法转换问题)
     * @param model
     * @return
     */
    public static String modelToJson(Model<?> model){
        return Json.getJson().toJson(model.toRecord().getColumns());
    }

    /**
     * record转Json
     * @param record
     * @return
     */
    public static String recordToJson(Record record){
        return Json.getJson().toJson(record.getColumns());
    }

    /**
     * List<Model<?>>转json
     * @param listModel
     * @return
     */
    public static String modelListToJson(List<Model<?>> listModel){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for (Model<?> model : listModel) {
            list.add(model.toRecord().getColumns());
        }
        return Json.getJson().toJson(list);
    }

    /**
     * List<Record>转json
     * @param listModel
     * @return
     */
    public static String recordListToJson(List<Record> listModel){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for (Record record : listModel) {
            list.add(record.getColumns());
        }
        return Json.getJson().toJson(list);
    }

    /**
     * List<?>列表转json
     * @param listModel
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String listToJson(List<?> list){
        if (list==null || list.size()==0) {
            return "[]";
        }

        if (list.get(0) instanceof Record) {
            return recordListToJson((List<Record>) list);
        }else if (list.get(0) instanceof Model){
            return modelListToJson((List<Model<?>>) list);
        }else{
            return Json.getJson().toJson(list);
        }
    }

    /**
     * json转字符串一般方法
     * @param obj
     * @return
     */
    public static String generalToJson(Object obj){
        return Json.getJson().toJson(obj);
    }
}
