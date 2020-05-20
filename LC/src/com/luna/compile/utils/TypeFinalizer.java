package com.luna.compile.utils;

import com.luna.base.io.OUT;
import com.luna.base.kits.StringKit;
import com.luna.base.result.Bean;
import com.luna.compile.compiler.constant.MultiSymbolOperator;
import com.luna.compile.compiler.constant.Operator;
import com.luna.compile.compiler.constant.SIG;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Token;

import java.util.ArrayList;
import java.util.List;

import static com.luna.compile.constant.TOKEN.*;

/**
 * derive the type of specify expression
 * such as
 * number + - * / number    =>      number
 * anything + string        =>      string
 * anything - * / string    =>      error
 * string - * / anything    =>      error
 */
public class TypeFinalizer extends BaseFinalizer {

    public static Bean derive(List<Token> tokens) {
        TOKEN result = null;
        if(tokens.size() == 1) result = tokens.get(0).getType();
        List<Token> cache = new ArrayList<>(tokens);
        for(int i = 0; i<cache.size(); i++) {
            Token t = cache.get(i);
            switch(t.getType()) {
                case OPERATOR:
                    if(t.getSig() == Operator.PLUS) {
                        Bean<TOKEN> bean = derive(t, cache.get(i - 1), cache.get(i + 1));
                        if(bean.isSuccess()) {
                            result = bean.getData();
                            Token prev = cache.get(i - 1);
                            Token next = cache.get(i + 1);
                            cache.remove(t);
                            cache.remove(prev);
                            cache.remove(next);
                            cache.add(i - 1, Token.get(0, 0, result, "", null));
                            i = 0;
                        } else {
                            return build(false, bean.getMessage(), bean.getData());
                        }
                    }
                    break;
            }
        }
        return build(true, "", result);
    }

    private static Bean derive(Token operator, Token... args) {
        OUT.debug(operator);
        OUT.debug(StringKit.join(args, ", "));
        Token error;
        if((error = TokenUtil.containsType(OPERATOR, args)) != null) return build(false, error.getValue() + "不应出现在此处。", error);
        if((error = TokenUtil.containsType(KEYWORD, args)) != null) return build(false, error.getValue() + "不应出现在此处。", error);
        if(operator.getType() == OPERATOR) {
            SIG sig = operator.getSig();
            if(sig == Operator.PLUS) {
                if(TokenUtil.containsType(STRING, args) != null) return build(true, "", STRING);
                if((error = TokenUtil.containsType(BOOLEAN, args)) != null) return build(false, error.getValue() + "不应出现在此处。", error);
                if(TokenUtil.inTypeRange(args, INTEGER, FLOAT) && (TokenUtil.containsType(FLOAT, args) != null)) return build(true, "", FLOAT);
                if(TokenUtil.allType(INTEGER, args)) return build(true, "", INTEGER);
            }
            if(sig == Operator.MINUS ||
                sig == Operator.DIV ||
                sig == Operator.MULTI
            ) {
                if((error = TokenUtil.containsType(BOOLEAN, args)) != null) return build(false, error.getValue() + "不应出现在此处。", error);
                if((error = TokenUtil.containsType(STRING, args)) != null) return build(false, error.getValue() + "不应出现在此处。", error);
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
        }
        return build(false, operator.getValue() + "不应出现在此处。", operator);
    }

}
