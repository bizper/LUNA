package com.luna.compile.utils.syntax;

import com.luna.compile.struct.TokenSequence;
import com.luna.compile.utils.syntax.struct.SyntaxNode;

import java.util.Map;

public class SyntaxMatcher {

    public static void init() {
        SyntaxParser.init();
    }

    public static boolean exist(TokenSequence ts) {
        return true;
    }

    public static String find(TokenSequence ts) {
        return null;
    }

    private static void match(TokenSequence ts) {
        Map<String, SyntaxNode> map = SyntaxParser.getMap();

    }

}
