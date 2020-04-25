package com.github.stark4j.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.stark4j.beans.vo.PageSearchParam;
import com.github.stark4j.beans.vo.PageSearchResult;
import com.github.stark4j.core.utils.StreamUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Allen Created 2020/4/19
 */
public interface PageSearchService {

    /**
     * 创建分页查询请求参数
     *
     * @param request 分页查询请求
     * @param <T>     具体的参数泛型
     * @return 返回分页信息
     */
    default <T> Page<T> createPage(PageSearchParam<?> request) {
        Page<T> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(request.isCount());
        return page;
    }

    /**
     * 创建分页查询请求参数
     *
     * @param request    分页查询请求
     * @param orderItems 分页信息
     * @param <T>        实体的泛型
     * @return 返回分页信息
     */
    default <T> Page<T> createPage(PageSearchParam<?> request, OrderItem... orderItems) {
        Page<T> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(request.isCount());
        page.addOrder(orderItems);
        return page;
    }

    /**
     * 执行分页查询
     *
     * @param page     分页信息
     * @param function 从持久层查询的函数
     * @param <T>      请求泛型
     * @param <R>      响应泛型
     * @return 返回分页查询结果
     */
    default <T, R> PageSearchResult<R> searchResult(IPage<T> page, Function<T, R> function) {
        PageSearchResult<R> result = new PageSearchResult<>();
        result.setTotal(page.getTotal());
        List<R> data = Optional.ofNullable(page.getRecords())
                .map(var -> StreamUtils.parallelTransformToList(var, function))
                .orElse(null);
        result.setSearchResult(data);
        return result;
    }

}
