package com.luna.core.env;

public class Stack<T> {

    private Stack<T> stack = new Stack<>();

    public void push(T t) {
        stack.push(t);
    }

    public T pop() {
        return stack.pop();
    }

    public T get() {
        return stack.get();
    }

    public void clear() {
        stack.clear();
    }

}
