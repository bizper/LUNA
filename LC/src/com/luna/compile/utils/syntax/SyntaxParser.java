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

public final class SyntaxParser {

    private static final String path = Env.get("syntax_file_path");

    private static Map<String, SyntaxNode> map = null;

    public static Map<String, SyntaxNode> getMap() {
        return map;
    }

    private static SyntaxNode find(String name) {
        return map.get(name);
    }

    public static SyntaxNode match(String expr) {
        for(SyntaxNode s : map.values()) {
            if(s.match(expr)) return s;
        }
        return null;
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
            OUT.debug(code);
            OUT.debug(sn);
        }
        return 0;
    }

    private static SyntaxNode parseBNF(String code) {
        if(code.contains(":=")) {
            String[] partition = code.split(":=");
            return SyntaxNode.create(partition[0].trim(), partition[1].trim()).setType(SyntaxNodeType.BASE);
        }
        StringBuilder value = new StringBuilder();
        if(code.contains("::")) {
            String[] partition = code.split("::");
            if(partition[1].contains("|")) {
                value.append("(");
                String[] nodes = partition[1].split("\\|");
                for(String node : nodes) {
                    node = node.trim();
                    if(node.contains(" ")) {
                        String[] secNodes = node.split("\\s");
                        value.append("(");
                        for(String n : secNodes) {
                            n = n.trim();
                            if(map.containsKey(n)) {
                                value.append("(").append(find(n).getValue()).append(")").append("[\\s]*");
                            }
                        }
                        value.append(")");
                    } else {
                        if(map.containsKey(node)) {
                            value.append(find(node).getValue()).append("|");
                        }
                    }
                }
                if(value.charAt(value.length() - 1) == '|') value.deleteCharAt(value.length() - 1);
                value.append(")");
            } else {
                if(partition[1].contains(" ")) {
                    String[] secNodes = partition[1].split("\\s");
                    value.append("(");
                    for(String n : secNodes) {
                        n = n.trim();
                        if(map.containsKey(n)) {
                            value.append("(").append(find(n).getValue()).append(")").append("[\\s]*");
                        }
                    }
                    value.append(")");
                } else {
                    if(map.containsKey(partition[1])) {
                        value.append(find(partition[1]).getValue()).append("|");
                    }
                }
            }
            return SyntaxNode.create(partition[0].trim(), value.toString()).setType(SyntaxNodeType.LINK);
        }
        return null;
    }

}
