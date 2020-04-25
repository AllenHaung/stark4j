package com.github.stark4j.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.beans.vo.PageSearchParam;
import com.github.stark4j.beans.vo.PageSearchResult;
import com.github.stark4j.core.exception.Stark4jException;
import lombok.Getter;

import java.util.function.Function;

@Getter
public final class PageSearchServices<T, R> implements PageSearchService {
    /**
     * Page对象
     */
    private Page<T> page;
    /**
     * 查询的方程
     */
    private Function<Page<T>, IPage<T>> searchFunction;
    /**
     * 做DTO转换的方程
     */
    private Function<T, R> transformFunction = var -> (R) var;

    public PageSearchServices<T, R> page(PageSearchParam<?> request, OrderItem... orderItems) {
        this.page = createPage(request, orderItems);
        return this;
    }

    public PageSearchServices<T, R> query(Function<Page<T>, IPage<T>> searchFunction) {
        this.searchFunction = searchFunction;
        return this;
    }

    public PageSearchServices<T, R> transform(Function<T, R> transformFunction) {
        if (transformFunction != null) {
            this.transformFunction = transformFunction;
        }
        return this;
    }

    public PageSearchResult<R> search() {
        if (page == null) {
            throw Stark4jException.create(Stark4jCode.NOT_ALLOW, "please init page data");
        }
        return searchResult(searchFunction.apply(page), transformFunction);
    }
}
