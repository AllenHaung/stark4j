package com.github.stark4j.beans.vo;

import com.github.stark4j.beans.Stark4jVersion;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Allen Created 2020/3/30
 */
@Data
public class PageSearchResult <T> implements Serializable {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;
    /**
     * 总记录数，只有在分页查询请求参数count为true是才返回
     */
    private Long total;
    /**
     * 分页查询结果
     */
    private Collection<T> searchResult;

    public static <T> PageSearchResult<T> create(Collection<T> data, Long total) {
        PageSearchResult<T> result = new PageSearchResult<>();
        result.setTotal(total);
        result.setSearchResult(data);
        return result;
    }

    public static <T> PageSearchResult<T> emptyOfNull() {
        PageSearchResult<T> result = new PageSearchResult<>();
        result.setTotal(0L);
        return result;
    }

    public static <T> PageSearchResult<T> emptyOfArray() {
        PageSearchResult<T> result = new PageSearchResult<>();
        result.setTotal(0L);
        result.setSearchResult(new ArrayList<>(0));
        return result;
    }
}
