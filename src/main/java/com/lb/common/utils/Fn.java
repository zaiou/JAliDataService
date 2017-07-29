package com.lb.common.utils;

import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LB 帮助类
 * @date 2017/7/29 19:28
 */
public class Fn {
    private final static Pattern intPaddern = Pattern.compile("^[+-]?[0-9]+$");

    /**
     * 获取时间戳  201701010000000+0800
     * 2017年3月24日 下午3:55:42
     * @param dateStr  2017-03-01
     * @return
     */
    public static String getDateZ(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmsssZ", Locale.US);
        return format.format(toDate(dateStr, "yyyy-MM-dd", null));
    }

    /**
     * 时间戳转换时间字符串   2017-03-01
     * 2017年6月8日 下午6:23:33
     * @param dateStr  201701010000000+0800
     * @return
     */
    public static String getDateD(String dateStr){
        Date date = new Date();
        try {
            date = DateUtils.parseDate(Fn.getDateZ("2017-04-05"), Locale.US,"yyyyMMddHHmmsssZ");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toDateStr(date);
    }

    /**
     * 获取当前时间字符串格式 yyyy-MM-dd HH:mm
     *
     * @return
     */
    public static String getNowStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date());
    }

    /**
     * 获取当前时间字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 转日期时间字符串 yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String toDateTimeStr(Date date) {
        return toDateTimeStr(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 转日期字符串 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String toDateStr(Date date) {
        return toDateTimeStr(date, "yyyy-MM-dd");
    }

    /**
     * 截取时间格式字符串中的年份
     * 2017年4月28日上午9:35:18
     */
    public static String toYear(String dateStr){
        if(Fn.isDateFormat(dateStr, "yyyy-MM-dd")){
            String yearStr=dateStr.substring(0,4);
            return yearStr;
        }else{
            Calendar calendar=Calendar.getInstance();
            return toString(calendar.get(Calendar.YEAR));
        }

    }

    /**
     * Date转字符串 yyyy-MM
     *
     * @param date
     * @return
     */
    public static String toDateStrYM(Date date) {
        return toDateTimeStr(date, "yyyy-MM");
    }

    /**
     * 转时间字符串 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toTimeStr(Date date) {
        return toDateTimeStr(date, "HH:mm:ss");
    }

    /**
     * 转时间字符串格式 （如 年-月-日 时:分:秒 毫秒 格式为 yyyy-MM-dd HH:mm:ss SSS）
     *
     * @param date
     * @param dateFormat
     *            指定格式
     * @return 转换失败反回空字符串
     */
    public static String toDateTimeStr(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);
        try {
            return format.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 转时间字符串格式 （如 年-月-日 时:分:秒 毫秒 格式为 yyyy-MM-dd HH:mm:ss SSS）
     *
     * @param millis
     *            毫秒 如:1459315616020
     * @param dateFormat
     *            指定格式
     * @return
     */
    public static String toDateTimeStr(long millis, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);
        return format.format(millis);
    }

    /**
     * 转日期类型
     *
     * @param millis
     * @return
     */
    public static Date toDate(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.getTime();
    }

    /**
     * 字符串转Date，若字符串非 yyyy-MM-dd 格式，则返回null
     *
     * @param strDate
     * @return
     */
    public static Date toDateYMD(String strDate) {
        return toDate(strDate, "yyyy-MM-dd", null);
    }

    /**
     * 字符串转Date，若字符串非 yyyy-MM 格式，则返回null
     *
     * @param strDate
     * @return
     */
    public static Date toDateYM(String strDate) {
        return toDate(strDate, "yyyy-MM", null);
    }

    /**
     *
     * @param strDate
     * @return
     */
    public static Date toDate(String strDate) {
        return toDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 字符串转Date，若字符串非 dateFormat 格式，则返回null
     *
     * @param strDate
     * @param dateFormat
     *            需要转换的日期格式，如yyyy-MM
     * @return 转换失败返回null
     */
    public static Date toDate(String strDate, String dateFormat) {
        return toDate(strDate, dateFormat, null);
    }

    /**
     * 字符串转Date，若字符串非 dateFormat 格式，则返回defaultValue
     *
     * @param strDate
     * @param dateFormat
     *            需要转换的日期格式，如yyyy-MM
     * @param defaultValue
     *            转换失败返回的默认值
     * @return
     */
    public static Date toDate(String strDate, String dateFormat, Date defaultValue) {
        if (isStrEmpty(strDate)) {
            return defaultValue;
        }
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);//false：比较宽松的日期验证方式
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    /**
     * 时间类型转换
     * 非 yyyy-MM-dd 格式字符串转换为  yyyy-MM-dd 格式
     * @param dateStr
     */
    public static String toStrYMD(String dateStr){
        //判断格式是否正确
        if(Fn.isDateFormat(dateStr, "yyyy-MM-dd")){
            return dateStr.substring(0, 10);
        }else if(Fn.isDateFormat(dateStr, "yyyy-MM")){
            return  dateStr.substring(0, 7)+"-01";
        }else if(Fn.isDateFormat(dateStr, "yyyy")){
            return  dateStr.substring(0, 4)+"-01-01";
        }else{
            return Fn.toDateStr(new Date());
        }
    }

    /**
     * 给时间添加天
     *
     * @param d
     * @param num
     * @return
     */
    public static Date addDate(Date d, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + num);
        return c.getTime();
    }

    /**
     * 给时间添加天
     *
     * @param num
     * @return
     */
    public static Date addDate(int num) {
        return Fn.addDate(new Date(), num);
    }

    /**
     * 给时间添加（日，月，年）
     *
     * @param d
     * @param num
     * @param dateType
     *            y:年 m：月 d：日
     * @return
     */
    public static Date addDate(Date d, int num, String dateType) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if (dateType.equals("y")) {
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + num);
        } else if (dateType.equals("m")) {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) + num);
        } else if (dateType.equals("d")) {
            c.set(Calendar.DATE, c.get(Calendar.DATE) + num);
        }
        return c.getTime();
    }

    /**
     * 获取当前所在的月份  2017-04
     * 2017年4月26日下午7:59:10
     */
    public static String getNowYM(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    /**
     * 判断是否日期类型 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Boolean isDate(String strDate) {
        return isDateFormat(strDate, "yyyy-MM-dd");
    }

    /**
     * 判断是否日期类型 yyyy-MM
     * @param strDate
     * @return
     */
    public static Boolean isDateYM(String strDate){
        return isDateFormat(strDate, "yyyy-MM");
    }

    /**
     * 判断是否日期类型 yyyy-MM-dd
     * @param strDate
     * @return
     */
    public static Boolean isDateYMD(String strDate){
        return isDateFormat(strDate, "yyyy-MM-dd");
    }

    /**
     * 判断是否时间类型 HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Boolean isTime(String strDate) {
        return isDateFormat(strDate, "HH:mm:ss");
    }

    /**
     * 判断字符串是否可以转时间类型 yyyy-MM-dd HH:mm
     *
     * @param strDate
     * @return
     */
    public static Boolean isDateTime(String strDate) {
        return isDateFormat(strDate, "yyyy-MM-dd HH:mm");
    }

    /**
     * 判断字符串是否可以转 指定的时间类型
     *
     * @param strDate
     * @param dateFormat
     *            时间格式
     * @return
     */
    public static Boolean isDateFormat(String strDate, String dateFormat) {
        if (isStrEmpty(strDate)) {
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);
        try {
            format.parse(strDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否为空，去除前后空格
     *
     * @param str
     * @return true:空的 false:非空
     */
    public static Boolean isStrEmpty(String str) {
        return str == null || trim(str).isEmpty();
    }

    /**
     * 判断对象是否为空（对象的toString()），去除前后空格
     *
     * @param o
     * @return
     */
    public static Boolean isStrEmpty(Object o) {
        return o == null || trim(o.toString()).isEmpty();
    }

    /**
     * 对象转字符串，当null时，返回空字符串，并去除前后空格
     *
     * @param o
     * @return
     */
    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString().trim();
    }

    /**
     * 获取当前绝对路径，即当前上下文（类资源）的路径)
     *
     * @return 如：/E:/proj/.../Release/wtpwebapps/SXF/WEB-INF/classes/
     */
    public static String getResource() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /**
     * 获取指定的绝对路径
     *
     * @param resourcePath
     * @return 如：/E:/proj/.../Release/wtpwebapps/SXF/WEB-INF/classes/
     */
    public static String getResource(String resourcePath) {
        return Thread.currentThread().getContextClassLoader().getResource(resourcePath).getPath();
    }

    /**
     * 判断字符串是否可以转double
     *
     * @param str
     * @return
     */
    public static Boolean isDouble(String str) {
        if (str == null) {
            return false;
        }

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * 判断对象是否可以转double
     *
     * @param o
     * @return
     */
    public static Boolean isDouble(Object o) {
        if (o == null) {
            return false;
        }
        String str = o.toString();

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * 对象转double，失败返回failureVal
     *
     * @param o
     * @param failureVal
     * @return
     */
    public static Double toDouble(Object o, double failureVal) {
        if (o == null) {
            return failureVal;
        }
        String str = o.toString();

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return failureVal;
        }
    }

    /**
     * 对象转double，失败返回0
     *
     * @param o
     * @return
     */
    public static Double toDouble(Object o) {
        if (o == null) {
            return 0d;
        }
        String str = o.toString();

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return 0d;
        }
    }

    /**
     * 保留两位小数
     *
     * @param val
     * @return
     */
    public static String toFixed(Double val) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(val);
    }

    /**
     * 保留N位小数（或数字格式化）
     *
     * @param val
     * @param format
     *            格式化模板（如保留4位小数，使用 0.0000 ）
     * @return
     */
    public static String toFixed(Double val, String format) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(format);
        return df.format(val);
    }

    /**
     * 判断字符串是否可以转int
     *
     * @param str
     *            字符串
     * @return
     */
    public static Boolean isInt(String str) {
        if (str == null) {
            return false;
        }

        return intPaddern.matcher(str).find();
    }

    /**
     * 判断对象是否可以转int
     *
     * @param o
     * @return
     */
    public static Boolean isInt(Object o) {
        if (o == null) {
            return false;
        }
        String str = o.toString();

        Matcher mer = intPaddern.matcher(str);
        return mer.find();
    }

    /**
     * 对象转int(默认是对象的toString())，失败返回 failureVal
     *
     * @param o
     * @param failureVal
     * @return
     */
    public static Integer toInt(Object o, int failureVal) {
        if (o == null) {
            return failureVal;
        }
        String str = o.toString();

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return failureVal;
        }
    }

    /**
     * 对象转int(默认是对象的toString())，失败返回 0
     *
     * @param o
     * @return
     */
    public static Integer toInt(Object o) {
        if (o == null) {
            return 0;
        }
        String str = o.toString();
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * 字符串重新组合 “1,2,a,3,4” -> 1,2,3,4
     *
     * @param t
     * @return
     */
    public static String clearNoInt(String t) {
        String[] arr = t.split(",");
        List<String> list = new ArrayList<>();
        for (String val : arr) {
            if (isInt(val)) {
                list.add(val);
            }
        }

        return String.join(",", list);
    }

    /**
     * 字符串重新组合 -->以；分割的字符串放入集合
     *
     * @param t
     * @return
     */
    public static String[] strToList(String t) {
        String[] arr = t.split(";");
        return arr;
    }

    /**
     * 去除换行符制表符 /r /n /t
     *
     * @param t
     * @return
     */
    public static String removeline(String t) {
        String newS = "";
        if (t != null) {
            Pattern p = Pattern.compile("\\t|\r|\n");
            Matcher m = p.matcher(t);
            newS = m.replaceAll("");
        }
        return newS;
    }

    /**
     * 获取月的总天数
     *
     * @param date
     *            月份 如：2016-07
     * @return
     */
    public static int getMonthCountDay(String date) {
        Calendar c = Fn.getCalendar(date, "yyyy-MM");
        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数
        return day;
    }

    /**
     * 获取周几（周一等1...周天=7）
     * @param date
     * @return
     */
    public static Integer getWeek(String date){
        Calendar c = getCalendar(date, "yyyy-MM-dd");
        return getWeek(c);
    }

    /**
     * 获取周几（周一等1...周天=7）
     * @param date
     * @return
     */
    public static Integer getWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getWeek(c);
    }

    /**
     * 获取周几（周一等1...周天=7）
     * @param c
     * @return
     */
    public static Integer getWeek(Calendar c){
        int week=c.get(Calendar.DAY_OF_WEEK)-1;
        if (week==0) {
            week=7;
        }
        return week;
    }

    /**
     * 获取日期字符串的Calendar类型
     * @param date
     * @param format
     * @return
     */
    public static Calendar getCalendar(String date,String format){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * 获取日期Date的Calendar类型
     * @param date
     * @param format
     * @return
     */
    public static Calendar getCalendar(Date date,String format){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 获取周一的日期
     * @param date
     * @return 返回 即指定日期所在周的第1天日期
     */
    public static Date getMonday(String date){
        Calendar c = getCalendar(date, "yyyy-MM-dd");
        int week=getWeek(c);
        c.add(Calendar.DATE, -week + 1);
        return c.getTime();
    }

    /**
     * 获取周天的日期
     * @param date
     * @return 返回 指定日期所在周的第7天的日期
     */
    public static Date getSunday(String date){
        Calendar c = getCalendar(date, "yyyy-MM-dd");
        int week=getWeek(c);
        c.add(Calendar.DATE, 7-week);
        return c.getTime();
    }

    /**
     * 获取年月集合
     * @param startYM 2016-02
     * @param endYM 2016-06
     * @return List集合，如：[2016-02, 2016-03, 2016-04, 2016-05, 2016-06]
     */
    public static List<String> getYMList(String startYM, String endYM) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            min.setTime(sdf.parse(startYM));
            max.setTime(sdf.parse(endYM));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> result = new ArrayList<>();
        Calendar curr = min;
        while (curr.compareTo(max)<=0) {//月累加
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * 取数组中最大值 2016年9月12日 上午10:20:52
     *
     * @param arr
     *            object 数组
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T getMaxByArr(T[] arr) {
        T max = (T) toDouble(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            T tmp = (T) toDouble(arr[i]);

            if (tmp.doubleValue() > max.doubleValue()) {
                max = tmp;
            }
        }
        return max;
    }

    /**
     * 集合转化分页 2016年9月10日 下午5:57:04
     *
     * @param list
     *            需分页的集合
     * @param pageIndex
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 返回Page对象，Page对象的getList()返回结果是经过筛选后的当前pageIndex页的记录
     */
    public static <T> Page<T> listToPage(Collection<T> list, int pageIndex, int pageSize) {
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize <= 0) {
            pageSize = 1;
        }

        int rowTotal = list.size();// 总条数
        int pageTotal = rowTotal / pageSize;// 总页数
        int startIndex = (pageIndex - 1) * pageSize;// 所在页码的开始索引
        int endIndex = startIndex + pageSize;// 所在页码的结束索引

        // 若最后一页不满pageSize条
        if (rowTotal % pageSize > 0) {
            pageTotal++;
        }

        List<T> tmpResult = new ArrayList<>();// 分页后的结果集

        int itIndex = 0;// 迭代器索引
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (itIndex >= endIndex) {
                break;// 找完后退出
            }

            T val = (T) it.next();
            // 若页码在指的开始索引和结束索引，则加入结果集
            if (startIndex <= itIndex && itIndex < endIndex) {
                tmpResult.add(val);
            }
            itIndex++;
        }

        Page<T> result = new Page<>(tmpResult, pageIndex, pageSize, pageTotal, rowTotal);
        return result;
    }

    /**
     * 获取两数的百分比，保留2位小数 2016年10月8日 下午8:38:18
     *
     * @param fenzi
     *            分子
     * @param fenmu
     *            分母
     * @return 如：1/2 则结果为 50.00
     */
    public static String getPercent(double fenzi, double fenmu) {
        return getPercent(fenzi, fenmu, "0.00");
    }

    /**
     * 获取两数的百分比
     *
     * @param fenzi
     *            分子
     * @param fenmu
     *            分母
     * @param format
     *            返回格式：如保留4位小数使用：0.0000
     * @return 根据format返回数字格式
     */
    public static String getPercent(double fenzi, double fenmu, String format) {
        if (fenmu == 0) {
            return "0.00";
        }
        double f = fenzi / fenmu * 100;
        DecimalFormat df = new DecimalFormat(format);// 格式化小数
        return df.format(f);
    }

    /**
     * 获取几个月份之间的所有集合 比如:2016-01,2016-03。返回的结果为:2016-01,2016-02,2016-03
     *
     * @param startdate
     *            开始时间
     * @param enddate
     *            结束时间
     * @return
     */
    public static List<String> getAllMonths(String startdate, String enddate) {
        // 判断格式是否正确
        if (!Fn.isDateFormat(startdate + "-01", "yyyy-MM-dd")) {
            return new ArrayList<>();
        }
        if (!Fn.isDateFormat(enddate + "-01", "yyyy-MM-dd")) {
            return new ArrayList<>();
        }

        // 循环日期，加入到返回集合中
        Date d1 = Fn.toDate(startdate + "-01", "yyyy-MM-dd");
        Date d2 = Fn.toDate(enddate + "-01", "yyyy-MM-dd");
        List<String> returnList = new ArrayList<>();
        while (d1.compareTo(d2) <= 0) {
            returnList.add(Fn.toDateTimeStr(d1, "yyyy-MM"));
            d1 = Fn.addDate(d1, 1, "m");
        }

        return returnList;
    }

    /**
     * 排序，针对List<map>进行排序
     *
     * @param list
     *            要排序的集合
     * @param fieldName
     *            要排序的字段
     * @param action
     *            要排序的方法
     */
    public static <T1, T2> void listMapSort(List<Map<T1, T2>> list, String fieldName,
                                            Consumer<? super CompareAssist<T2>> action) {
        //针对list进行排序
        Collections.sort(list, new Comparator<Map<T1, T2>>() {
            @Override
            public int compare(Map<T1, T2> o1, Map<T1, T2> o2) {
                CompareAssist<T2> ca = new CompareAssist<T2>(o1.get(fieldName), o2.get(fieldName));
                action.accept(ca);
                return ca.getResult();
            }
        });
    }

    /**
     * 排序，针对List任意类型的集合排序
     *
     * @param list
     *            要排序的集合
     * @param action
     *            要排序的方法，匿名方法可参考SortHelper.listMapNumber()的使用方式
     */
    public static <T> void listSort(List<T> list, Consumer<? super CompareAssist<T>> action) {
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                CompareAssist<T> ca = new CompareAssist<T>(o1, o2);
                action.accept(ca);
                return ca.getResult();
            }
        });
    }

    /**
     * 字符串比较，支持中文
     *
     * @param s1
     * @param s2
     * @return 0:s1=s2 1:s1>s2 -1:s1<s2
     */
    public static int compareString(String s1, String s2) {
        Comparator<Object> cmp = java.text.Collator.getInstance(java.util.Locale.CHINA);
        return cmp.compare(s1, s2);
    }

    /**
     * 过滤字符串前后空格，支持全角
     * @param str
     * @return 若为null，返回空字符串
     */
    public static String trim(String str) {
        if (str==null) {
            return "";
        }
        //第1个：全角空格 encodeURI('　')=%E3%80%80
        //第2个：半角空格 encodeURI(' ')=%20
        //第3个：特殊空格 encodeURI(' ')=%C2%A0
        while (str.startsWith("　") || str.startsWith(" ") || str.startsWith(" ")) {// 全角空格
            str = str.substring(1, str.length()).trim();
        }
        while (str.endsWith("　") || str.endsWith(" ") || str.endsWith(" ")) {// 全角空格
            str = str.substring(0, str.length() - 1).trim();
        }
        return str;
    }

    /**
     * 过滤字符串前后空格，支持全角
     * @param o
     * @return 若为null，返回空字符串
     */
    public static String trim(Object o) {
        if (o==null) {
            return "";
        }
        return trim(o.toString());
    }

    /**
     * 两个时间相减得到的时间差
     * @param firstDateStr
     * @param secondDateStr
     * @return
     */
    public static long getDateDifference(String firstDateStr,String secondDateStr){
        Date a=Fn.toDate(firstDateStr,"yyyy-MM-dd HH:mm:ss");
        Date b=Fn.toDate(secondDateStr,"yyyy-MM-dd HH:mm:ss");
        long interval = (b.getTime() - a.getTime())/1000;

        System.out.println("两个时间相差"+interval+"秒");//会打印出相差3秒
        return interval;
    }

    /**
     * 对象转Long，失败返回failureVal
     *
     * @param o
     * @param failureVal
     * @return
     */
    public static Long toLong(Object o, Long failureVal) {
        if (o == null) {
            return failureVal;
        }
        String str = o.toString();

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException ex) {
            return failureVal;
        }
    }

    /**
     * 对象转Long，失败返回0
     *
     * @param o
     * @return
     */
    public static Long toLong(Object o) {
        if (o == null) {
            return 0l;
        }
        String str = o.toString();

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException ex) {
            return 0l;
        }
    }
}
