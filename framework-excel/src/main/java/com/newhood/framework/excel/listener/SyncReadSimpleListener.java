package com.newhood.framework.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.newhood.framework.excel.dto.SimpleExcelImportDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Description 简单的表头数据导入操作.
 * 解析有对象xls,Or,xlsx文件.
 *
 * @author ZJW
 * @since 2020/09/22 9:40
 **/
@Slf4j
public class SyncReadSimpleListener extends AnalysisEventListener<Map<Integer, Object>> {

    /**
     * <p>
     *     简单导入的DTO对象.
     * </p>
     */
    private final SimpleExcelImportDTO simpleExcelImportDTO;

    public SimpleExcelImportDTO getSimpleExcelImportDTO() {
        return simpleExcelImportDTO;
    }

    /**
     * Description 初始化.
     *
     * @author ZJW
     * @since 2020/09/22 11:40
     **/
    public SyncReadSimpleListener() {
        log.info("SimpleExcelImportDTO 初始化");
        simpleExcelImportDTO = new SimpleExcelImportDTO();
        simpleExcelImportDTO.setHeadMap(new HashMap<>());
        simpleExcelImportDTO.setValusList(new ArrayList<>());
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        log.info("导入的表头数据 headMap=[{}]", headMap);
        simpleExcelImportDTO.setHeadMap(headMap);
    }

    @Override
    public void invoke(Map<Integer, Object> data, AnalysisContext context) {
        log.info("导入的列数据 headMap=[{}]", data);
        simpleExcelImportDTO.getValusList().add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("导入数据解析完毕 ListSize=[{}]", simpleExcelImportDTO.getValusList().size());
    }

}
