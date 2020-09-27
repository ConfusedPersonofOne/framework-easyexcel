package com.newhood.framework.excel.importer;

/**
 * Description 保存对外接口.
 *
 * @author ZJW
 * @date 2020/09/23 12:48
 **/
public interface ISave<T> {

    void saveLine(T dto);

}