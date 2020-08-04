package com.luna.compile.compiler.constant;

public enum MultiSymbolOperator implements SIG {

    COMMENT("//", 0),
    DOUBLE_PLUS("++", 0),
    NOT_EQUALS("!=", 0),
    EQUALS("==", 0),
    DOUBLE_MINUS("--", 0),
    LEFT_ARROW("<-", 0);

    private final String value;

    private final int level;

    MultiSymbolOperator(String value, int level) {
        this.value = value;
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
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
