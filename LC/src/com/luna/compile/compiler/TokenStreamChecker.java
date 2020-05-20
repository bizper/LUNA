package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.result.Bean;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Token;
import com.luna.compile.utils.TypeFinalizer;

import java.util.List;

import static com.luna.compile.constant.STATUS.*;

public class TokenStreamChecker extends Component {

    private static Component instance;

    private static final String TYPE_CHECK_ERROR = "TYPE CHECK ERROR";

    public static Component getInstance() {
        if(instance == null) instance = new TokenStreamChecker();
        return instance;
    }

    private TokenStreamChecker() {}

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        final List<List<Token>> lists = context.getList();
        for(List<Token> list : lists) {
            Bean bean = TypeFinalizer.derive(list);
            if(!bean.isSuccess()) {
                this.context.setCode(TOKEN_TYPE_ERROR);
                this.context.setMsg(TYPE_CHECK_ERROR);
                this.context.addErrMsg((Token) bean.getData(), true, bean.getMessage());
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
                case INTEGER:
                    break;
                case FLOAT:
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
