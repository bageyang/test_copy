package com.zj.auction.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

/**
 * @author Mao Qi
 * @describe 字符串处理工具类
 * @title com.duoqio.common.util.StringUtils.java
 * @date 2019年6月5日 上午9:14:18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static final String COMMA = ",";
    public static final String UTF8 = "UTF-8";

    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 转换为BigDecimal
     *
     * @title toBigDecimal
     * @author Mao Qi
     * @date 2019年7月16日 上午9:13:38
     */
    public static BigDecimal toBigDecimal(Object val) {
        return toBigDecimal(val, 0D);
    }

    /**
     * 转换为BigDecimal 保留两位小数
     *
     * @title toBigDecimal
     * @author Mao Qi
     * @date 2019年7月16日 上午9:13:38
     */
    public static BigDecimal toBigDecimal2(Object val) {
        return toBigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal toBigDecimal(Object val, final double defaultValue) {
        return new BigDecimal(toDouble(val, defaultValue));
    }

    /**
     * @param bigDecimal 原数据
     * @return java.math.BigDecimal
     * @describe 校验 是否为空并且大于0
     * @title toValidBigDecimal
     * @author Mao Qi
     * @date 2019/8/29 10:11
     */
    public static BigDecimal toValidBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal == null || BigDecimal.ZERO.compareTo(bigDecimal) > 0) {
            bigDecimal = BigDecimal.ZERO;
        }
        return bigDecimal;
    }

    /**
     * 转换为Double类型
     *
     * @param val the val
     * @return the double
     */
    public static Double toDouble(Object val) {
        return toDouble(val, 0.0D);
    }

    /**
     * <pre>
     *   toDouble(null, 1.1d)   = 1.1d
     *   toDouble("", 1.1d)     = 1.1d
     *   toDouble("1.5", 0.0d)  = 1.5d
     * </pre>
     *
     * @param defaultValue the default value
     * @return the double represented by the string, or defaultValue
     * if conversion fails
     */
    public static Double toDouble(final Object val, final double defaultValue) {
        if (val == null || stripToNull(val.toString()) == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(deleteWhitespace(val.toString()));
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 转换为Float类型
     *
     * @param val the val
     * @return the float
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     *
     * @param val the val
     * @return the long
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     *
     * @param val the val
     * @return the integer
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * @describe Object转换为String
     * @author Mao Qi
     * @date 2019/8/1 17:14
     */
    @NonNull
    public static String emptyIfNull(Object val) {
        return val == null ? EMPTY : val.toString();
    }

    /**
     * @param val
     * @param defaultValue
     * @return java.lang.String
     * @describe
     * @title toString
     * @author Mao Qi
     * @date 2019/9/19 17:19
     */
    @NonNull
    public static String toString(Object val, String defaultValue) {
        return val == null || isBlank(val.toString()) ? defaultValue : val.toString();
    }

    /**
     * 是否满足最小长度限制
     *
     * @param value
     * @param minLength
     * @return
     */
    public static boolean minLength(String value, int minLength) {
        return value != null && value.length() >= minLength;
    }

    /**
     * 是否满足最大长度限制
     *
     * @param value
     * @param maxLength
     * @return
     */
    public static boolean maxLength(String value, int maxLength) {
        return value != null && value.length() <= maxLength;
    }

    /**
     * 是否满足长度范围限制
     *
     * @param value
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean rangeLength(String value, int minLength, int maxLength) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }

    /**
     * @param val string
     * @return boolean
     * @describe 是否undefined 或者空值
     * @title isUndefined
     * @author Mao Qi
     * @date 2019/9/19 17:33
     */
    public static boolean isUndefined(String val) {
        return isBlank(val) || equalsIgnoreCase(val, "undefined");
    }


    public static boolean hasText(@Nullable String str) {
        return isNotBlank(str);
    }


    /**
     * @Description 判断是否是BigDecimal
     * @Title isBigDecimal
     * @Author Mao Qi
     * @Date 2020/12/11 15:33
     * @param integer
     * @return	boolean
     */
    public static boolean isBigDecimal(String integer) {
        try{
            BigDecimal bd = new BigDecimal(integer);
            return true;
        }catch(NumberFormatException e) {
            return false;
        }
    }
}
