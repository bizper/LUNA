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

    public static String match(TokenSequence ts) {
        Map<String, SyntaxNode> map = SyntaxParser.getMap();
        for(Map.Entry<String, SyntaxNode> entry : map.entrySet()) {
            List<Token> list = ts.getList();
            for(int i = 0; i<list.size(); i++) {
                Token token = list.get(i);
                if(entry.getValue().match(token)) {

                }
            }
        }
        return null;
    }

}
