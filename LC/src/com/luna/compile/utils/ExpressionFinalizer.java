package com.luna.compile.utils;

import com.luna.compile.compiler.constant.Operator;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;

import java.util.List;

public class ExpressionFinalizer extends BaseFinalizer {

    private static final char symbols[] = {'+', '-', '*', '/', '(', ')'};

    private static String result;

    private static final StringBuilder cache = new StringBuilder();

    private static void put(Object obj) {
        cache.append(obj);
    }

    private static void clear() {
        cache.delete(0, cache.length() - 1);
    }

    private static String get() {
        return cache.toString();
    }

    public static String getResult() {
        return result;
    }

    public static void calculate(List<Token> list) {
        for(int i = 0; i<list.size(); i++) {
            Token token = list.get(i);
            if(token.getType() == TOKEN.OPERATOR && token.getSig() == Operator.LP) ICalculate(list, i);

        }
    }

    /**
     * picker
     * @param token token been chose
     */
    private static void PCalculate(Token token) {
        switch(token.getType()) {
            case OPERATOR:
                break;
            case INTEGER:
                break;
            case FLOAT:
                break;
                default:
                    break;

        }
    }

    private static void ICalculate(List<Token> list, int pos) {
        for(int i = pos; i < list.size(); i++) {
            Token token = list.get(i);
            if(token.getType() == TOKEN.OPERATOR && token.getSig() == Operator.LT) return;
        }
    }

}
