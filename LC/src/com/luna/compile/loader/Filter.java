package com.luna.compile.loader;

@FunctionalInterface
public interface Filter<T, E> {

    E check(T t);

}
