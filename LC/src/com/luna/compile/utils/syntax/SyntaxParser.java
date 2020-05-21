package com.luna.compile.utils.syntax;

import com.luna.base.io.OUT;
import com.luna.base.io.loader.FileReader;
import com.luna.base.result.FileInfo;
import com.luna.compile.utils.syntax.constant.SyntaxNodeType;
import com.luna.compile.utils.syntax.struct.SyntaxNode;
import com.luna.compile.utils.Env;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class SyntaxParser {

    private static final String path = Env.get("syntax_file_path");

    private static final Pattern rangep = Pattern.compile("[a-zA-Z0-9]-[a-zA-Z0-9]");

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
            if(parse() != 0) map = null;
        }
    }

    private static int parse() {
        FileInfo fileInfo = FileReader.read(new File(path), true, "\\");
        for(String code : fileInfo.getContent()) {
            SyntaxNode sn = parseBNF(code);
            if(sn == null) return 1;
            map.put(sn.getName(), sn);
            OUT.debug(code);
            OUT.debug(sn);
        }
        return 0;
    }

    private static SyntaxNode parseBNF(String code) {
        String[] partition = code.split(":=");
        SyntaxNode syntaxNode = SyntaxNode.create(partition[0].trim());
        parseInside(syntaxNode, partition[1]);
        return syntaxNode;
    }

    private static void parseInside(SyntaxNode node, String str) {
        str = str.trim();
        node.setType(SyntaxNodeType.BASE);
        char[] arr = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        String code;
        for (char c : arr) {
            switch (c) {
                case '|':
                    node.setType(SyntaxNodeType.LINK);
                    code = stringBuilder.toString().trim();
                    if (rangep.matcher(code).matches()) {
                        List<String> range = parseRange(code);
                        if (range != null) {
                            node.addNode(SyntaxNode.create(node.getName(), range));
                        }
                    } else {
                        SyntaxNode cache;
                        if ((cache = find(code)) != null) node.addNode(cache);
                        else node.addNode(SyntaxNode.create(node.getName(), code));
                    }
                    stringBuilder.delete(0, stringBuilder.length() - 1);
                    break;
                case '&':
                    code = stringBuilder.toString().trim();
                    if (rangep.matcher(code).matches()) {
                        List<String> range = parseRange(code);
                        if (range != null) node.addValue(range);
                    } else {
                        SyntaxNode cache;
                        if ((cache = find(code)) != null) node.addNode(cache);
                        else node.addValue(code);
                    }
                    stringBuilder.delete(0, stringBuilder.length() - 1);
                    break;
                default:
                    stringBuilder.append(c);
            }
        }
        if(stringBuilder.length() > 0) {
            code = stringBuilder.toString().trim();
            if(rangep.matcher(code).matches()) {
                List<String> range = parseRange(code);
                if(range != null && node.getType() == SyntaxNodeType.BASE) node.addValue(range);
                if(range != null && node.getType() == SyntaxNodeType.LINK) node.addNode(SyntaxNode.create(node.getName(), range));
            } else {
                if(node.getType() == SyntaxNodeType.BASE) {
                    SyntaxNode cache;
                    if((cache = find(code)) != null) node.addNode(cache);
                    else node.addValue(code);
                }
                if(node.getType() == SyntaxNodeType.LINK) {
                    SyntaxNode cache;
                    if((cache = find(code)) != null) node.addNode(cache);
                    else node.addNode(SyntaxNode.create(node.getName(), code));
                }
            }
        }
    }

    private static List<String> parseRange(String range) {
        String[] atoms = range.trim().split("-");
        List<String> result = new ArrayList<>();
        if(atoms[0].length() != 1 || atoms[1].length() != 1) return null;
        int a = atoms[0].charAt(0);
        int b = atoms[1].charAt(0);
        if(a < b) {
            for(; a<=b; a++) {
                result.add(String.valueOf((char) a));
            }
        } else if(a > b) {
            for(; b<=a; b++) {
                result.add(String.valueOf((char) b));
            }
        } else result.add(String.valueOf((char) a));
        return result;
    }

}
