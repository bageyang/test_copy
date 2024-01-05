package com.zj.auction.common.utils;
import com.zj.auction.common.constant.SystemConstant;
import com.zj.auction.common.exception.ServiceException;
import com.zj.auction.common.exception.SystemExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 *
 *************************************************
 * 公用的工具类
 *
 * @author MengDaNai
 * @version 1.7
 * @date 2019年2月1日 创建文件
 * @See
 *************************************************
 */
@Service
public class PubFun {

    public static void main(String[] args) {

        NumberFormat nf = NumberFormat.getInstance();
        String format = nf.format(3.001);
        System.out.println("nf = " + format);

    }
    /**
     * 验证用户名账户名
     * @Author: Mr.zhao
     * @Date: 2020/5/2
     * @Param:
     * @return: boolean
     */
    public static boolean isUserName(String name) {
        try {
            String regExp = "^[^0-9][\\w_]{5,9}$";
            boolean matches = name.matches(regExp);
            Pattern pattern = Pattern.compile("([a-z]|[A-Z])*");
            boolean matches1 = pattern.matcher(name).matches();
            Pattern p = Pattern.compile("^[1][1-9][0-9]{9}$"); // 验证手机号
            boolean matches2 = p.matcher(name).matches();
            boolean matches3 = Pattern.compile("^-?[0-9]+").matcher(name).matches();
//            String regex = "^[a-zA-Z][0-9a-zA-Z_]{3,15}$";
//            boolean matches2 = Pattern.matches(regex, name);
            return matches || matches1 || matches2 || matches3;
        }catch (Exception e){
            return false;
        }
    }
    /**
    * 分组 数据返回Object 并行
    * @Author: Mr.zhao
    * @Date: 2020/6/16
    * @Param: * @param: list
     * @param: deal
    * @return: java.util.Map<K,T>
    */
    public static <T,K> Map<K,T> listToMapByGroupObject(List<T> list,Function<T,K> deal){
        return listToMapByGroupObject(list, deal, true);
    }
    /**
    * 分组 数据返回Object
    * @Author: Mr.zhao
    * @Date: 2020/6/16
    * @Param: * @param: list
     * @param: deal
     * @param: parallel
    * @return: java.util.Map<K,T>
    */
    public static <T,K> Map<K,T> listToMapByGroupObject(List<T> list,Function<T,K> deal, boolean parallel){
        return stream(list, parallel).collect(toMap(deal,e -> e));
    }
    /**
    * 返回stream
    * @Author: Mr.zhao
    * @Date: 2020/6/16
    * @Param: * @param: list
     * @param: parallel
    * @return: java.util.stream.Stream<T>
    */
    private static <T> Stream<T> stream(List<T> list, boolean parallel){
        return parallel ? list.stream():list.parallelStream();
    }
    /**
    * 把map处理成stream
    * @Author: Mr.zhao
    * @Date: 2020/6/16
    * @Param: * @param: map
    * @return: java.util.stream.Stream<java.util.Map.Entry<T,K>>
    */
    public static <T, K> Stream<Map.Entry<T, K>> mapStream(Map<T, K> map){
        return mapStream(map, true);
    }
    /**
    * 把map处理成stream
    * @Author: Mr.zhao
    * @Date: 2020/6/16
    * @Param: * @param: map
     * @param: parallel
    * @return: java.util.stream.Stream<java.util.Map.Entry<T,K>>
    */
    public static <T, K> Stream<Map.Entry<T, K>> mapStream(Map<T, K> map, boolean parallel){
        return stream(map.entrySet(), parallel);
    }

    /**
    * 返回stream
    * @Author: Mr.zhao
    * @Date: 2020/6/16
    * @Param: * @param: set
     * @param: parallel
    * @return: java.util.stream.Stream<T>
    */
    private static <T> Stream<T> stream(Set<T> set, boolean parallel){
        return parallel ? set.stream():set.parallelStream();
    }

