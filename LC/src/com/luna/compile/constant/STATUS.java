package com.luna.compile.constant;

public class STATUS {
    /**
     * 通用成功码
     */
    public static final int OK = 0;
    /**
     * 通用错误码
     */
    public static final int ERROR = 100;
    /**
     * 文件不合法，不为合法的LUNA后缀文件
     */
    public static final int FILE_NOT_VALID = 101;
    /**
     * 编译器没有找到对应的文件
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
