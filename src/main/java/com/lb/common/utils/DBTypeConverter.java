package com.lb.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 数据库类型转化
 * @author LB
 * @date 2017/7/29 20:28
 */
public class DBTypeConverter {
    private static final int timeStampLen = "2016-03-28 16:18:18".length();
    private static final String timeStampPattern = "yyyy-MM-dd HH:mm:ss";
    private static final String datePattern = "yyyy-MM-dd";

    /**
     * 类型转化
     * @param cla 需要转化的类型
     * @param val 需要转化的值
     * @return
     * @throws ParseException
     */
    public static final Object convert(final Class<?> cla, String val) throws ParseException {
        // mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
        if (cla == String.class) {
            return val; // 用户在表单域中没有输入内容时将提交过来 "", 因为没有输入,所以要转成 null.
        }
       /* s = s.trim();
        if ("".equals(s)) { // 前面的 String跳过以后,所有的空字符串全都转成 null, 这是合理的
            return null;
        }*/
        // 以上两种情况无需转换,直接返回, 注意, 本方法不接受null为 s 参数(经测试永远不可能传来null, 因为无输入传来的也是"")

        Object result = null;
        // mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
        if (cla == Integer.class || cla == int.class) {
            result = Integer.parseInt(val);
        }
        // mysql type: bigint
        else if (cla == Long.class || cla == long.class) {
            result = Long.parseLong(val);
        }
        // 经测试java.util.Data类型不会返回, java.sql.Date, java.sql.Time,java.sql.Timestamp 全部直接继承自 java.util.Data, 所以 getDate可以返回这三类数据
        else if (cla == java.util.Date.class) {
            if (val.length() >= timeStampLen) { // if (x < timeStampLen) 改用 datePattern 转换，更智能
                // Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]
                // result = new java.util.Date(java.sql.Timestamp.valueOf(s).getTime()); // error under jdk 64bit(maybe)
                result = new SimpleDateFormat(timeStampPattern).parse(val);
            } else {
                // result = new java.util.Date(java.sql.Date.valueOf(s).getTime()); // error under jdk 64bit
                result = new SimpleDateFormat(datePattern).parse(val);
            }
        }
        // mysql type: date, year
        else if (cla == java.sql.Date.class) {
            if (val.length() >= timeStampLen) { // if (x < timeStampLen) 改用 datePattern 转换，更智能

                // result = new java.sql.Date(java.sql.Timestamp.valueOf(s).getTime()); // error under jdk 64bit(maybe)
                result = new java.sql.Date(new SimpleDateFormat(timeStampPattern).parse(val).getTime());
            } else {
                // result = new java.sql.Date(java.sql.Date.valueOf(s).getTime()); // error under jdk 64bit
                result = new java.sql.Date(new SimpleDateFormat(datePattern).parse(val).getTime());
            }
        }
        // mysql type: time
        else if (cla == java.sql.Time.class) {
            result = java.sql.Time.valueOf(val);
        }
        // mysql type: timestamp, datetime
        else if (cla == java.sql.Timestamp.class) {
            result = java.sql.Timestamp.valueOf(val);
        }
        // mysql type: real, double
        else if (cla == Double.class) {
            result = Double.parseDouble(val);
        }
        // mysql type: float
        else if (cla == Float.class) {
            result = Float.parseFloat(val);
        }
        // mysql type: bit, tinyint(1)
        else if (cla == Boolean.class) {
            result = Boolean.parseBoolean(val) || "1".equals(val);
        }
        // mysql type: decimal, numeric
        else if (cla == java.math.BigDecimal.class) {
            result = new java.math.BigDecimal(val);
        }
        // mysql type: unsigned bigint
        else if (cla == java.math.BigInteger.class) {
            result = new java.math.BigInteger(val);
        }
        // mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob. I have not finished the test.
        else if (cla == byte[].class) {
            result = val.getBytes();
        } else {
            throw new RuntimeException(cla.getName() + " can not be converted, please use other type of attributes in your model! from sixi.");
        }
        return result;
    }
}
