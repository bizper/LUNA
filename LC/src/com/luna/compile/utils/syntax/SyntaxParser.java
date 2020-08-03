package com.luna.compile.utils.syntax;

import com.luna.base.io.OUT;
import com.luna.base.io.loader.FileReader;
import com.luna.base.result.FileInfo;
import com.luna.compile.utils.Env;
import com.luna.compile.utils.syntax.struct.SyntaxNode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class SyntaxParser {

    private static final String DEFINE_SYMBOL = "::";

    private static final String path = Env.get("syntax_file_path");

    private static Map<String, SyntaxNode> map = null;

    public static Map<String, SyntaxNode> getMap() {
        return map;
    }

    private static SyntaxNode find(String name) {
        return map.get(name);
    }

    public static void init() {
        if(map == null) {
            map = new HashMap<>();
            if(parse() != 0) {
                map = null;
            }
        }
    }

    private static int parse() {
        FileInfo fileInfo = FileReader.read(new File(path), true, "\\");
        for(String code : fileInfo.getContent()) {
            SyntaxNode sn = parseBNF(code);
            map.put(sn.getName(), sn);
            OUT.debug(sn);
        }
        return 0;
    }

    private static int pointer = 0;

    private static StringBuilder cache = new StringBuilder();

    private static void add(char c) {
        cache.append(c);
    }

    private static String get() {
        return cache.toString();
    }

    private static void clear() {
        cache.delete(0, cache.length());
    }

    private static SyntaxNode parseBNF(String code) {
        char[] chars = code.toCharArray();
        boolean isDefine = false;
        SyntaxNode sn = SyntaxNode.get();
        for(pointer = 0; pointer < chars.length; pointer++) {
            char c = chars[pointer];
            if(!isDefine) {
                if(c == '<') {
                    sn.setName(parseTerminal(chars));
                    clear();
                }
                if(c == ':') {
                    isDefine = parseDefine(chars);
                    clear();
                }
            }
        }
        return sn;
    }

    private static String parseTerminal(char[] chars) {
        for(int i = pointer + 1; i < chars.length; i++) {
            if(chars[i] == '>') {
                pointer = i;
                break;
            } else {
                add(chars[i]);
            }
        }
        return get();
    }

    private static boolean parseDefine(char[] chars) {
        for(int i = pointer; i < chars.length; i++) {
            if(chars[i] == '=' && get().equals(DEFINE_SYMBOL)) {
                pointer = i;
                return true;
            } else {
                add(chars[i]);
            }
        }
        return false;
    }

}
