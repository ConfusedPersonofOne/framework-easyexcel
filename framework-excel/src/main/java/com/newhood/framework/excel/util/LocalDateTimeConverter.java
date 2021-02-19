package com.newhood.framework.excel.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    private static SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                           GlobalConfiguration globalConfiguration) {
        // 如果是 44095.975266203706(将保存在getNumberValue中)
        if (cellData.getStringValue() == null && cellData.getNumberValue() != null) {
            return LocalDateTime.parse(
                    getPOIDate(false, cellData.getNumberValue().doubleValue())
                    , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
        }
        // 如果是 2020/9/24(将保存在getStringValue中)
        if (cellData.getStringValue() != null) {
            return LocalDateTimeUtils.parse(cellData.getStringValue());
        }
        // 其余情况全部为空
        return null;
    }

    @Override
    public CellData<String> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new CellData<>(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * @param use1904windowing 是否按照1904开始计算
     * @param value            时间double
     */
    private String getPOIDate(boolean use1904windowing, double value) {
        int wholeDays = (int) Math.floor(value);
        int millisecondsInDay = (int) ((value - (double) wholeDays) * 8.64E7D + 0.5D);
        Calendar calendar = new GregorianCalendar();
        short startYear = 1900;
        byte dayAdjust = -1;
        if (use1904windowing) {
            startYear = 1904;
            dayAdjust = 1;
        } else if (wholeDays < 61) {
            dayAdjust = 0;
        }
        calendar.set(startYear, 0, wholeDays + dayAdjust, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, millisecondsInDay);
        if (calendar.get(Calendar.MILLISECOND) == 0) {
            calendar.clear(Calendar.MILLISECOND);
        }
        Date date = calendar.getTime();
        return s.format(date);
    }

}

