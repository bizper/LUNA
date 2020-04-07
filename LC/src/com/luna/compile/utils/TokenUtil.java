package com.luna.compile.utils;

import com.luna.compile.compiler.constant.SIG;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;

import java.util.List;
import java.util.stream.Collectors;

public class TokenUtil {

    public static boolean check(Token token, TOKEN type, String value) {
        return token != null && token.check(type, value);
    }

    public static boolean check(Token token, TOKEN type, SIG value) {
        return token != null && token.check(type, value);
    }

    public static boolean check(Token token, TOKEN type) {
        return token != null && token.check(type);
    }

    /**
     * 返回一个新的list，包含了相同列的所有token
     * @param list
     * @param pos
     * @return
     */
    public static List<Token> getLine(List<Token> list, int pos) {
        Token key = list.get(pos);
        return list.stream().filter((e) -> e.getLine() == key.getLine() && e.getCol() > key.getCol()).collect(Collectors.toList());
    }

    /**
     * 返回一个新的list，清除了相同列的所有list
     * @param list
     * @param line
     * @return
     */
    public static List<Token> clearLine(List<Token> list, int line) {
        return list.stream().filter((e) -> e.getLine() != line).collect(Collectors.toList());
    }

    public static boolean containsType(TOKEN type, Token... args) {
        for(Token t : args) {
            if(t.check(type)) return true;
        }
        return false;
    }

    public static boolean allType(TOKEN type, Token... args) {
        for(Token t : args) {
            if(!t.check(type)) return false;
        }
        return true;
    }

}
