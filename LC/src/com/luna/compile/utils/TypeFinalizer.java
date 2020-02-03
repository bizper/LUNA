package com.luna.compile.utils;

import com.luna.base.result.Bean;
import com.luna.compile.compiler.constant.MultiSymbolOperator;
import com.luna.compile.compiler.constant.Operator;
import com.luna.compile.compiler.constant.SIG;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;

import java.util.List;

import static com.luna.compile.constant.TOKEN.*;

/**
 * derive type of the expression
 * such as
 * number + - * / number => number
 * anything + string => string
 * anything - * / string => error
 * string - * / anything => error
 */
public class TypeFinalizer extends BaseFinalizer {

    public static Bean<TOKEN> derive(List<Token> tokens) {
        return null;
    }

    private static Bean<TOKEN> derive(Token a, Token operator, Token b) {
        if(a == null || operator == null || b == null) return null;
        if(a.getType() == OPERATOR || b.getType() == OPERATOR) return build(false, "");
        if(a.getType() == KEYWORD || b.getType() == KEYWORD) return build(false, "");
        if(operator.getType() == OPERATOR) {
            SIG sig = operator.getSig();
            if(sig == Operator.PLUS) {
                if(a.getType() == BOOLEAN || b.getType() == BOOLEAN) return build(false, "");
                if(a.getType() == STRING || b.getType() == STRING) return build(true, "", STRING);
                if(a.getType() == NUMBER && b.getType() == NUMBER) return build(true, "", NUMBER);
            }
            if(sig == Operator.MINUS) {
                if(a.getType() == BOOLEAN || b.getType() == BOOLEAN) return build(false, "");
                if(a.getType() == STRING || b.getType() == STRING) return build(false, "");
                if(a.getType() == NUMBER && b.getType() == NUMBER) return build(true, "", NUMBER);
            }
            if(sig == Operator.DIV) {
                if(a.getType() == BOOLEAN || b.getType() == BOOLEAN) return build(false, "");
                if(a.getType() == STRING || b.getType() == STRING) return build(false, "");
                if(a.getType() == NUMBER && b.getType() == NUMBER) return build(true, "", NUMBER);
            }
            if(sig == Operator.MULTI) {
                if(a.getType() == BOOLEAN || b.getType() == BOOLEAN) return build(false, "");
                if(a.getType() == STRING || b.getType() == STRING) return build(false, "");
                if(a.getType() == NUMBER && b.getType() == NUMBER) return build(true, "", NUMBER);
            }
            if(sig == MultiSymbolOperator.EQUALS) {
                return build(true, "", BOOLEAN);
            }
            if(sig == MultiSymbolOperator.NOT_EQUALS) {
                return build(true, "", BOOLEAN);
            }
            return null;
        } else return build(false, operator.getValue() + "不应出现在此处。");
    }

}
