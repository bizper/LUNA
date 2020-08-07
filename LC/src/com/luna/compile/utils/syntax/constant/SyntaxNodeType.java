package com.luna.compile.utils.syntax.constant;

public enum SyntaxNodeType {

    TERMINAL,//终止符
    CONST,//静态值
    AND,//且，不需要明确指定，默认为此逻辑
    OR//或，需要明显指定，对应为 | 符号

}
