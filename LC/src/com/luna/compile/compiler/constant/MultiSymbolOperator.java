package com.luna.compile.compiler.constant;

public enum MultiSymbolOperator implements SIG {

    COMMENT("//"),
    DOUBLE_PLUS("++"),
    NOT_EQUALS("!="),
    DOUBLE_MINUS("--"),
    LEFT_ARROW("<-");

    private String value;

    MultiSymbolOperator(String value) {
        this.value = value;
    }

    public static MultiSymbolOperator getMultiSymbolOperator(String value) {
        for(MultiSymbolOperator op : values()) {
            if(op.getValue().equals(value)) return op;
        }
        return null;
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
