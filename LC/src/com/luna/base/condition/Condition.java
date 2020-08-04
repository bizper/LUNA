package com.luna.base.condition;

@FunctionalInterface
public interface Condition<T, E> {

    E accept(T t);

}
