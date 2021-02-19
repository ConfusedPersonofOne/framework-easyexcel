package com.newhood.framework.excel.config;

/**
 * Description ExcelFileConstants.
 *
 * @author by ZJW
 * @since 2020/10/13 10:31
 */
public class ExcelFileConfig {
    public static final String EXCEL2003 = "xls";
    public static final String EXCEL2007 = "xlsx";

    public static void endsWith(String fileName) throws RuntimeException {
        if (fileName != null &&
                (fileName.endsWith(ExcelFileConfig.EXCEL2003)
                        || fileName.endsWith(ExcelFileConfig.EXCEL2007))) {
            return;
        }

        throw new RuntimeException("当前文件不支持解析!fileName=" + fileName);
    }

}
