package com.luna.compile.utils;

import com.luna.compile.compiler.constant.SIG;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TokenUtil {

    private static final Object NULL = null;

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
     */
    public static List<Token> getLine(List<Token> list, int line) {
        return list.stream().filter((e) -> e.getLine() == line).collect(Collectors.toList());
    }

    /**
     * 返回一个新的list，清除了相同列的所有list
     */
    public static List<Token> clearLine(List<Token> list, int line) {
        return list.stream().filter((e) -> e.getLine() != line).collect(Collectors.toList());
    }

    public static Token containsType(TOKEN type, Token... args) {
        for(Token t : args) {
            if(t.check(type)) return t;
        }
        return null;
    }

    public static Token containsType(TOKEN type, List<Token> args) {
        for(Token t : args) {
            if(t.check(type)) return t;
        }
        return null;
    }

    public static boolean allType(TOKEN type, Token... args) {
        for(Token t : args) {
            if(!t.check(type)) return false;
        }
        return true;
    }

    public static boolean inTypeRange(Token[] args, TOKEN... types) {
        return inTypeRange(Arrays.asList(args), types);
    }

    public static boolean inTypeRange(List<Token> args, TOKEN... types) {
        return args.stream().anyMatch((e) -> {
            for (TOKEN type : types) {
                if(e.check(type)) return true;
            }
            return false;
        });
    }

}
