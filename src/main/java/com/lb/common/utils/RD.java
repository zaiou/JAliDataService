package com.lb.common.utils;

import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据处理 ReturnData
 * @author LB
 * @date 2017/7/29 20:23
 */
public class RD {
    private int state;
    private String msg;
    private Object data;

    /**
     * 获取返回状态的实例
     * @param state
     * @param msg
     * @param data
     * @return
     */
    public static RD getInstance(int state,String msg,Object data){
        return new RD(state,msg,data);
    }

    /**
     * 返回错误处理
     * @param msg
     * @param data
     * @return
     */
    public static RD error(String msg,Object data){
        return new RD(0,msg,data);
    }

    /**
     * 返回错误处理(data 默认为 null)
     * @param msg
     * @param msg
     * @return
     */
    public static RD error(String msg){
        return new RD(0,msg,null);
    }

    /**
     * 成功处理
     * @param msg
     * @param data
     * @return
     */
    public static RD success(String msg,Object data){
        return new RD(1,msg,data);
    }

    /**
     * 成功处理(msg 默认为 "")
     * @param data
     * @param data
     * @return
     */
    public static RD success(Object data){
        return new RD(1,"",data);
    }

    /**
     * 成功处理(msg 默认为 ""，data默认为null)
     * @return
     */
    public static RD success(){
        return new RD(1,"",null);
    }

    /**
     * 快速或敏捷返回数据
     * @param b
     * @return
     */
    public static RD quick(boolean b){
        if (b) {
            return new RD(1,"",null);
        } else {
            return new RD(0,"操作失败",null);
        }
    }

    /**
     * 快速或敏捷返回数据
     * @param b
     * @param errorMsg 若异常时，返回的消息
     * @return
     */
    public static RD quick(boolean b,String errorMsg){
        if (b) {
            return new RD(1,"",null);
        } else {
            return new RD(0,errorMsg,null);
        }
    }

    /**
     *
     * @param b
     * @param successData 若成功时，返回的数据
     * @return
     */
    public static RD quick(boolean b,Object successData){
        if (b) {
            return new RD(1,"",successData);
        } else {
            return new RD(0,"操作失败",null);
        }
    }

    /**
     *
     * @param b
     * @param errorMsg 若异常时，返回的消息
     * @param successData 若成功时，返回的数据
     * @return
     */
    public static RD quick(boolean b,String errorMsg,Object successData){
        if (b) {
            return new RD(1,"",successData);
        } else {
            return new RD(0,errorMsg,null);
        }
    }

    /**
     * 状态 0：失败或错误 1：成功
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     * 消息内容
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 返回数据
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * 创建实例
     * @param state 0：失败 1：成功
     * @param msg
     * @param data
     */
    public RD(int state,String msg,Object data){
        this.state=state;
        this.msg=msg;
        this.data=data;
    }

    /**
     * 生成json时，一般使用此方法返回
     * @return
     */
    public Map<String, Object> getMap(){
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", state);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    /**
     * 推荐使用此方式在所有往客户端输出时，调用此方法
     * 注意：此方法不允许在非Controller里调用（如业务逻辑等层面不允许使用）
     * 转成json字符串后，向客户端输出json
     * @param controller 请转出当前控制器，如：this
     */
    public void renderJson(Controller controller){
        controller.renderJson(getMap());
    }
}
