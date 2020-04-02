package com.github.stark4j.core.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Allen Created 2020/3/30
 */
@FunctionalInterface
public interface LambdaFunction <T, R> extends Function<T, R>, Serializable {
}
