package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.base.kits.ContextKit;
import com.luna.base.result.Bean;
import com.luna.compile.compiler.constant.Keywords;
import com.luna.compile.compiler.constant.MultiSymbolOperator;
import com.luna.compile.compiler.constant.Operator;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.loader.Loader;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.luna.compile.constant.CONSTANT.*;
import static com.luna.compile.constant.STATUS.*;

/**
 * 提取词组，并进行分类
 */
public class Tokenizer extends Component {

    private static final int def_col = 1;
    private static final int def_lin = 1;

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        String[] files = config.getCompileFiles();
        for(String path : files) {
            try {
                context.add(parseFile(path));
            } catch (IOException e) {
                context.setCode(ERROR);
                context.setMsg(e.getMessage());
            }
        }
        OUT.info(context.getList());
        return this;
    }

    private List<Token> parseFile(String path) throws IOException {
        Loader loader = Loader.get();
        Bean<File> bean = loader.load(path, (e0) -> e0.endsWith(".luna"));
        List<Token> list = new ArrayList<>();
        if(bean.isSuccess()) {
            File file = bean.getData();
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String code;
            int line = def_lin;
            while((code = fr.readLine()) != null) {
                list.addAll(parseInside(line++, code));
            }
        } else {
            context.setCode(bean.getCode());
            context.setMsg(bean.getMessage());
        }
        return list;
    }

    private final StringBuilder stringBuilder = new StringBuilder();

    private void add(char c) {
        stringBuilder.append(c);
    }

    private String get() {
        return stringBuilder.toString();
    }

    private void clear() {
        stringBuilder.delete(0, stringBuilder.length());
    }

    private boolean isEmpty() {
        return length() == 0;
    }

    private int length() {
        return stringBuilder.length();
    }

    private List<Token> parseInside(int line, String code) {

        int col = def_col;
        boolean isString = false;
        final char[] chars = code.toCharArray();

        List<Token> list =  new ArrayList<>();

        for(char c : chars) {
            if(c != space) {
                if(c == double_quote) {
                    if(isString) {
                        if(!isEmpty()) {
                            pushToList(list, line, col - (length() + 1), TOKEN.STRING, get());
                        }
                    } else {
                        if(!isEmpty()) {
                            pushToList(list, line, col - length(), get());
                        }
                    }
                    isString = !isString;
                    col++;
                    continue;
                }
                if(Operator.isOperator(c)) {
                    if(!isEmpty()) {
                        pushToList(list, line, col - length(), get());
                    }
                    pushToList(list, line, col - length(), TOKEN.OPERATOR, c);
                    col ++;
                    continue;
                }
                add(c);
                col ++;
            } else {
                if(isString) {
                    add(c);
                    col ++;
                } else {
                    if(!isEmpty()) {
                        pushToList(list, line, col - length(), get());
                    }
                    col++;
                }
            }
        }
        if(!isEmpty()) {
            pushToList(list, line, col - length(), get());
        }
        return merge(list);
    }

    /**
     * 将缓存区的字符压入列表
     * @param list  目标列表
     * @param line  行号
     * @param  col  列号
     * @param value 字符
     */
    private void pushToList(List<Token> list, int line, int col, String value) {
        if(Keywords.isKeyword(value)) {
            list.add(Token.get(line, col, TOKEN.KEYWORD, value));
        } else if(isDigit(value)) {
            list.add(Token.get(line, col, TOKEN.NUMBER, value));
        } else {
            list.add(Token.get(line, col, TOKEN.SYMBOL, value));
        }
        clear();
    }

    private void pushToList(List<Token> list, int line, int col, TOKEN type, Object value) {
        list.add(Token.get(line, col, type, value.toString()));
        clear();
    }

    private boolean isDigit(String s) {
        char[] arr = s.toCharArray();
        for(char c : arr) {
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }

    private List<Token> merge(List<Token> list) {
        for(int i = 0; i < list.size(); i++) {
            String[] arr = MultiSymbolOperator.toArray();
            for(String s : arr) {
                if(ContextKit.match(list, i, s.length(), s, (e0) -> e0 == null ? "" : e0.getValue(), (e1) -> {
                    Token key = e1.get(0);
                    if(key == null) return false;
                    for(int j = 1; j<e1.size(); j++) {
                        Token token = e1.get(j);
                        if(token == null) return false;
                        if(token.getCol() - key.getCol() != 1 && token.getLine() == key.getLine()) return false;
                    }
                    return true;
                })) {
                    List<Token> l = ContextKit.view(list, i, s.length());//获得整个符号列
                    l.remove(0);//移除第一个token，其为记录位
                    list.removeAll(l);//移除剩下的token
                    list.get(i).setValue(s);//将其设置为多符号操作符
                }
            }
        }
        return list;
    }

}
