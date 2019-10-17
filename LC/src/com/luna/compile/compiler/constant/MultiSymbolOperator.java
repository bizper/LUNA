package com.luna.compile.compiler.constant;

public enum MultiSymbolOperator {

    COMMENT("//"),
    SELF_PLUS("++"),
    NOT_EQUALS("!="),
    SELF_MINUS("--");

    private String value;

    MultiSymbolOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String[] toArray() {
        MultiSymbolOperator[] mso = values();
        String[] arr = new String[mso.length];
        int index = 0;
        for(MultiSymbolOperator m : mso) {
            arr[index++] = m.getValue();
        }
        return arr;
    }
}