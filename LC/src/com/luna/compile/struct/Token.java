package com.luna.compile.struct;

import com.luna.compile.constant.TOKEN;

public class Token {

    private Token() { }

    private int line;

    private int col;

    @Override
    public String toString() {
        return "Token [" +
                "line=" + line +
                ", col=" + col +
                ", type=" + type +
                ", value='" + value + '\'' +
                ']';
    }

    private TOKEN type;

    private String value;

    public Token setLine(int line) {
        this.line = line;
        return this;
    }

    public Token setCol(int col) {
        this.col = col;
        return this;
    }

    public Token setType(TOKEN type) {
        this.type = type;
        return this;
    }

    public Token setValue(String value) {
        this.value = value;
        return this;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public TOKEN getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public static Token get(int line, int col, TOKEN type, String value) {
        return new Token().setLine(line).setCol(col).setType(type).setValue(value);
    }

    public static Token get(int line, int col, TOKEN type, char value) {
        return get(line, col, type, String.valueOf(value));
    }
}
