package com.newhood.framework.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * <p>
 * 简单的导出excel文件.
 * </p>
 *
 * @author ZJW
 * @since 2020/11/6 10:06
 */
public class SimpleExcelExport {

    private final static Integer sheetSize = 10000;

    public static List<List<Object>> valueType(List<List<Object>> dataList) {
        List<List<Object>> newDataList = new ArrayList<>();
        if (dataList != null && dataList.size() != 0) {
            for (List<Object> list : dataList) {
                List<Object> newList = new ArrayList<>();
                for (Object object : list) {
                    // 对象转换
                    if (object != null && (!(object instanceof Long) && !(object instanceof Short) && !(object instanceof Integer) &&
                            !(object instanceof Character) && !(object instanceof String) && !(object instanceof Double)
                            && !(object instanceof Float) && !(object instanceof Byte) && !(object instanceof Boolean))) {
                        // 时间类型处理
                        if (!(object instanceof LocalDateTime) && !(object instanceof java.util.Date) && !(object instanceof java.sql.Date)) {
                            // 其余的数据类型统一保存string的信息[防止数据类型没有对应的解析对象,ObjectMapper进行object处理]
                            object = object.toString();
                        }
                    }
                    newList.add(object);
                }
                newDataList.add(newList);
            }
        }
        return newDataList;
    }

    public static void autoSheet(ExcelTypeEnum excelTypeEnum, List<List<String>> head, List<List<Object>> dataList, OutputStream outputStream) {
        // 先将数据处理一下
        dataList = valueType(dataList);
        // 数据导出
        ExcelWriterBuilder write = EasyExcel.write(outputStream);
        // 样式定制以及自适应定制
        write.registerWriteHandler(getStyleStrategy());
        write.registerWriteHandler(new CustemHandler());
        // LocalDateTime 解析对象
        write.registerConverter(new LocalDateTimeConverter());
        // TimeStamp 解析对象
        write.registerConverter(new TimestampConverter());

        write.head(head);
        write.excelType(excelTypeEnum);
        ExcelWriter build = write.build();
        if (dataList != null && dataList.size() != 0) {
            int item = 1;
            while (dataList.size() > sheetSize) {
                List<List<Object>> data = dataList.subList(0, sheetSize);
                dataList = dataList.subList(sheetSize, dataList.size());
                // sheet写入
                WriteSheet writeSheet = new WriteSheet();
                writeSheet.setSheetName("sheet" + item);
                writeSheet.setSheetNo(item++);
                build.write(data, writeSheet);
            }
            // 不足sheetSize的处理方式
            if (dataList.size() != 0) {
                List<List<Object>> data = dataList.subList(0, dataList.size());
                // sheet写入
                WriteSheet writeSheet = new WriteSheet();
                writeSheet.setSheetName("sheet" + item);
                writeSheet.setSheetNo(item);
                build.write(data, writeSheet);
            }
        }
        // 刷新缓存
        build.finish();
    }

    public static void autoSheet(List<List<String>> head, List<List<Object>> dataList, OutputStream outputStream) {
        autoSheet(ExcelTypeEnum.XLSX, head, dataList, outputStream);
    }

    /**
     * 设置生成excel样式 去除默认表头样式及设置内容居中，如有必要可重载该方法给定参数配置不同样式
     *
     * @return HorizontalCellStyleStrategy
     */
    public static HorizontalCellStyleStrategy getStyleStrategy() {
        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(false);
        // 内容样式策略
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteFont.setFontName("Calibri");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //头样式策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

}
