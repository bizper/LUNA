package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.base.kits.ContextKit;
import com.luna.base.result.Bean;
import com.luna.compile.compiler.constant.Keywords;
import com.luna.compile.compiler.constant.MultiSymbolOperator;
import com.luna.compile.compiler.constant.Operator;
import com.luna.compile.constant.COMPONENT;
import com.luna.compile.constant.TOKEN;
import com.luna.base.io.loader.Loader;
import com.luna.compile.struct.Context;
import com.luna.base.result.FileInfo;
import com.luna.compile.struct.Module;
import com.luna.compile.struct.Token;
import com.luna.compile.struct.TokenSequence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.luna.compile.constant.CONSTANT.double_quote;
import static com.luna.compile.constant.CONSTANT.space;
import static com.luna.compile.constant.STATUS.OK;

/**
 * 提取词组，并进行分类
 */
public class Tokenizer extends Component {

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new Tokenizer();
        return instance;
    }

    private Tokenizer() {}

    @Override
    public COMPONENT type() {
        return COMPONENT.TOKENIZER;
    }

    private static final int def_col = 1;
    private static final int def_lin = 1;

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        String[] files = config.getCompileFiles();
        for(String path : files) {
            context.addModule(parseFile(path));
        }
        context.setCode(OK);
        context.setMsg("SUCCESS");
        return this;
    }

    private Module parseFile(String path) {
        Module module = Module.get();
        List<TokenSequence> list = new ArrayList<>();
        module.setList(list);
        Loader loader = Loader.get();
        Bean<File> bean = loader.load(path, (e0) -> e0.endsWith(".luna"));
        if(!bean.isSuccess()) {
            context.setCode(bean.getCode());
            context.setMsg(bean.getMessage());
            return module;
        }
        FileInfo fileInfo = com.luna.base.io.loader.FileReader.read(bean.getData());
        OUT.info("NOW COMPILING: " + fileInfo.getPath());
        module.setName(fileInfo.getName());
        int line = def_lin;
        for(String code : fileInfo.getContent()) {
            TokenSequence ts = parseInside(line++, code);
            if(!ts.isEmpty()) {
                list.add(ts);
                OUT.debug(ts.getList());
            }
        }
        return module;
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

    private TokenSequence parseInside(int line, String code) {

        int col = def_col;
        boolean isString = false;
        final char[] chars = code.toCharArray();

        List<Token> list =  new ArrayList<>();

        TokenSequence ts = TokenSequence.getInstance(list).setLine(line);

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
            } else {
                if(isString) {
                    add(c);
                } else {
                    if(!isEmpty()) {
                        pushToList(list, line, col - length(), get());
                    }
                }
            }
            col ++;
        }
        if(!isEmpty()) {
            pushToList(list, line, col - length(), get());
        }
        return merge(ts);
    }

//    public List<Token> parseLine(int line, String code) {
//        return parseInside(line, code);
//    }

    /**
     * put the buffer string into the list
     * @param list  target list
     * @param line  line
     * @param col   column
     * @param value char
     */
    private void pushToList(List<Token> list, int line, int col, String value) {
        boolean[] isDigit;
        if(Keywords.isKeyword(value)) {
            list.add(Token.get(line, col, TOKEN.KEYWORD, value, Keywords.getKeyword(value)));
        } else if((isDigit = isDigit(value))[0]) {
            list.add(Token.get(line, col, isDigit[1] ? TOKEN.FLOAT : TOKEN.INTEGER, value, null));
        } else if(value.equals("true") || value.equals("false")) {
            list.add(Token.get(line, col, TOKEN.BOOLEAN, value, null));
        } else {
            list.add(Token.get(line, col, TOKEN.SYMBOL, value, null));
        }
        clear();
    }

    private void pushToList(List<Token> list, int line, int col, TOKEN type, Object value) {
        list.add(Token.get(line, col, type, value.toString(), Operator.getOperator(value.toString())));
        clear();
    }

    private boolean[] isDigit(String s) {
        boolean hex = false;
        if(s.startsWith("0x")) {
            s = s.substring(2);
            hex = true;
        }
        char[] arr = s.toCharArray();
        boolean isFloat = false;
        for(char c : arr) {
            if(c == '.') {
                if(!isFloat) isFloat = true;
                else return new boolean[]{false, false};
            }
            if(!Character.isDigit(c) && (!hex && c >= 'A' && c <= 'F')) {
                return new boolean[]{false, false};
            }
        }
        return new boolean[]{true, isFloat};
    }

    private TokenSequence merge(TokenSequence ts) {
        List<Token> list = ts.getList();
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
                    List<Token> l = ContextKit.view(list, i, s.length());//get the whole symbol line
                    l.remove(0);//remove the first token，mark it as the symbol
                    list.removeAll(l);//remove the last token
                    list.get(i).setValue(s);
                    list.get(i).setSig(MultiSymbolOperator.getMultiSymbolOperator(s));
                }
            }
        }
        return ts;
    }

}
