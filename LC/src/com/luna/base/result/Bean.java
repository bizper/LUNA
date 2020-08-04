package com.luna.base.result;

public class Bean<T> {

    private int code;
    private boolean success;
    private String message;
    private T data;

    @Override
    public String toString() {
        return "Bean [" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ']';
    }

    public Bean<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Bean<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Bean<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Bean<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
