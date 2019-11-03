package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Token;
import com.luna.compile.utils.ModeMatcher;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
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
            OUT.debug("After Preprocess:\n"+list);
        }
        return this;
    }

    private HashMap<String, String> map = new HashMap<>();

    private void checkDefine(final List<Token> list) {
        int i;
        for(i = 0; i<list.size(); i++) {
            Token token = list.get(i);
            if(check(token, TOKEN.OPERATOR, "<-")) {
                //search for text
                Token prev = i - 1 < 0 ? null : list.get(i - 1);
                Token next = list.get(i + 1);
                //ensure there are no null
                if(prev == null) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg("PREPROCESS ERROR");
                    context.addErrMsg(token, "语法错误：不允许空的源文本");
                    continue;
                }
                if(prev.getType() == TOKEN.NUMBER || prev.getType() == TOKEN.OPERATOR || prev.getType() == TOKEN.STRING) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg("PREPROCESS ERROR");
                    context.addErrMsg(prev, "语法错误：源文本不能为" + prev.getType().getDesc() + " " + prev.getValue());
                    continue;
                }
                if(Objects.requireNonNull(prev).getLine() != token.getLine()) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg("PREPROCESS ERROR");
                    context.addErrMsg(prev, "语法错误：不允许空的源文本");
                    continue;
                }
                if(Objects.requireNonNull(next).getLine() != token.getLine()) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg("PREPROCESS ERROR");
                    context.addErrMsg(token, true, "语法错误：不允许空的替换文本");
                    continue;
                }
                if(map.containsKey(Objects.requireNonNull(prev).getValue())) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg("PREPROCESS ERROR");
                    context.addErrMsg(prev, "语法错误：重复定义的源文本 " + prev.getValue());
                    continue;
                }
                if(ModeMatcher.compile("SYMBOL * *").match(getLine(list, i))) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg("PREPROCESS ERROR");
                    context.addErrMsg(token, true, "语法错误：不允许定义表达式作为替换文本");
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
        OUT.debug(map);
    }

    private void process(final List<Token> list) {
        for(Token token : list) {
            if(map.containsKey(token.getValue()) && !check(token, TOKEN.STRING)) {
                token.setValue(map.get(token.getValue()));
                token.setType(TOKEN.STRING);//the all replace text is string, not symbol, in case of symbol link error
            }
        }
    }

    private boolean check(Token token, TOKEN type, String value) {
        return token != null && token.getType() == type && token.getValue().equals(value);
    }

    private boolean check(Token token, TOKEN type) {
        return token != null && token.getType() == type;
    }

    private List<Token> getLine(List<Token> list, int pos) {
        List<Token> result = new ArrayList<>();
        Token key = list.get(pos);
        for(int i = pos + 1; i<list.size(); i++) {
            Token token = list.get(i);
            if(token.getLine() == key.getLine()) result.add(token);
        }
        return result;
    }

}
