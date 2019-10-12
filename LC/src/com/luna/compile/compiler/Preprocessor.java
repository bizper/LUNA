package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.Out;
import com.luna.base.result.Bean;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Token;

import java.util.HashMap;
import java.util.List;

public class Preprocessor extends Component {

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        for(final List<Token> list : context.getList()) {
            process(list);
            System.out.println(list);
        }
        return this;
    }

    private List<Token> list;
    private int i;
    private HashMap<String, String> defineMap = new HashMap<>();

    private void process(final List<Token> list) {
        this.list = list;
        for(i = 0; i<list.size(); i++) {
            Token token = list.get(i);
            if(check(token, TOKEN.OPERATOR, "#") && check(next(), TOKEN.SYMBOL, "define")) {
                if(!checkType(get(2), TOKEN.SYMBOL)) {
                    context.addErrMsg(String.format("Syntax Error at line %d: Can't set keywords %s as define words.", get(2).getLine(), getValue(2)));
                    context.setMsg("Error happened.");
                    context.setCode(10001);
                }
                if(!checkType(get(3), TOKEN.SYMBOL)) {
                    context.addErrMsg(String.format("Syntax Error at line %d: Can't set keywords %s as define words.", get(3).getLine(), getValue(3)));
                    context.setMsg("Error happened.");
                    context.setCode(10001);
                }
                System.out.print(get(2).getValue() + " = ");
                System.out.println(get(3).getValue());
                defineMap.put(getValue(2), getValue(3));
                i = i + 2;
            } else {
                if(checkType(token, TOKEN.SYMBOL) && defineMap.containsKey(token.getValue())) {
                    token.setValue(defineMap.get(token.getValue()));
                }
            }
        }
    }

    private boolean check(Token token, TOKEN type, String value) {
        return token != null && token.getType() == type && token.getValue().equals(value);
    }

    private boolean checkType(Token token, TOKEN type) {
        return token != null && token.getType() == type;
    }

    private Token get() {
        return get(0);
    }

    private Token get(int offset) {
        return list.get(i + offset);
    }

    private String getValue() {
        return getValue(0);
    }

    private String getValue(int offset) {
        return list.get(i + offset).getValue();
    }

    private Token next() {
        if(i + 1 >= list.size()) return null;
        return list.get(i + 1);
    }

    private Token prev() {
        if(i - 1 < 0) return null;
        return list.get(i - 1);
    }

}
