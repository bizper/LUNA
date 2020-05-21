package com.luna.compile.struct;

import com.luna.compile.constant.TOKEN;

public class TokenRepresent {

    private Token represent;

    private TOKEN type;

    @Override
    public String toString() {
        return "TokenRepresent [" +
                "represent=" + represent +
                ", type=" + type +
                ']';
    }

    public TokenRepresent setRepresent(Token represent) {
        this.represent = represent;
        return this;
    }

    public TokenRepresent setType(TOKEN type) {
        this.type = type;
        return this;
    }

    public Token getRepresent() {
        return represent;
    }

    public TOKEN getType() {
        return type;
    }

    private TokenRepresent() {}

    public static TokenRepresent get(Token represent, TOKEN type) {
        return new TokenRepresent().setRepresent(represent).setType(type);
    }

    public static TokenRepresent get(Token represent) {
        return new TokenRepresent().setRepresent(represent);
    }

    public static TokenRepresent get(TOKEN type) {
        return new TokenRepresent().setType(type);
    }
}
