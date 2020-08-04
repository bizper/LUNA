package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.compile.compiler.constant.MultiSymbolOperator;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Module;
import com.luna.compile.struct.Token;
import com.luna.compile.struct.TokenSequence;
import com.luna.compile.utils.ExpressionFinalizer;
import com.luna.compile.utils.ModeMatcher;
import com.luna.compile.utils.TokenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.luna.compile.constant.STATUS.OK;
import static com.luna.compile.constant.STATUS.TOKEN_SYNTAX_ERROR;
import static com.luna.base.io.OUT.*;

/**
 * 处理 [source text] <- [replace text] 的预处理器，将代码中的源文本全部替换为后来的文本
 * 源文本语法定义
 * 不允许数字，操作符以及表达式
 */
public class Preprocessor extends Component {

    private Preprocessor() {}

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new Preprocessor();
        return instance;
    }

    private static final String PREPROCESS_ERROR = "PREPROCESS ERROR";

    @Override
    public Component run(Context context, Config config) {
        super.run(context, config);
        for(Module module : context.getModules()) {
            forEach(module);
            if(context.getCode() != OK) {
                break;
            }
        }
        return this;
    }

    private void forEach(Module module) {
        List<TokenSequence> list = module.getList();
        List<TokenSequence> cache = new ArrayList<>(list);
        for (TokenSequence ts : cache) {
            checkDefine(module, ts);
            if(context.getCode() != OK) {
                break;
            }
        }
        for(TokenSequence ts : list) {
            process(ts);
            debug(ts);
        }
    }

    private final HashMap<TokenSequence, TokenSequence> map = new HashMap<>();

    private void checkDefine(Module module, TokenSequence ts) {
        List<Token> list = ts.getList();
        for(int i = 0; i<list.size(); i++) {
            Token token = list.get(i);
            if(TokenUtil.check(token, TOKEN.OPERATOR, MultiSymbolOperator.LEFT_ARROW)) {
                //search for text
                TokenSequence prev = i - 1 < 0 ? null : TokenSequence.getInstance(list, 0, i, (e) -> e.getLine() == token.getLine());
                TokenSequence next = i + 1 > list.size() ? null : TokenSequence.getInstance(list, i + 1, list.size(), (e) -> e.getLine() == token.getLine());
                //to make sure that all tokens are at the same line.
                if(prev != null && prev.isEmpty()) {
                    prev = null;
                }
                if(next != null && next.isEmpty()) {
                    next = null;
                }
                if(prev == null) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(module, token, "语法错误：不允许空的源文本");
                    module.remove(token.getLine());
                    break;
                }
                TOKEN type = prev.getType();
                if(type == TOKEN.INTEGER || type == TOKEN.FLOAT || type == TOKEN.STRING || type == TOKEN.BOOLEAN) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(module, prev.get(0), "语法错误：源文本不能为 " + prev.toString() + "::" + type);
                    module.remove(token.getLine());
                    break;
                }
                if(next == null) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(module, token, true, "语法错误：不允许空的替换文本");
                    module.remove(token.getLine());
                    break;
                }
                if(map.containsKey(prev)) {
                    context.setCode(TOKEN_SYNTAX_ERROR);
                    context.setMsg(PREPROCESS_ERROR);
                    context.addErrMsg(module, prev.get(0), "语法错误：重复定义的源文本 " + prev.toString());
                    module.remove(token.getLine());
                    break;
                }
                map.put(prev, next);
                //start to remove token for non multi-define
                module.remove(token.getLine());
             }
        }
    }

    private void process(TokenSequence ts) {
        List<Token> list = ts.getList();
        for(int i = 0; i<list.size(); i++) {
            Token token = list.get(i);
            final int j = i;//lambda expression needs non-effectively variable
            map.keySet().forEach((e) -> {
                if(e.headMatch(token)) {
                    if(subListMatch(list, j, e)) {
                        clearMatch(list, j, e, map.get(e));
                    }
                }
            });
        }
    }

    private boolean subListMatch(List<Token> list, int i, TokenSequence ts) {
        for(i = i + 1; i<list.size() && !ts.isTail(); i++) {
            if(!ts.nextMatch(list.get(i))) return false;
        }
        return true;
    }

    //ts: matched token sequence
    //tts: target token sequence
    private void clearMatch(List<Token> list, int i, TokenSequence ts, TokenSequence tts) {
        int k = 0;
        Token key = list.get(i);
        tts.resetMetaInfo(key.getLine(), key.getCol());
        for(;i<list.size() && k<ts.size(); k++) {
            list.remove(i);
        }
        list.addAll(i, tts.getList());
    }

}
