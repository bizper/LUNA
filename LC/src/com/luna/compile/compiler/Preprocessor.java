package com.luna.compile.compiler;

import com.luna.base.config.Config;
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

    private void process(final List<Token> list) {
        this.list = list;

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
