package com.luna.compile.struct.syntax;

import com.luna.compile.struct.Token;

import java.util.ArrayList;
import java.util.Iterator;

public class ASTLeaf implements ASTree {

    private final ArrayList<ASTree> empty = new ArrayList<>();

    protected Token token;

    public ASTLeaf(Token token) {
        this.token = token;
    }

    @Override
    public ASTree child(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Iterator<ASTree> children() {
        return empty.iterator();
    }

    @Override
    public String location() {
        return "at line: " + token.getLine();
    }

    public Token token() {
        return token;
    }

}
