package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Token;

import java.util.List;

public class TokenStreamChecker extends Component {

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        final List<List<Token>> lists = context.getList();
        for(List<Token> list : lists) {
            if(!check(list)) {
                break;
            }
        }
        return this;
    }

    private List<Token> list;
    private int i;

    private boolean check(final List<Token> list) {
        this.list = list;
        for(i = 0; i<list.size(); i++) {
            Token token = list.get(i);
            switch(token.getType()) {
                case OPERATOR:
                    break;
                case KEYWORD:
                    break;
                case SYMBOL:
                    break;
                case STRING:
                    break;
                case NUMBER:
                    break;
                default:
                    break;
            }
        }
        return true;
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
