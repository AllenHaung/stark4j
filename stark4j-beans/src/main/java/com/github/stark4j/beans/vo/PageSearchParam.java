package com.github.stark4j.beans.vo;

import com.github.stark4j.beans.Stark4jVersion;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页查询请求参数
 *
 * @author Allen Created 2020/3/30
 */
@Data
public class PageSearchParam<T> {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;
    /**
     * 当前页
     */
    @Min(value = 1, message = "page number can not less than 1")
    private int pageNum = 1;
    /**
     * 做了最大数量的限制为了防止系统崩溃
     */
    @Min(value = 1, message = "page size can not less than 1")
    @Max(value = 2048, message = "page size can not more than 1")
    private int pageSize = 10;
    /**
     * 是否查询总记录数。默认不查询总记录数，这样可以提高系统的性能
     */
    private boolean count;
    /**
     * 查询请求参数
     */
    @Valid
    @NotNull(message = "searchData can not be null.")
    private T searchData;
}
