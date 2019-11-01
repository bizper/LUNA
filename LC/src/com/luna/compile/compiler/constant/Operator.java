package com.luna.compile.compiler.constant;

public enum Operator {

    EQUAL('='),
    PLUS('+'),
    MINUS('-'),
    MULTI('*'),
    DIV('/'),
    NOT('!'),
    LT('<'),
    GT('>'),
    LP('('),
    RP(')'),
    QM('?'),
    COLON(':'),
    DEFINE('#');

    private char value;

    Operator(char c) {
        this.value = c;
    }

    public char getValue() {
        return value;
    }

    public static Operator getOperator(char c) {
        for(Operator op : values()) {
            if(op.getValue() == c) return op;
        }
        return null;
    }

    public static Operator getOperator(String c) {
        if(c == null) return null;
        if(c.length() > 1) return null;
        for(Operator op : values()) {
            if(op.getValue() == c.charAt(0)) return op;
        }
        return null;
    }

    public static boolean isOperator(char c) {
        return getOperator(c) != null;
    }

}
