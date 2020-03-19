package com.luna.compile.utils.syntax;

import com.luna.compile.loader.FileReader;
import com.luna.compile.struct.FileInfo;
import com.luna.compile.struct.SyntaxNode;
import com.luna.compile.utils.Env;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SyntaxParser {

    private static final String path = Env.get("syntax_file_path");

    private static Map<String, SyntaxNode> map = null;

    public static Map<String, SyntaxNode> getMap() {
        return map;
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
            System.out.println(code);
            System.out.println(sn);
        }
        return 0;
    }

    private static SyntaxNode parseBNF(String code) {
        String[] partition = code.split(":=");
        SyntaxNode syntaxNode = new SyntaxNode();
        syntaxNode.setName(partition[0].trim());
        parseInside(syntaxNode, partition[1]);
        return syntaxNode;
    }

    private static void parseInside(SyntaxNode node, String str) {
        str = str.trim();
        node.setType(0);
        char[] arr = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = new ArrayList<>();
        String code;
        for(int i = 0; i<arr.length; i++) {
            char c = arr[i];
            switch(c) {
                case '|':
                    node.setType(1);
                    code = stringBuilder.toString().trim();
                    if(code.contains("-")) {
                        List<String> range = parseRange(code);
                        if(range != null) {
                            node.addNode(SyntaxNode.create(node.getName(), range.toArray(new String[]{})));
                        }
                    } else {
                        list.add(code);
                    }
                    stringBuilder.delete(0, stringBuilder.length() - 1);
                    break;
                case '&':
                    code = stringBuilder.toString().trim();
                    if(code.contains("-")) {
                        List<String> range = parseRange(code);
                        if(range != null) list.addAll(range);
                    } else {
                        list.add(code);
                    }
                    stringBuilder.delete(0, stringBuilder.length() - 1);
                    break;
                    default:
                        stringBuilder.append(c);
            }
        }
        if(stringBuilder.length() > 0) {
            code = stringBuilder.toString().trim();
            if(code.contains("-")) {
                List<String> range = parseRange(code);
                if(range != null) list.addAll(range);
            }
        }
    }

    private static List<String> parseRange(String range) {
        String[] atoms = range.trim().split("-");
        List<String> result = new ArrayList<>();
        if(atoms[0].length() != 1 || atoms[1].length() != 1) return null;
        int a = (int) atoms[0].charAt(0);
        int b = (int) atoms[1].charAt(0);
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
