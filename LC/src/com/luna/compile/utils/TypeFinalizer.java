package com.luna.compile.utils;

import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;

import java.util.List;

/**
 * derive type of the expression
 * such as
 * int + int => int
 * double + double => double
 * int + double => double
 * anything + string => string
 *
 */
public class TypeFinalizer {

    public TOKEN derive(List<Token> list) {
        return derive(list.toArray(new Token[]{}));
    }

    public TOKEN derive(Token... tokens) {
        return null;
    }

}
