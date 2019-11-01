package com.luna.compile.constant;

public enum TOKEN {

    KEYWORD,//关键字
    SYMBOL,//符号
    STRING,//字面常量会被设置为此类型
    NUMBER,//常量，对于数字会被设置为此类型
    OPERATOR,//操作符
    EXPRESSION

}
