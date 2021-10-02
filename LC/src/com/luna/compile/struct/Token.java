package com.luna.compile.struct;

import com.luna.compile.compiler.constant.Keywords;
import com.luna.compile.compiler.constant.SIG;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.intf.StringElement;

import java.util.StringJoiner;

public class Token implements StringElement {

    private Token() { }

    private Integer line;

    private Integer col;

    private TOKEN type;

    private SIG sig;

    private String value;

    @Override
    public String toString() {
        return new StringJoiner(", ", Token.class.getSimpleName() + " [", "]")
                .add("line=" + line)
                .add("col=" + col)
                .add("type=" + type)
                .add("sig=" + sig)
                .add("value='" + value + "'")
                .toString();
    }

    public Token setSig(SIG sig) {
        this.sig = sig;
        return this;
    }

    public SIG getSig() {
        return sig;
    }

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
        if(Keywords.isKeyword(value)) setSig(Keywords.getKeyword(value));
        return this;
    }

    public int getInt() {
        if(type != TOKEN.INTEGER) throw new TokenException("ERROR TYPE", this);
        return Integer.parseInt(value);
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

    public static Token get(int line, int col, TOKEN type, String value, SIG sig) {
        return new Token().setLine(line).setCol(col).setType(type).setValue(value).setSig(sig);
    }

    public static Token get(int line, int col, TOKEN type, char value, SIG sig) {
        return get(line, col, type, String.valueOf(value), sig);
    }

    public boolean check(TOKEN type, String value) {
        return this.getType() == type && this.getValue().equals(value);
    }

    public boolean check(TOKEN type) {
        return this.getType() == type;
    }

    public boolean check(TOKEN token, SIG sig) {
        return this.getType() == token && this.getSig() == sig;
    }

    @Override
    public String getElement() {
        return value;
    }
}
