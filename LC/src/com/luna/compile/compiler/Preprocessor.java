package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.luna.compile.constant.STATUS.TOKEN_SYNTAX_ERROR;

/**
 * 处理 [source text] <- [replace text] 的预处理器，将代码中的source text全部替换为后来的文本
 * source text语法定义
 * 不允许数字，操作符以及字符的定义
 */
public class Preprocessor extends Component {

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        for(final List<Token> list : context.getList()) {
            checkDefine(list);
            process(list);
            System.out.println(list);
        }
        return this;
    }

    private HashMap<String, String> map = new HashMap<>();

    private void checkDefine(final List<Token> list) {
        int i;
        for(i =0; i<list.size(); i++) {
            Token token = list.get(i);
            if(check(token, TOKEN.OPERATOR, "<-")) {
                //search for text
                Token prev = i - 1 < 0 ? null : list.get(i - 1);
                Token next = list.get(i + 1);
                //ensure there are no null
                if(checkToken(prev)) continue;
                if(map.containsKey(Objects.requireNonNull(prev).getValue())) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg("PREPROCESS ERROR");
                    context.addErrMsg(prev, "语法错误：重复定义的源文本 " + prev.getValue());
                    continue;
                }
                map.put(prev.getValue(), next.getValue());
                //start to remove token for non multi-define
                list.remove(prev);
                list.remove(next);
                list.remove(token);
                i = 0;
            }
        }
        System.out.println(map);
    }

    private boolean checkToken(Token next) {
        if(next == null || next.getType() == TOKEN.NUMBER || next.getType() == TOKEN.OPERATOR || next.getType() == TOKEN.STRING) {
            if(next == null) {
                context.setCode(TOKEN_SYNTAX_ERROR);
                context.setMsg("PREPROCESS ERROR");
                context.addErrMsg("语法错误：不允许空的源文本");
                return true;
            } else {
                context.setCode(TOKEN_SYNTAX_ERROR);
                context.setMsg("PREPROCESS ERROR");
                context.addErrMsg(next, "语法错误：源文本不允许为" + next.getType() + " " + next.getValue());
                return true;
            }
        }
        return false;
    }

    private void process(final List<Token> list) {
        for(Token token : list) {
            if(map.containsKey(token.getValue())) {
                token.setValue(map.get(token.getValue()));
                token.setType(TOKEN.STRING);
            }
        }
    }

    private boolean check(Token token, TOKEN type, String value) {
        return token != null && token.getType() == type && token.getValue().equals(value);
    }

}
