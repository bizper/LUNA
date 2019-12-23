package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Token;
import com.luna.compile.utils.ModeMatcher;
import com.luna.compile.utils.TokenUtil;

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

    private static final String PREPROCESS_ERROR = "PREPROCESS ERROR";

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        for(List<Token> list : context.getList()) {
            list = process(checkDefine(list));
            OUT.debug(list);
        }
        return this;
    }

    private HashMap<String, String> map = new HashMap<>();

    private ModeMatcher.Atom atom = ModeMatcher.compile("SYMBOL *");

    private List<Token> checkDefine(List<Token> list) {
        for(int i = 0; i<list.size(); i++) {
            Token token = list.get(i);
            if(TokenUtil.check(token, TOKEN.OPERATOR, "<-")) {
                //search for text
                Token prev = i - 1 < 0 ? null : list.get(i - 1);
                Token next = i + 1 > list.size() ? null : list.get(i + 1);
                //to make sure that all tokens are at the same line.
                if(prev != null && prev.getLine() != token.getLine()) {
                    prev = null;
                }
                if(next != null && next.getLine() != token.getLine()) {
                    next = null;
                }
                if(prev == null) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(token, "语法错误：不允许空的源文本");
                    list = TokenUtil.clearLine(list, token.getLine());
                    i = -1;
                    continue;
                }
                if(prev.getType() == TOKEN.NUMBER || prev.getType() == TOKEN.OPERATOR || prev.getType() == TOKEN.STRING) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(prev, "语法错误：源文本不能为" + prev.getType().getDesc() + " " + prev.getValue());
                    list = TokenUtil.clearLine(list, token.getLine());
                    i = -1;
                    continue;
                }
                if(Objects.requireNonNull(prev).getLine() != token.getLine()) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(prev, "语法错误：不允许空的源文本");
                    list = TokenUtil.clearLine(list, token.getLine());
                    i = -1;
                    continue;
                }
                if(next == null || (Objects.requireNonNull(next).getLine() != token.getLine())) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(token, true, "语法错误：不允许空的替换文本");
                    list = TokenUtil.clearLine(list, token.getLine());
                    i = -1;
                    continue;
                }
                if(map.containsKey(Objects.requireNonNull(prev).getValue())) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(prev, "语法错误：重复定义的源文本 " + prev.getValue());
                    list = TokenUtil.clearLine(list, token.getLine());
                    i = -1;
                    continue;
                }
                if(atom.match(TokenUtil.getLine(list, i))) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(token, true, "语法错误：不允许定义表达式作为替换文本");
                    list = TokenUtil.clearLine(list, token.getLine());
                    i = -1;
                    continue;
                }
                map.put(prev.getValue(), next.getValue());
                //start to remove token for non multi-define
                list.remove(prev);
                list.remove(next);
                list.remove(token);
                i = -1;
             }
        }
        OUT.debug(map);
        return list;
    }

    private List<Token> process(final List<Token> list) {
        for(Token token : list) {
            if(map.containsKey(token.getValue()) && !TokenUtil.check(token, TOKEN.STRING)) {
                token.setValue(map.get(token.getValue()));
                token.setType(TOKEN.SYMBOL);
            }
        }
        return list;
    }



}
