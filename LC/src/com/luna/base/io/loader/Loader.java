package com.luna.base.io.loader;

import com.luna.base.result.Bean;
import com.luna.compile.constant.STATUS;

import java.io.File;

import static com.luna.compile.constant.STATUS.*;

public abstract class Loader {

    public abstract Bean<File> load(String path, Filter<String, Boolean> filter);

    public static Loader get() {
        return new FileLoader();
    }

    <T> Bean<T> build(boolean success, String message, T t) {
        return new Bean<T>().setSuccess(success).setCode(success ? OK : ERROR).setMessage(message).setData(t);
    }

    <T> Bean<T> build(boolean success, String message) {
        return build(success, message, null);
    }

    <T> Bean<T> build(boolean success) {
        return build(success, null, null);
    }

    static <T> Bean<T> build(int code, String message, T t) {
        return new Bean<T>().setSuccess(code == STATUS.OK).setCode(code).setMessage(message).setData(t);
    }

    static <T> Bean<T> build(int code, String message) {
        return build(code, message, null);
    }

    static <T> Bean<T> build(int code) {
        return build(code, null, null);
    }

}
