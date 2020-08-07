package com.luna.compile.utils.syntax;

import com.luna.compile.struct.Token;
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

    public static String match(TokenSequence ts) {
        for(Token token : ts) {
            String name = match(token);
            if(name != null) return name;
        }
        return null;
    }

    private static String match(Token token) {
        Map<String, SyntaxNode> map = SyntaxParser.getMap();
        for (Map.Entry<String, SyntaxNode> entry : map.entrySet()) {
            if(entry.getValue().match(token)) return entry.getKey();
        }
        return null;
    }

}
