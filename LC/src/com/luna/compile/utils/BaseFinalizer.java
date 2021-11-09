package com.luna.compile.utils;

import com.luna.base.result.Bean;

public abstract class BaseFinalizer {

    public static <T> Bean<T> build(boolean success, String message, T t) {
        return new Bean<T>().setSuccess(success).setMessage(message).setData(t);
    }

    public static <T> Bean<T> build(boolean success, String message) {
        return new Bean<T>().setSuccess(success).setMessage(message);
    }

    public static <T> Bean<T> build(boolean success, T t) {
        return new Bean<T>().setSuccess(success).setData(t);
    }

    public static <T> Bean<T> build(boolean success) {
        return new Bean<T>().setSuccess(success);
    }

    public static <T> Bean<T> build(int code, String message, T t) {
        return new Bean<T>().setCode(code).setMessage(message).setData(t);
    }

    public static <T> Bean<T> build(int code, String message) {
        return new Bean<T>().setCode(code).setMessage(message);
    }

    public static <T> Bean<T> build(int code) {
        return new Bean<T>().setCode(code);
    }

}
