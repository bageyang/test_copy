package com.zj.auction.common.date;
import com.zj.auction.common.constant.SystemConstant;
import com.zj.auction.common.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.LongFunction;

/**
 * 时间工具类
 * @ClassName: DateUtil.java
 * @author MengDaNai
 * @version 1.0
 * @Date 2019年7月19日 下午5:40:37
 */
@Component
public final class DateUtil {

	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private DateUtil(){}



	/**
	 * 判断时间格式 格式必须为“YYYY-MM-dd”
	 * 2004-2-30 是无效的
	 * 2003-2-29 是无效的
	 * @param sDate
	 * @return
	 */
	public static boolean isLegalDate(String sDate) {
		int legalLen = 10;
		if ((sDate == null) || (sDate.length() != legalLen)) {
			return false;
		}

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(sDate);
			return sDate.equals(formatter.format(date));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取当前时间
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:40:50
	 * @return String
	 */
	public static String getDateTime() {
		LocalDateTime datetime = LocalDateTime.now();
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
		return datetime.format(sdf);
	}
	
	/**
	 * 获取当前时间 
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:41:00
	 * @param pattern 时间格式 yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getDateTime(String pattern) {
		LocalDateTime datetime = LocalDateTime.now();
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(pattern);
		return datetime.format(sdf);
	}
	
	/**
	 * 获取当前时间
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:41:15
	 * @return Date
	 */
	public static Date getDate() {
		String dateTime = getDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
		LocalDateTime startTime=LocalDateTime.parse(dateTime, formatter);
		ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdtStart = startTime.atZone(zoneId);
    	return Date.from(zdtStart.toInstant());
	}
	
	/**
	 * StringToDate
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:41:22
	 * @param dateTime
	 * @return Date
	 */
	public static Date stringToDate(String dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
		LocalDateTime startTime=LocalDateTime.parse(dateTime, formatter);
		ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdtStart = startTime.atZone(zoneId);
    	return Date.from(zdtStart.toInstant());
	}
//    public static Date stringToDate(String dateTime) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
//        LocalDateTime startTime=LocalDateTime.parse(dateTime, formatter);
//        ZoneId zoneId = ZoneId.systemDefault();
//        ZonedDateTime zdtStart = startTime.atZone(zoneId);
//        return Date.from(zdtStart.toInstant());
//    }

	/**
	* 字符串转时间
	* @Author: Mr.zhao
	* @Date: 2020/6/2
	* @Param: * @param: dateTime
	 * @param: ftrStr
	* @return: java.time.LocalDateTime
	*/
    public static LocalDateTime stringToDate(String dateTime, String ftrStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ftrStr);
            return LocalDateTime.parse(dateTime, formatter);
        }catch (Exception e){
            return null;
        }
    }

	public static LocalTime stringToLocalTime(String dateTime, String ftrStr) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ftrStr);
			return LocalTime.parse(dateTime, formatter);
		}catch (Exception e){
			return null;
		}
	}
	
	/**
	 * 获取一个月多少天
	 * @author SHISHI
	 * @Date 2019年7月19日 下午5:41:32
	 * @param date
	 * @return int
	 */
	public static int getDayByMonth(Date date) {
		return dateToLocalDate(date).lengthOfMonth();
	}
	
	/**
	 * 获取时间是星期几
	 * @Author SHISHI
	 * @Date 2019/7/18 14:27
	 * @param date
	 * @Return java.time.DayOfWeek 枚举类型
	 * @See java.time.DayOfWeek 定义
	 */
	public static DayOfWeek getDayOfWeek(Date date) {
		return dateToLocalDate(date).getDayOfWeek();
	}
	
	/**
	 * 把Date转换为LocalDateTime
	 * @Author SHISHI
	 * @Date 2019/7/18 11:43
	 * @param date
	 * @Return java.time.LocalDateTime
	 */
	private static LocalDateTime dateToLocalDateTime(Date date){
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant,zone);
	}
	
	/**
	 * 把Date转换为localDate
	 * @Author SHISHI
	 * @Date 2019/7/18 11:41
	 * @param date
	 * @Return java.time.LocalDate
	 */
	private static LocalDate dateToLocalDate(Date date){
		return dateToLocalDateTime(date).toLocalDate();
	}
	
	/**
	 * 计算时间差
	 * @Author SHISHI->MengDaNai
	 * @Date 2019/7/18 14:14
	 * @param oldTime 过去时间
	 * @param nowTime 当前时间
	 * @param type 要计算的时间差类型
	 * @Return long 技术按结果
	 */
	public static long differenceTime(Date oldTime, Date nowTime, TimeTypeEnum type){
		LocalDateTime oldLocalDateTime = dateToLocalDateTime(oldTime);
		LocalDateTime nowLocalDateTime = dateToLocalDateTime(nowTime);
        return differenceTime(oldLocalDateTime, nowLocalDateTime, type);
	}
	/**
	* 计算时间
	* @Author: Mr.zhao
	* @Date: 2020/6/2
	* @Param: * @param: oldLocalDateTime
	 * @param: nowLocalDateTime
	 * @param: type
	* @return: long
	*/
    public static long differenceTime(LocalDateTime oldLocalDateTime, LocalDateTime nowLocalDateTime, TimeTypeEnum type){
        switch (type) {
            case YEAR:
                return oldLocalDateTime.until(nowLocalDateTime, ChronoUnit.YEARS);
            case MONTH:
                return oldLocalDateTime.until(nowLocalDateTime, ChronoUnit.MONTHS);
            case DAY:
                return Duration.between(oldLocalDateTime, nowLocalDateTime).toDays();
            case HOUR:
                return Duration.between(oldLocalDateTime, nowLocalDateTime).toHours();
            case MINUTE:
                return Duration.between(oldLocalDateTime, nowLocalDateTime).toMinutes();
            case SECOND:
                return Duration.between(oldLocalDateTime, nowLocalDateTime).toMillis()/1000;
            case MILLI:
                return Duration.between(oldLocalDateTime, nowLocalDateTime).toMillis();
            default:
                throw new ServiceException(SystemConstant.OPERATION_FAILED_CODE,"操作失败");
        }
    }
	
	/**
	 * 比较日期大小
	 * <p>
	 * 		time1 = "2018-01-01 00:00:00"
	 * 		time2 = "2019-01-01 00:00:00"
	 * 		return true
	 * 		时间1小于时间2
	 * </p>
	 * @Author SHISHI
	 * @Date 2019/7/18 14:22
	 * @param time1
	 * @param time2
	 * @Return boolean
	 */
	public static boolean compareTime(Date time1,Date time2){
		LocalDateTime localDateTime1 = dateToLocalDateTime(time1);
		LocalDateTime localDateTime2 = dateToLocalDateTime(time2);
		return compareTime(localDateTime1, localDateTime2);
	}
    public static boolean compareTime(LocalDateTime time1,LocalDateTime time2){
        return time1.isBefore(time2);
    }
	
	/**
	 * 对时间加减(不支持乘，除，毫秒级)
	 * @Author SHISHI->MengDaNai
	 * @Date 2019/7/18 14:55
	 * @param time 时间
	 * @param num 计算的数量
	 * @param timeTypeEnum 计算的时间类型
	 * @param calculateTypeEnum 计算的类型
	 * @Return java.util.Date
	 */
	public static LocalDateTime calculatingTime(Date time, CalculateTypeEnum calculateTypeEnum,
									   long num, TimeTypeEnum timeTypeEnum){
		LocalDateTime localDateTime = dateToLocalDateTime(time);
		switch (timeTypeEnum){
			case YEAR: return calculation(num, calculateTypeEnum, localDateTime::plusYears, localDateTime::minusYears);
			case MONTH: return calculation(num, calculateTypeEnum, localDateTime::plusMonths , localDateTime::minusMonths);
			case DAY: return calculation(num, calculateTypeEnum, localDateTime::plusDays, localDateTime::minusDays);
			case HOUR: return calculation(num, calculateTypeEnum, localDateTime::plusHours, localDateTime::minusHours);
			case MINUTE: return calculation(num, calculateTypeEnum, localDateTime::plusMinutes, localDateTime::minusMinutes);
			case SECOND: return calculation(num, calculateTypeEnum, localDateTime::plusSeconds, localDateTime::minusSeconds);
			default:
				throw new ServiceException(SystemConstant.OPERATION_FAILED_CODE,"操作失败");
		}
	}

	/**
	 * 执行加还是减
	 * @Author SHISHI
	 * @Date 2019/7/18 15:15
	 * @param num 计算数量
	 * @param calculateTypeEnum 计算类型
	 * @param addDeal 加方法
	 * @param subDeal 减方法
	 * @Return java.time.LocalDateTime
	 * @return
	 */
	private static LocalDateTime calculation(long num, CalculateTypeEnum calculateTypeEnum,
											 LongFunction<LocalDateTime> addDeal, LongFunction<LocalDateTime> subDeal){
		ZoneId zoneId = ZoneId.systemDefault();
		switch (calculateTypeEnum){
			case ADD: return addDeal.apply(num);
			case SUBTRACT: return subDeal.apply(num);
			default:
				throw new ServiceException(SystemConstant.OPERATION_FAILED_CODE,"操作失败");
		}
	}
}
