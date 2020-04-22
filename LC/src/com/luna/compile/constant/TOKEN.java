package com.luna.compile.constant;

public enum TOKEN {

    KEYWORD("关键字"),//关键字
    SYMBOL("符号"),//符号
    STRING("字符常量"),//字面常量会被设置为此类型
    INTEGER("整型数字常量"),
    FLOAT("浮点型数字常量"),//常量，对于数字会被设置为此类型
    OPERATOR("操作符"),//操作符
    BOOLEAN("布尔值"),
    EXPRESSION("表达式");

    private String desc;

    private TOKEN(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
