package com.luna.compile.utils;

import com.luna.base.io.OUT;
import com.luna.compile.compiler.constant.Operator;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;
import com.luna.compile.struct.TokenSequence;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionFinalizer extends BaseFinalizer {

    private ExpressionFinalizer() {}

    public static ExpressionFinalizer getInstance() {
        return new ExpressionFinalizer();
    }

    private String result;

    public void init() {
        result = "";
    }

    //5 + 6 => 5 6 +
    public String calculate(TokenSequence ts) {
        init();
        List<Token> list = ts.getList();
        List<Token> stack = reversePolishNotation(list);
        for(int i = 0; i<stack.size(); i++) {
            Token token = stack.get(i);
            if(token.getType() == TOKEN.OPERATOR) {
                Token param1 = stack.get(i - 2);
                Token param2 = stack.get(i - 1);
                String value = "";
                if(Operator.PLUS.equals(token.getSig())) {
                    value = String.valueOf(Integer.parseInt(param1.getValue()) + Integer.parseInt(param2.getValue()));
                }
                if(Operator.MINUS.equals(token.getSig())) {
                    value = String.valueOf(Integer.parseInt(param1.getValue()) - Integer.parseInt(param2.getValue()));
                }
                if(Operator.MULTI.equals(token.getSig())) {
                    value = String.valueOf(Integer.parseInt(param1.getValue()) * Integer.parseInt(param2.getValue()));
                }
                if(Operator.DIV.equals(token.getSig())) {
                    value = String.valueOf(Integer.parseInt(param1.getValue()) / Integer.parseInt(param2.getValue()));
                }
                Token calculateResult = Token.get(param1.getLine(), param1.getCol(), TOKEN.INTEGER, value, null);
                stack.remove(param1);
                stack.remove(param2);
                stack.remove(token);
                stack.add(i - 2, calculateResult);
                i = -1;
            }
        }
        result = String.valueOf(stack.get(0).getValue());
        return result;
    }

    private List<Token> reversePolishNotation(List<Token> list) {
        List<Token> result = new ArrayList<>();
        Stack<Token> stack = new Stack<>();
        for(Token token : list) {
            if(token.getType() == TOKEN.OPERATOR) {
                if(token.getSig() == Operator.RP) {
                    while(!stack.isEmpty() && stack.peek().getSig() != Operator.LP) {
                        result.add(stack.pop());
                    }
                    stack.pop();
                    continue;
                }
                while(!stack.isEmpty() && stack.peek().getSig().getLevel() == token.getSig().getLevel()) {
                    result.add(stack.pop());
                }
                stack.push(token);
            } else {
                result.add(token);
            }
        }
        if(!stack.isEmpty()) result.add(stack.pop());
        OUT.debug(result);
        return result;
    }

}
