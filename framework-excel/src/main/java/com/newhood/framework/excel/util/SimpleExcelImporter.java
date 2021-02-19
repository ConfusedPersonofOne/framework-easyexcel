package com.newhood.framework.excel.util;

import com.alibaba.excel.EasyExcelFactory;
import com.newhood.framework.excel.dto.SimpleExcelImportDTO;
import com.newhood.framework.excel.listener.SyncReadSimpleListener;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 无验证机制数据导入处理.
 * </p>
 *
 * @author ZJW
 * @since 2020/1/5 10:46
 */
@Slf4j
public class SimpleExcelImporter {

    /**
     * Description 执行excel导入操作.
     *
     * @author ZJW
     * @since 2020/09/22 11:27
     **/
    public static SimpleExcelImportDTO importExcel(InputStream ins) throws RuntimeException {
        SyncReadSimpleListener listener = new SyncReadSimpleListener();
        try {
            EasyExcelFactory.read(ins, listener).autoTrim(true).sheet(0).doReadSync();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                ins.close();
            } catch (IOException e) {
                log.error("流关闭异常" + e.getMessage());
            }
        }
        return listener.getSimpleExcelImportDTO();
    }

}
