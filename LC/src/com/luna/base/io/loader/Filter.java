package com.luna.base.io.loader;

@FunctionalInterface
public interface Filter<T, E> {

    E check(T t);

}