    /**
     * @Title: StrSplitToList
     * @Description:  将字符串按照指定的方式才分为 Arr数组
     * @author yz
     * @date 2019年9月12日 下午3:18:15
     * @param str
     * @param spl  拆分当时
     * @return
     */
    public static List<String> strSplitToList(String str,String spl) {
        String[] array = strSplitToArr(str, spl);
        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * @Title: StrSplitToList
     * @Description:  将字符串按照指定的方式才分为 Arr数组
     * @author yz
     * @date 2019年9月12日 下午3:18:15
     * @param str
     * @param spl  拆分当时
     * @return
     */
    public static String[] strSplitToArr(String str,String spl) {
        if(StringUtils.isBlank(str)) {
            return new String[]{};
        }
        return StringUtils.split(str,spl);
    }

    /**
     * 手机号验证
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        try {
            Pattern p = Pattern.compile("^[1][1-9][0-9]{9}$"); // 验证手机号
            Matcher m = p.matcher(str);
            return m.matches();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 更具网络地址获取文件输出流
     * @param path
     * @return
     * @throws Exception
     */
    public static InputStream getFileUrlInputStream(String path) {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-type","application/x-java-serialized-object");
            urlConn.setRequestMethod("GET");
            urlConn.connect();
            return urlConn.getInputStream();
        } catch (Exception e) {
            throw new ServiceException(2,"获取地址文件失败："+path+"!!!");
        }
    }

    public static <T> T requireNonNull(T t,String message){
        if(Objects.isNull(t)){
            throw new ServiceException(1,message);
        }
        return t;
    }

    /**
     * @Description 校验参数
     * @Title check
     * @Author Mao Qi
     * @Date 2020/2/16 13:52
     * @param objects
     * @return	void
     */
    public static void check(Object... objects){
        Arrays.stream(objects).filter(e -> Objects.isNull(e) || StringUtils.isBlank(e.toString())).forEach(e -> {
            throw new ServiceException(SystemConstant.DATA_NO_ISEMPTY_CODE,SystemConstant.DATA_NO_ISEMPTY);
        });
    }


