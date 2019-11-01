package com.luna.compile.compiler.constant;

public enum Keywords {

    IF("if"),
    ELSE("else"),
    NAIVE("naive"),
    END("end"),
    LINK("link"),
    THIS("this"),
    DO("do");

    private String value;

    Keywords(String c) {
        this.value = c;
    }

    public String getValue() {
        return value;
    }

    public static Keywords getKeyword(String c) {
        for(Keywords op : values()) {
            if(op.getValue().equals(c)) return op;
        }
        return null;
    }

    public static boolean isKeyword(String c) {
        return getKeyword(c) != null;
    }

}
