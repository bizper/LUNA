package com.luna.compile.constant;

public class STATUS {
    /**
     * typically success code
     */
    public static final int OK = 0;
    /**
     * typically error code
     */
    public static final int ERROR = 100;
    /**
     * illegal luna file
     */
    public static final int FILE_NOT_VALID = 101;
    /**
     * can not find the file described in path
     */
    public static final int FILE_NOT_FOUND = 102;
    /**
     * 检查语法错误时返回此错误码，此为编译器静态检查语法出错。
     */
    public static final int TOKEN_SYNTAX_ERROR = 201;
    /**
     * 检查类型错误时返回此错误码，此为编译器静态检查类型出错。
     */
    public static final int TOKEN_TYPE_ERROR = 202;

}
