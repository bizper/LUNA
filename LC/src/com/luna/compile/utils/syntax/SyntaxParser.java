package com.luna.compile.utils.syntax;

import com.luna.base.io.OUT;
import com.luna.base.io.loader.FileReader;
import com.luna.base.result.FileInfo;
import com.luna.compile.utils.Env;
import com.luna.compile.utils.syntax.constant.SyntaxNodeType;
import com.luna.compile.utils.syntax.struct.SyntaxNode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class SyntaxParser {

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
            if(sn == null) return 1;
            map.put(sn.getName(), sn);
            OUT.debug(sn.getName());
            OUT.debug(sn.getValue());
        }
        return 0;
    }

    private static SyntaxNode parseBNF(String code) {
        return null;
    }

}
