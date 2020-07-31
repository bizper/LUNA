package com.luna.compile.compiler.constant;

public enum Keywords implements SIG {

    IF("if", 0),
    ELSE("else", 0),
    NAIVE("naive", 0),
    END("end", 0),
    LINK("link", 0),
    THIS("this", 0),
    DO("do", 0);

    private final String value;

    private final int level;

    Keywords(String c, int level) {
        this.value = c;
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
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
