package com.luna.compile.utils;

import com.luna.compile.struct.Token;

import java.util.List;
import java.util.Map;

public class ExpressionFinalizer {

    private static final char operators[] = {'+', '-', '*', '/', '(', ')'};

    public static Token derive(List<Token> list) {
        return derive(list.toArray(new Token[]{}));
    }

    public static Token derive(Token... tokens) {
        return null;
    }

    public static Token deriveWithMapping(Map<String, Object> map, Token... tokens) {
        return null;
    }

}
