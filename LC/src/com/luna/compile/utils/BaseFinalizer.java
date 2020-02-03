package com.luna.compile.utils;

import com.luna.base.result.Bean;

abstract class BaseFinalizer {

    static <T> Bean<T> build(boolean success, String message, T t) {
        return new Bean<T>().setSuccess(success).setMessage(message).setData(t);
    }

    static <T> Bean<T> build(boolean success, String message) {
        return new Bean<T>().setSuccess(success).setMessage(message);
    }

    static <T> Bean<T> build(boolean success) {
        return new Bean<T>().setSuccess(success);
    }

    static <T> Bean<T> build(int code, String message, T t) {
        return new Bean<T>().setCode(code).setMessage(message).setData(t);
    }

    static <T> Bean<T> build(int code, String message) {
        return new Bean<T>().setCode(code).setMessage(message);
    }

    static <T> Bean<T> build(int code) {
        return new Bean<T>().setCode(code);
    }

}
