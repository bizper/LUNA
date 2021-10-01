package com.luna.compile.struct;

import com.luna.compile.struct.syntax.ASTree;

public class TokenException extends RuntimeException {

    public TokenException(String m) {
        super(m);
    }

    public TokenException(String m, ASTree t) {
        super(m + "/" + t.location());
    }

    public TokenException(String m, Token t) {
        super(m + "/" + t.getValue());
    }

}
