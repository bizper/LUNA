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

    private static Bean<TOKEN> derive(Token operator, Token... args) {
        if(args.length == 0) return null;
        if(TokenUtil.containsType(OPERATOR, args)) return build(false, "");
        if(TokenUtil.containsType(KEYWORD, args)) return build(false, "");
        if(operator.getType() == OPERATOR) {
            SIG sig = operator.getSig();
            if(sig == Operator.PLUS) {
                if(TokenUtil.containsType(BOOLEAN, args)) return build(false, "");
                if(TokenUtil.containsType(STRING, args)) return build(true, "", STRING);
                if(TokenUtil.allType(INTEGER, args)) return build(true, "", INTEGER);
            }
            if(sig == Operator.MINUS ||
                sig == Operator.DIV ||
                sig == Operator.MULTI
            ) {
                if(TokenUtil.containsType(BOOLEAN, args)) return build(false, "");
                if(TokenUtil.containsType(STRING, args)) return build(false, "");
                if(TokenUtil.allType(INTEGER, args)) return build(true, "", INTEGER);
            }
            if(sig == MultiSymbolOperator.EQUALS ||
                    sig == MultiSymbolOperator.NOT_EQUALS ||
                    sig == Operator.GT ||
                    sig == Operator.LT ||
                    sig == Operator.NOT
            ) {
                return build(true, "", BOOLEAN);
            }
            return null;
        } else return build(false, operator.getValue() + "不应出现在此处。");
    }

}
