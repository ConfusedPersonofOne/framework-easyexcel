package com.newhood.framework.excel.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description 自定义string转换为localTime类.
 *
 * @author ZJW
 * @since 2020/09/23 14:04
 **/
@Slf4j
public class LocalDateTimeUtils {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>
     * 解析时间.
     * </p>
     *
     * @param dateString 时间类型数据
     * @return java.time.LocalDateTime
     * @author ZJW
     * @date 2020-11-08 16:29
     **/
    public static LocalDateTime parse(String dateString) {
        try {
            //使用正则表达式，将非数字所有符号替换为"-"，并替换掉可能出现"--"的情况
            String time = dateString.trim().replaceAll("\\D", "-").replace("--", "-");
            //log.info("转换数据 replaceAll = " + time);
            String[] split = time.split("-");
            //log.info("转换数据长度" + split.length);
            String yyyy = String.format("%02d", Integer.valueOf(split[0]));
            String MM = String.format("%02d", Integer.valueOf(split[1]));
            String dd = String.format("%02d", Integer.valueOf(split[2]));
            String HH = "00";
            if (split.length > 3) {
                HH = String.format("%02d", Integer.valueOf(split[3]));
            }
            String mm = "00";
            if (split.length > 4) {
                mm = String.format("%02d", Integer.valueOf(split[4]));
            }
            String ss = "00";
            if (split.length > 5) {
                ss = String.format("%02d", Integer.valueOf(split[5]));
            }
            //log.info("格式化内容：" + yyyy + "年" + MM + "月" + dd + "日" + HH + "时" + mm + "分" + ss + "秒");
            return LocalDateTime.parse(yyyy + "-" + MM + "-" + dd + " " + HH + ":" + mm + ":" + ss, dateTimeFormatter);
        } catch (Exception e) {
            log.info(" string 转 localDateTime异常 ，string=" + dateString);
        }
        return null;
    }

}
