package com.luna.compile.compiler.constant;

public enum Operator implements SIG {

    EQUAL('=', 0),
    PLUS('+', 0),
    MINUS('-', 0),
    MULTI('*', 1),
    DIV('/', 1),
    NOT('!', 0),
    LT('<', 0),
    GT('>', 0),
    LP('(', 2),
    RP(')', 2),
    QM('?', 0),
    COLON(':', 0),
    DEFINE('#', 0);

    private final char value;

    private final int level;

    Operator(char c, int level) {
        this.value = c;
        this.level = level;
    }

    public String getValue() {
        return String.valueOf(value);
    }

    public int getLevel() {
        return level;
    }

    public static Operator getOperator(char c) {
        for(Operator op : values()) {
            if(op.getValue().equals(String.valueOf(c))) return op;
        }
        return null;
    }

    public static Operator getOperator(String c) {
        if(c == null) return null;
        if(c.length() > 1) return null;
        for(Operator op : values()) {
            if(op.getValue().equals(c)) return op;
        }
        return null;
    }

    public static boolean isOperator(char c) {
        return getOperator(c) != null;
    }

}
