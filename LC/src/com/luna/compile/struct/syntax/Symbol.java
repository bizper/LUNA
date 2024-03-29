package com.luna.compile.struct.syntax;

import com.luna.compile.struct.Token;

import java.util.Iterator;

public class Symbol extends ASTLeaf {

    public Symbol(Token token) {
        super(token);
    }

    public String value() {
        return token.getValue();
    }

    @Override
    public Iterator<ASTree> iterator() {
        return super.iterator();
    }
}
