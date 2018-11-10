package com.shishic.cb.util;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 类说明： 日期时间工具类
 * 
 * @author Liucd
 * @date Jul 6, 2011 1:46:53 PM
 * @version 1.0
 */
public class DateUtils {
	/**
	 * 
	 * 默认日期格式
	 */
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 时间日期格式化到年月日.
	 */
	public static final String dateFormatYMD = "yyyy-MM-dd";

	/**
	 * @param pubtime
	 *            样例：2011-06-20T17:23:11Z
	 * @return 样例：05-10 17:11
	 */
	public static String getFormatTime(String pubtime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date = null;
		try {
			date = df.parse(pubtime.replace("Z", ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (null == date) ? null : (new SimpleDateFormat("MM-dd HH:mm"))
				.format(date);
	}

	/**
	 * @param pubtime
	 *            样例：2014-08-08 18:30:40
	 * @return 样例：05-10 17:11
	 */
	public static String getFormatTime2(String pubtime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = null;
		try {
			date = df.parse(pubtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (null == date) ? null : (new SimpleDateFormat("MM-dd HH:mm"))
				.format(date);
	}

	/**
	 * @param pubtime
	 *            样例：2011-06-20T17:23:11Z
	 * @param format
	 * @return
	 */
	public static String getFormatTime(String pubtime, String format) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date = null;
		try {
			date = df.parse(pubtime.replace("Z", ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (null == date) ? null : (new SimpleDateFormat(format))
				.format(date);
	}
	/**
	 * @param pubtime
	 *            样例：2011-06-20 17:23:11
	 * @param format
	 * @return
	 */
	public static String getFormatTime3(String pubtime, String format) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = null;
		try {
			date = df.parse(pubtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return (null == date) ? null : (new SimpleDateFormat(format))
				.format(date);
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str, String fromat) {
		Date date = str2Date(str, fromat);
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取两个时间戳之间差值,并且返回小时
	 */
	public static int subTimeStamp2Hour(Timestamp one, Timestamp two) {
		int minute = subTimeStamp2Minute(one, two);
		return minute / 60;
	}

	/**
	 * 获取两个时间戳之间差值,并且返回小时
	 */
	public static int subTimeStamp2Day(Long one, Long two) {
		int day = (int) ((two - one)/(1000*60*60*24));
		return day;
	}

	/**
	 * 获取两个时间戳之间差值,并且返回分钟
	 */
	public static int subTimeStamp2Minute(Timestamp one, Timestamp two) {
		return (int) (two.getTime() - one.getTime()) / (1000 * 60);
	}

	/**
	 * 获取两个时间戳之间差值,并且返回秒
	 */
	public static int subTimeStamp2Second(Timestamp one, Timestamp two) {
		int minute = subTimeStamp2Minute(one, two);
		return minute * 60;
	}

	/**
	 * Date转Timestamp
	 * 
	 * @param pubtime
	 * @return
	 */
	public static Timestamp string2Timestamp(String pubtime) {
		Timestamp timestamp = null;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date = null;
		try {
			date = df.parse(pubtime.replace("Z", ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (null != date) {
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df1.format(date);
			timestamp = Timestamp.valueOf(time);
		}

		return timestamp;
	}

	/**
	 * 获取用于显示的时间
	 * 
	 * @param pubtime
	 * @return
	 */
	public static String getTime(String pubtime) {
		String displayTime = getFormatTime(pubtime);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		Timestamp pubTimestamp = DateUtils.string2Timestamp(pubtime);

		if (null != pubTimestamp) {
			int second = subTimeStamp2Second(pubTimestamp, current);
			if (second < 60 && second > 0) {
				displayTime = second + "秒前";
			} else {
				int minute = subTimeStamp2Minute(pubTimestamp, current);
				if (minute < 60 && minute > 0) {
					displayTime = minute + "分钟前";
				} else {
					int hour = subTimeStamp2Hour(pubTimestamp, current);

					if (hour < 24 && hour > 0) {
						displayTime = hour + "小时前";
					}
				}
			}
		}

		return displayTime;
	}

	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 日期
	 * @throws java.text.ParseException
	 */
	public static Date str2Date(String str, String format) {
		Date date = null;

		if (!TextUtils.isEmpty(str)) {
			// 如果没有指定字符串转换的格式，则用默认格式进行转换
			if (null == format || "".equals(format)) {
				format = DEFAULT_FORMAT;
			}

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	/**
	 * 时间字符串转换为指定格式
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String strToStrByFormat(String date, String format) {
		String dateString = "";
		if (null == format || "".equals(format)) {
			format = DEFAULT_FORMAT;
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date mDate = str2Date(date, format);
			dateString = formatter.format(mDate);
		} catch (Exception e) {
			e.getMessage();
		}
		return dateString;
	}

	/**
	 * 两个格林时间差
	 *
	 * @param date1
	 *            当前时间
	 * @param date2
	 *            过去时间
	 * @return
	 */
	public static String compressData(long date1, long date2) {
		String strDate = "";
		if (date1 == 0 || date2 == 0 || date2 == -1) {
			// strDate = String.valueOf(0)+" 秒钟前";
			strDate = "正在";
		} else {
			long cmDate = date1 - date2;
			long mDate = cmDate / 1000; // 将毫秒转为 秒
			if (mDate >= 60) {
				long minDate = mDate / 60;// 将秒转化为分钟
				if (minDate >= 60) {
					long hourDate = minDate / 60;// 将分钟转化为小时
					if (hourDate >= 24) {
						long dayDate = hourDate / 24;
						if (dayDate == 0) {
							dayDate = 1;
						}
						strDate = String.valueOf(dayDate) + " 天前";
					} else {
						if (hourDate == 0) {
							hourDate = 1;
						}
						strDate = String.valueOf(hourDate) + " 小时前";
					}
				} else {
					if (minDate == 0) {
						minDate = 1;
					}
					strDate = String.valueOf(minDate) + " 分钟前";
				}
			} else {
				if (mDate == 0) {
					mDate = 1;
				}
				strDate = String.valueOf(mDate) + " 秒钟前";
			}
		}
		if (strDate.startsWith("-")) {
			return UtilDateString.STRING_JUSTNOW;
		}
		return strDate;
	}

	/**
	 * 显示时间为 几秒前，几分钟前，几小时前，几天前，几月前，几年前的实现
	 *
	 * @param date
	 * @return
	 */
	private static final long ONE_MINUTE = 60000L;

	private static final long ONE_HOUR = 3600000L;

	private static final long ONE_DAY = 86400000L;

	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";

	private static final String ONE_MINUTE_AGO = "分钟前";

	private static final String ONE_HOUR_AGO = "小时前";

	private static final String ONE_DAY_AGO = "天前";

	private static final String ONE_MONTH_AGO = "月前";

	private static final String ONE_YEAR_AGO = "年前";

	public static String format(Date date) {
		String strDate;

		long delta = new Date().getTime() - date.getTime();

		if (delta < 1L * ONE_MINUTE) {

			long seconds = toSeconds(delta);

			strDate = (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;

		} else

		if (delta < 45L * ONE_MINUTE) {

			long minutes = toMinutes(delta);

			strDate = (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;

		} else

		if (delta < 24L * ONE_HOUR) {

			long hours = toHours(delta);

			strDate = (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;

		} else

		if (delta < 48L * ONE_HOUR) {

			strDate = "昨天";

		} else

		if (delta < 30L * ONE_DAY) {

			long days = toDays(delta);

			strDate = (days <= 0 ? 1 : days) + ONE_DAY_AGO;

		} else

		if (delta < 12L * 4L * ONE_WEEK) {

			long months = toMonths(delta);

			strDate = (months <= 0 ? 1 : months) + ONE_MONTH_AGO;

		} else {

			long years = toYears(delta);

			strDate = (years <= 0 ? 1 : years) + ONE_YEAR_AGO;

		}
		if (strDate.startsWith("-")) {
			return UtilDateString.STRING_JUSTNOW;
		}
		return strDate;
	}

	private static long toSeconds(long date) {

		return date / 1000L;

	}

	private static long toMinutes(long date) {

		return toSeconds(date) / 60L;

	}

	private static long toHours(long date) {

		return toMinutes(date) / 60L;

	}

	private static long toDays(long date) {

		return toHours(date) / 24L;

	}

	private static long toMonths(long date) {

		return toDays(date) / 30L;

	}

	private static long toYears(long date) {

		return toMonths(date) / 365L;

	}

	public static String transRelativeTime(String publishtimeStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		String tempTime = "";
		try {
			if (publishtimeStr.contains("T") && publishtimeStr.endsWith("Z")) {
				date = format.parse(publishtimeStr);
			} else {
				date = format2.parse(publishtimeStr);
			}
			tempTime = DateUtils.compressData(new Date().getTime(),
					date.getTime());
			if (tempTime != null && tempTime.contains("天前")) {
				int t = UtilInteger.parseInt(String.valueOf(
						tempTime.substring(0, tempTime.indexOf("天"))).trim());
				if (t > 7) {
					tempTime = getFormatTime3(publishtimeStr, "yyyy-MM-dd");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempTime;
	}

    /**
     * 获取72小时有效期的时间差
     * @param publishtimeStr
     * @return tempTime
     */
	public static String transRemainTime(String publishtimeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String tempTime = "";
        try {
            if (publishtimeStr.contains("T") && publishtimeStr.endsWith("Z")) {
                date = format.parse(publishtimeStr);
            } else {
                date = format2.parse(publishtimeStr);
            }
            tempTime = DateUtils.compressRemainData(new Date().getTime(), date.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return tempTime;
	}
	/**
	 * long类型时间的转换
	 * @return tempTime
	 */
	public static String transRemainTimeByLong(String remainLong) {
        if(remainLong == null){
            return "";
        }
		long remain = Long.parseLong(remainLong);
		if (remain <= 0) {
			return "已结束";
		}
		String tempTime = "";
		long mDate =  remain / 60 / 60 ; // 将秒转为小时
		if (mDate >= 24) {
			long dayDate = mDate / 24;
			if (dayDate == 0) {
				dayDate = 1;
			}
			tempTime ="距离结束还有"+ String.valueOf(dayDate) + "天";

		}else {

			tempTime = "距离结束还有"+ String.valueOf(mDate) + "小时";
		}
		return tempTime;

	}

    /**
     * 两个格林时间差
     *
     * @param date1
     *            当前时间
     * @param date2
     *            过去时间
     * @return
     */
    public static String compressRemainData(long date1, long date2) {
        String strDate = "";
            long cmDate = (72 * 60 * 60 * 1000) + date2 - date1 ;
            long mDate = cmDate / 1000 / 60 / 60 ; // 将毫秒转为小时
            if (mDate >= 24) {
                long dayDate = mDate / 24;
                if (dayDate == 0) {
                    dayDate = 1;
                }
                strDate ="距离结束还有"+ String.valueOf(dayDate) + "天";

            }else {

                strDate = "距离结束还有"+ String.valueOf(mDate) + "小时";
            }
        if (cmDate <= 0) {
            return "已结束";
        }
        return strDate;
    }

	public static String transLongToString(long time, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		// 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		java.util.Date dt = new Date(time);
		String sDateTime = sdf.format(dt); // 得到精确到秒的表示：08/31/2006 21:08:00
		return sDateTime;
	}

	/**
	 * 根据转换类型获取时间显示
	 *
	 * @param timeStamp
	 * @param format
	 * @return
	 */
	public static String getPaserTime(String timeStamp, String format) {
		if (TextUtils.isEmpty(timeStamp)) {
			return timeStamp;
		}

		try {
			if ((timeStamp).length() == 10) {//如果时间戳只有10位 补上后面三位
				timeStamp = timeStamp + "000";
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			long t = Long.valueOf(timeStamp);
			if (t == 0) {
				t = System.currentTimeMillis();
			}

			Date parserTime = new Date(t);// 获取当前时间
			return dateFormat.format(parserTime);
		} catch (Exception e) {
			return timeStamp;
		}
	}

	/**
	 * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
	 *
	 * @param time
	 * @return
	 */
	public static long dataOne(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		Date date;
		long times = 0;
		try {
			date = sdr.parse(time);
			long l = date.getTime();
			times=l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return times;
	}

    /**
     *
     * @param date1
     *            当前时间
     * @param date2
     *            过去时间
     * @return
     */
    public static String getCustomDateFormat(long date1, long date2) {
        String strDate = "";
        if (date1 == 0 || date2 == 0 || date2 == -1) {
            // strDate = String.valueOf(0)+" 秒钟前";
            strDate = "正在";
        } else {
            long cmDate = date1 - date2;
            long mDate = cmDate / 1000; // 将毫秒转为 秒
            if (mDate >= 60) {
                long minDate = mDate / 60;// 将秒转化为分钟
                if (minDate >= 60) {
                    long hourDate = minDate / 60;// 将分钟转化为小时
                    if (hourDate >= 24) {
                        String currentYear=transLongToString(date1,"yyyy");
						String date=transLongToString(date2,"yyyy年MM月dd日");
						if(date.startsWith(currentYear)){
							strDate=transLongToString(date2,"MM月dd日");
						}else {
							strDate=date;
						}
                    } else {
                        if (hourDate == 0) {
                            hourDate = 1;
                        }
                        strDate = String.valueOf(hourDate) + " 小时前";
                    }
                } else {
                    if (minDate == 0) {
                        minDate = 1;
                    }
                    strDate = String.valueOf(minDate) + " 分钟前";
                }
            } else {
                if (mDate == 0) {
                    mDate = 1;
                }
                strDate = "刚刚";
            }
        }
        if (strDate.startsWith("-")) {
            return UtilDateString.STRING_JUSTNOW;
        }
        return strDate;
    }

	/**
	 *
	 * @param date1
	 *            当前时间
	 * @param date2
	 *            过去时间
	 * @return
	 */
	public static String getCustomDateFormat2(long date1, long date2) {
		String strDate = "";
		if (date1 == 0 || date2 == 0 || date2 == -1) {
			// strDate = String.valueOf(0)+" 秒钟前";
			strDate = "正在";
		} else {
			long cmDate = date1 - date2;
			long mDate = cmDate / 1000; // 将毫秒转为 秒
			if (mDate >= 60) {
				long minDate = mDate / 60;// 将秒转化为分钟
				if (minDate >= 60) {
					long hourDate = minDate / 60;// 将分钟转化为小时
					if (hourDate >= 24) {
						if (hourDate > 24*3) {
							String date = transLongToString(date2,"yyyy-MM-dd");
							strDate = date;
						} else {
							strDate = String.valueOf(hourDate/24) + " 天前";
						}
					} else {
						if (hourDate == 0) {
							hourDate = 1;
						}
						strDate = String.valueOf(hourDate) + " 小时前";
					}
				} else {
					if (minDate == 0) {
						minDate = 1;
					}
					strDate = String.valueOf(minDate) + " 分钟前";
				}
			} else {
				if (mDate == 0) {
					mDate = 1;
				}
				strDate = "刚刚";
			}
		}
		if (strDate.startsWith("-")) {
			return UtilDateString.STRING_JUSTNOW;
		}
		return strDate;
	}

	/**
	 * 获取当天日期
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String dataCurrentDay(long timeMillis) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
		Date date = new Date(timeMillis);
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取当天的具体日期
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String dataCurrentDate(long timeMillis) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(timeMillis);
		return simpleDateFormat.format(date);
	}

}