    /**
     *************************************************
     * 	更好的数据校验
     * @author  MengDaNai
     * @param param
     * @return true：数据为空
     * @date    2019年4月12日 创建文件
     *************************************************
     */
    public static Boolean dataCheck(Object... param) {
        Predicate<Object> deal = e -> {
            if(Objects.isNull(e)) return true;
            if(e instanceof String&&StringUtils.isBlank(e.toString())) return true;
            if(e instanceof List) {
                List<?> list = (List<?>) e;
                if(list.isEmpty()) return true;
            }
            if(e instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) e;
                if(map.isEmpty()) return true;
            }
            if(e instanceof BigDecimal) {
                BigDecimal b = (BigDecimal) e;
                if(b.doubleValue()<=0) return true;
            }
            if(e instanceof Double) {
                Double d = (Double) e;
                if(d<=0) return true;
            }
            return false;
        };
        return Arrays.asList(param).parallelStream().filter(deal).count()!=0;
    }

    /**
     * 判断是否为空
     *
     * @param object
     */
    public static void isNull(Object object, int code) {
        if (Objects.isNull(object)) {
            throw new ServiceException(SystemExceptionEnum.PARAM_ERROR);
        }
    }



    /**
     *************************************************
     * Object转int
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static int ObjectToInt(Object param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return StringToInt(ObjectToString(param));
    }
    public static int ObjectStrongToInt(Object obj) {
        return Objects.isNull(obj)?0:StringToInt(ObjectToString(obj));
    }

    /**
     *************************************************
     * Object转String
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static String ObjectToString(Object param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return param.toString();
    }
    public static String ObjectStrongToString(Object param) {
        return Objects.toString(param, "");
    }
    /**
     *************************************************
     * Object转long
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static long ObjectToLong(Object param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return Long.valueOf(ObjectToString(param));
    }
    public static long ObjecStrongtToLong(Object param) {
        return Objects.isNull(param)?0:Long.valueOf(ObjectToString(param));
    }
    public static long ObjecStrongtToLong(Object param, Long dealt) {
        try {
            return Objects.isNull(param) ? dealt:StringToDouble(ObjectToString(param)).longValue();
        }catch (Exception e){
            return dealt;
        }
    }
    /**
     *************************************************
     * Object转double
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static double ObjectToDouble(Object param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return Double.parseDouble(ObjectToString(param));
    }
    public static double ObjectStrongToDouble(Object param) {
        return Objects.isNull(param)?0.00:Double.parseDouble(ObjectToString(param));
    }
    /**
     *************************************************
     * String转int
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static int StringToInt(String param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return Integer.parseInt(param);
    }
    public static int StringStrongToInt(String param) {
        return Objects.isNull(param)?0:Integer.parseInt(param);
    }
    /**
     *************************************************
     * String转long
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static long StringToLong(String param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return Long.valueOf(param);
    }
    public static long StringStrongToLong(String param) {
        return Objects.isNull(param)?0:Long.valueOf(param);
    }
    /**
     *************************************************
     * String转double
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static Double StringToDouble(String param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return Double.parseDouble(param);
    }
    public static Double StringStrongToDouble(String param) {
        return Objects.isNull(param)?0.00:Double.parseDouble(param);
    }
    /**
     *************************************************
     * int转string
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static String IntToString(int param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return String.valueOf(param);
    }
    public static String IntStrongToString(int param) {
        return param==0?"":String.valueOf(param);
    }
    /**
     *************************************************
     * int转double
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static double IntToDouble(int param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return param;
    }
    public static double IntStrongToDouble(int param) {
        return param==0?0.00:param;
    }
    /**
     *************************************************
     * int转long
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static long IntToLong(int param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return param;
    }
    public static long IntStrongToLong(int param) {
        return param==0?0:param;
    }
    /**
     *************************************************
     * double转long
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static long DoubleToLong(double param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return Math.round(param);
    }
    public static long DoubleStrongToLong(double param) {
        return param==0?0:Math.round(param);
    }
    /**
     *************************************************
     * double转String
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static String DoubleToString(double param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return String.valueOf(param);
    }
    public static String DoubleStrongToString(double param) {
        return param==0?"":String.valueOf(param);
    }
    /**
     *************************************************
     * double转int
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static int DoubleToInt(double param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return (int) param;
    }
    public static int DoubleStrongToInt(double param) {
        return param==0?0:(int) param;
    }
    /**
     *************************************************
     * Long转int
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static int LongToInt(Long param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return Integer.parseInt(LongToString(param));
    }
    public static int LongStrongToInt(Long param) {
        return param==0?0:Integer.parseInt(LongToString(param));
    }
    /**
     *************************************************
     * Long转string
     * @author MengDaNai
     * @param param
     * @return
     * @date 2019年2月1日 创建文件
     *************************************************
     */
    public static String LongToString(Long param) {
        isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
        return param.toString();
    }
    public static String LongStrongToString(Long param) {
        return param==0?"":param.toString();
    }

    /**
     * 加法运算
     * @param m1 被加数
     * @param m2 加数
     * @return
     */
    public static Double addBigDecimal(Object m1, Object m2) {
        BiFunction<Object, Object, Double> deal = (o, t) -> {
            BigDecimal p1 = new BigDecimal(Double.toString(ObjectToDouble(m1)));
            BigDecimal p2 = new BigDecimal(Double.toString(ObjectToDouble(m2)));
            return p1.add(p2).doubleValue();
        };
        return deal.apply(m1, m2);
    }

    /**
     * 减法运算
     * @param m1 被减数
     * @param m2 减数
     * @return
     */
    public static Double minusBigDecimal(Object m1, Object m2) {
        BiFunction<Object, Object, Double> deal = (o, t) -> {
            BigDecimal p1 = new BigDecimal(Double.toString(ObjectToDouble(m1)));
            BigDecimal p2 = new BigDecimal(Double.toString(ObjectToDouble(m2)));
            return p1.subtract(p2).doubleValue();
        };
        return deal.apply(m1, m2);
    }

    /**
     * 乘法运算
     * @param m1 被乘数
     * @param m2 乘数
     * @return
     */
    public static double multiplicationBigDecimal(Object m1, Object m2) {
        BiFunction<Object, Object, Double> deal = (o, t) -> {
            BigDecimal p1 = new BigDecimal(Double.toString(ObjectToDouble(m1)));
            BigDecimal p2 = new BigDecimal(Double.toString(ObjectToDouble(m2)));
            return p1.multiply(p2).doubleValue();
        };
        return deal.apply(m1, m2);
    }

    /**
     * 除法运算
     * @param m1    除数
     * @param m2    被除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return
     */
    public static double divisionBigDecimal(Object m1, Object m2, int scale) {
        BiFunction<Object, Object, Double> deal = (o, t) -> {
            if (scale < 0)
                throw new IllegalArgumentException("Parameter error");
            BigDecimal p1 = new BigDecimal(Double.toString(ObjectToDouble(m1)));
            BigDecimal p2 = new BigDecimal(Double.toString(ObjectToDouble(m2)));
            return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        };
        return deal.apply(m1, m2);
    }



    /**
     *************************************************
     * 判断指定文件夹是否存在 不存在则创建
     * @author MengDaNai
     * @param filePath
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static void checkFolderExists(String filePath) {
        PubFun.isNull(filePath, SystemConstant.DATA_ILLEGALITY_CODE);
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }

    /**
     *************************************************
     * 获取文件后缀
     * @author MengDaNai
     * @param fileName
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static String getFileSuffix(String fileName) {
        PubFun.isNull(fileName, SystemConstant.DATA_ILLEGALITY_CODE);
        return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
    }




    /**
     *************************************************
     * 获取订单号
     * @author MengDaNai
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static String GetTheOrderNumber() {
        return PubFun.timeFormatConversion("YYYYMMDDHHmmss") + System.nanoTime();
    }

    /**
     *************************************************
     * 获取当前时间
     * @author MengDaNai
     * @param timeFormat
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static String timeFormatConversion(String timeFormat) {
        PubFun.isNull(timeFormat, SystemConstant.DATA_ILLEGALITY_CODE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        return simpleDateFormat.format(new Date());
    }

    /**
     *************************************************
     * 获取当前时间
     * @author MengDaNai
     * @param timeFormat
     * @return
     * @throws ParseException
     * @date 2019年3月25日 创建文件
     *************************************************
     */
    public static Date timeFormatConversionDate(String timeFormat) throws ParseException {
        PubFun.isNull(timeFormat, SystemConstant.DATA_ILLEGALITY_CODE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        return simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    }



    /**
     *************************************************
     * 获取两个时间相差的天数
     * @author 曾鑫->MengDaNai
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static Long getDiffDay(Date beginTime, Date endTime) {
        PubFun.isNull(beginTime, SystemConstant.DATA_ILLEGALITY_CODE);
        PubFun.isNull(endTime, SystemConstant.DATA_ILLEGALITY_CODE);
        // 计算两个日期的月差
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.setTime(beginTime);
        end.setTime(endTime);
        long time1 = begin.getTimeInMillis();
        long time2 = end.getTimeInMillis();
        return (time2 - time1) / (1000 * 3600 * 24);
    }


    /**
     *************************************************
     * 获取两个时间相差的小时数
     * @author 曾鑫->MengDaNai
     * @param beginTime
     * @param endTime
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static Long getDiffHour(Date beginTime, Date endTime) {
        PubFun.isNull(beginTime, SystemConstant.DATA_ILLEGALITY_CODE);
        PubFun.isNull(endTime, SystemConstant.DATA_ILLEGALITY_CODE);
        // 计算两个日期的月差
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.setTime(beginTime);
        end.setTime(endTime);
        long time1 = begin.getTimeInMillis();
        long time2 = end.getTimeInMillis();
        return (time2 - time1) / (1000 * 3600);
    }



    /**
     *************************************************
     * 获取两个时间相差的分钟数
     * @author 曾鑫->MengDaNai
     * @param beginTime
     * @param endTime
     * @return
     * @date 2019年3月4日 创建文件
     *************************************************
     */
    public static Long getDiffMinute(Date beginTime, Date endTime) {
        PubFun.isNull(beginTime, SystemConstant.DATA_ILLEGALITY_CODE);
        PubFun.isNull(endTime, SystemConstant.DATA_ILLEGALITY_CODE);
        // 计算两个日期的月差
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.setTime(beginTime);
        end.setTime(endTime);
        long time1 = begin.getTimeInMillis();
        long time2 = end.getTimeInMillis();
        return (time2 - time1) / (1000 * 60);
    }

    /**
     *************************************************
     * 去掉最后一个字符
     * @author MengDaNai
     * @param str
     * @return
     * @date 2019年3月13日 创建文件
     *************************************************
     */
    public static String removeTheLastOne(String str) {
        PubFun.isNull(str, SystemConstant.DATA_ILLEGALITY_CODE);
        return str.substring(0, str.length() - 1);
    }

    /**
     *************************************************
     *	校验是否是数字
     * @author  MengDaNai
     * @param str
     * @return
     * @date    2019年3月28日 创建文件
     *************************************************
     */
    public static Boolean DigitalCheck(String str) {
        PubFun.isNull(str, SystemConstant.DATA_ILLEGALITY_CODE);
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }


    /**
     * @Title: throwException
     * @Description: 用于Optional错误返回
     * @author：Mao Qi
     * @date： 2019年7月9日上午10:40:43
     * @param errorMessage
     * @return
     * @return：RuntimeException
     */
    public static RuntimeException throwException(String errorMessage){
        return new RuntimeException(errorMessage);
    }

    /**
     *************************************************
     *	生成指定位数的随机数
     * @author  MengDaNai
     * @param length 生成随机数的长度
     * @return
     * @date    2019年3月8日 创建文件
     *************************************************
     */
    public static String getRandom(int length){
        isNull(length, SystemConstant.DATA_ILLEGALITY_CODE);
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * @title: isNumber
     * @description: 校验金额的正则表达式
     * @author: Mao Qi
     * @date: 2020年4月3日下午8:13:33
     * @param str
     * 正确返回true,错误返回false
     * @return: boolean
     */
    public static boolean isNumber(String str) {
        // 判断小数点后2位的数字的正则表达式
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * @title: selectToString
     * @description: angularjs获取下拉列表框中的字符串
     * @author: Mao Qi
     * @date: 2019年8月4日下午7:38:03
     * @param param
     * @return: String 返回类型
     */
    public static String selectToString(String param) {
        String paramStr = param;
        return paramStr.substring(paramStr.indexOf(':')+1);
    }

    /**
     * @title: selectToInteger
     * @description: angularjs获取下拉列表框中的ID
     * @author: Mao Qi
     * @date: 2019年8月4日下午7:38:03
     * @param param
     * @return: String 返回类型
     */
    public static Integer selectToInteger(String param) {
        String paramStr = param;
        return ObjectStrongToInt(paramStr.substring(paramStr.indexOf(':')+1));
    }

    /**
     * @title: selectToLong
     * @description: angularjs获取下拉列表框中的ID
     * @author: Mao Qi
     * @date: 2019年8月4日下午7:38:03
     * @param param
     * @return: String 返回类型
     */
    public static Long selectToLong(String param) {
        String paramStr = param;
        return ObjecStrongtToLong(paramStr.substring(paramStr.indexOf(':')+1));
    }

    /**
     * 功能描述: <br>
     * 〈分组 数据个数〉
     * @Param: [list, deal]
     * @Return: java.util.Map<K,T>
     * @Author: SHISHI
     * @Date: 2019/6/25 17:26
     */
    public static <T,K> Map<K,Long> listToMapByGroupCount(List<T> list, Function<T,K> deal){
        return list.stream().collect(groupingBy(deal,counting()));
    }


    /**
     * @Title: getBigDecimal
     * @Description: Object转BigDecimal
     * @author Mao Qi
     * @Date 2020年4月3日 下午3:31:35
     * @param obj
     * @return BigDecimal
     */
    public static BigDecimal ObjectToBigDecimal(Object obj) {
        BigDecimal ret = null;
        if(obj != null) {
            if(obj instanceof BigDecimal) {
                ret = (BigDecimal) obj;
            } else if(obj instanceof String) {
                ret = new BigDecimal((String) obj);
            } else if(obj instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) obj);
            } else if(obj instanceof Number) {
                ret = new BigDecimal(((Number)obj).doubleValue());
            } else {
                ret = new BigDecimal(0.0);
            }
        }
        return ret;
    }





    /**
     *************************************************
     *	Object转BigDecimal 保留roundNum位小数
     *************************************************
     */
    public static BigDecimal ObjectToBigDecimal(Object obj, Integer roundNum) {
        BigDecimal ret = null;
        if (obj != null) {
            if (obj instanceof BigDecimal) {
                ret = (BigDecimal) obj;
            } else if (obj instanceof String) {
                ret = new BigDecimal((String) obj);
            } else if (obj instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) obj);
            } else if (obj instanceof Number) {
                ret = new BigDecimal(((Number) obj).doubleValue());
            } else {
                StringBuffer str = new StringBuffer();
                str.append("0.");
                for (int i = 0; i < roundNum; i++) {
                    str.append("0");
                }
                ret = new BigDecimal(str.toString());
            }
        }
        return ret.setScale(roundNum, BigDecimal.ROUND_HALF_UP);
    }
}