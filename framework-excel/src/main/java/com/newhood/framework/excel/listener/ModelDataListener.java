package com.newhood.framework.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.newhood.framework.core.exception.BaseException;
import com.newhood.framework.excel.importer.ISave;
import java.util.HashMap;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description 自定义AnalysisEventListener的公共数据以及函数.
 * 解析有对象xls,Or,xlsx文件.
 *
 * @author ZJW
 * @date 2020/09/22 9:40
 **/
@Slf4j
public class ModelDataListener<T> extends AnalysisEventListener<T> {

    /**
     * Description 保存对象.
     *
     * @author ZJW
     * @date 2020/09/23 12:42
     **/
    @Setter
    protected ISave<T> save;

    /**
     * Description 表头数据保存list.
     *
     * @author ZJW
     * @date 2020/09/21 18:52
     **/
    protected Map<Integer, String> headerColumns;

    /**
     * Description 初始化.
     *
     * @author ZJW
     * @date 2020/09/22 11:40
     **/
    public ModelDataListener() {
        log.info("ModelDataListener 初始化");
        headerColumns = new HashMap<>();
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        log.info("AbstractNoModelDataListener 读取表头数据");
        if (headerColumns.size() != 0) {
            throw new BaseException("对应xls,Or,xlsx文件存在表头问题");
        } else {
            headerColumns = headMap;
        }
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("ModelDataListener 读取到了一条数据");
        save.saveLine(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("ModelDataListener 所有数据解析完成！");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
    }
}
