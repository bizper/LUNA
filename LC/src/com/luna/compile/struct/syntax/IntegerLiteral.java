package com.luna.compile.struct.syntax;

import com.luna.compile.struct.Token;

public class IntegerLiteral extends ASTLeaf {

    public IntegerLiteral(Token token) {
        super(token);
    }

    public int value() {
        return token.getInt();
    }

}
