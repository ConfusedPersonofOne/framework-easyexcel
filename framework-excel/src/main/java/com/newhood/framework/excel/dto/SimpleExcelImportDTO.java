package com.newhood.framework.excel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * <p>
 *     简单的导入数据DTO
 * </p>
 * @author ZJW
 * @since 2021/1/16
 */
@Data
@ApiModel("简单的导入数据元数据")
public class SimpleExcelImportDTO {

    @ApiModelProperty("导入表头")
    private Map<Integer, String> headMap;

    @ApiModelProperty("导入数据")
    private List<Map<Integer, Object>> valusList;

}
