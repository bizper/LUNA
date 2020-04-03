package com.luna.compile.utils;

import com.luna.compile.compiler.constant.Operator;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ExpressionFinalizer extends BaseFinalizer {

    private static final char symbol[] = {'+', '-', '*', '/', '(', ')'};

    private static int cursor = 0;

    private static List<Token> list;

    private static Map<String, Object> map;

    private static Stack<Token> numbers;

    private static Stack<Token> operators;

    public static List<Token> derive(List<Token> tokens) {
        return deriveWithMapping(null, tokens);
    }

    public static List<Token> deriveWithMapping(Map<String, Object> map, List<Token> tokens) {
        ExpressionFinalizer.list = tokens;
        ExpressionFinalizer.map = map;
        ExpressionFinalizer.numbers = new Stack<>();
        ExpressionFinalizer.operators = new Stack<>();
        ExpressionFinalizer.cursor = 0;
        for(Token token : tokens) {
            switch(token.getType()) {
                case NUMBER:
                case STRING:
                case SYMBOL:
                    numbers.add(token);
                    break;
                case OPERATOR:
                    operators.add(token);
                    break;
            }
            cursor ++;
        }
        System.out.println(numbers);
        System.out.println(operators);
        return null;
    }

    private static Token calculate() {
        Token atomA = numbers.pop();
        Token op = operators.pop();
        Token atomB = numbers.pop();
        if(op.getSig() == Operator.PLUS) {
            return Token.get(atomB.getLine(), atomB.getCol(), TOKEN.STRING, atomA.getValue() + atomB.getValue(), null);
        }
        return null;
    }

    private static void parseLP() {

    }

}
