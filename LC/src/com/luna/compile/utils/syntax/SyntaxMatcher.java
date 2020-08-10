package com.luna.compile.utils.syntax;

import com.luna.compile.struct.Token;
import com.luna.compile.struct.TokenSequence;
import com.luna.compile.utils.syntax.struct.SyntaxNode;

import java.util.ArrayList;
import java.util.List;
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

    public static List<String> match(TokenSequence ts) {
        List<String> result = new ArrayList<>();
        for(Token token : ts) {
            String name = match(token);
            result.add(name);
        }
        return result;
    }

    private static String match(List<String> syntaxList) {
        return "";
    }

    private static String match(Token token) {
        Map<String, SyntaxNode> map = SyntaxParser.getMap();
        for (Map.Entry<String, SyntaxNode> entry : map.entrySet()) {
            if(entry.getValue().match(token)) return entry.getKey();
        }
        return null;
    }

}
