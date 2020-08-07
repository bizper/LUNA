package com.luna.compile.utils.syntax;

import com.luna.base.io.OUT;
import com.luna.base.io.loader.FileReader;
import com.luna.base.result.FileInfo;
import com.luna.compile.utils.Env;
import com.luna.compile.utils.syntax.constant.SyntaxRequirement;
import com.luna.compile.utils.syntax.struct.SyntaxNode;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.luna.compile.utils.syntax.constant.SyntaxNodeType.*;

public final class SyntaxParser {

    private static final String DEFINE_SYMBOL = "::";

    private static final String path = Env.get("" + "syntax_file_path");

    private static Map<String, SyntaxNode> map = null;

    public static Map<String, SyntaxNode> getMap() {
        return map;
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
            OUT.print(sn);
        }
        return 0;
    }

    private static int pointer = 0;

    private static final StringBuilder cache = new StringBuilder();

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
        int partCount = 0;
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
            } else {
                if(c == '(') {
                    List<SyntaxNode> nodes = sn.getNodes();
                    SyntaxNode and = SyntaxNode.get(AND);
                    sn.addNode(and);
                    sn = and;
                    sn.addAllNode(nodes);
                    partCount = 0;
                }
                if(c == ')') {
                    sn = sn.getParent();
                }
                if(c == '"') {
                    String value = parseConst(chars);
                    SyntaxNode constNode = SyntaxNode.get(CONST);
                    constNode.setValue(value);
                    sn.addNode(constNode);
                    clear();
                    partCount ++;
                }
                if(c == '<') {
                    String value = parseTerminal(chars);
                    if(value.equals(sn.getName())) {
                        sn.addNode(sn);
                    } else {
                        SyntaxNode node = map.get(value);
                        sn.addNode(node);
                    }
                    clear();
                    partCount ++;
                }
                if(c == '[') {
                    String value = parseOptional(chars);
                    if(value.equals(sn.getName())) {
                        SyntaxNode clone = sn.clone();
                        Objects.requireNonNull(clone).setRequirement(SyntaxRequirement.OPTIONAL);
                        sn.addNode(clone);
                    } else {
                        SyntaxNode node = map.get(value).clone();
                        Objects.requireNonNull(node).setRequirement(SyntaxRequirement.OPTIONAL);
                        sn.addNode(node);
                    }
                    clear();
                    partCount ++;
                }
                if(c == '|') {
                    List<SyntaxNode> list = sn.getNodes(partCount);
                    if(sn.getType() != OR) {
                        SyntaxNode or = SyntaxNode.get(OR);
                        sn.addNode(or);
                        sn = or;
                    }
                    sn.addAllNode(list);
                    partCount = 0;
                }
            }
        }
        return recall(sn);
    }

    private static SyntaxNode recall(SyntaxNode node) {
        while(node.getParent() != null) {
            node = node.getParent();
        }
        return node;
    }

    private static String parseConst(char[] chars) {
        for(int i = pointer + 1; i < chars.length; i++) {
            if(chars[i] == '"') {
                pointer = i;
                break;
            } else {
                add(chars[i]);
            }
        }
        return get();
    }

    private static String parseOptional(char[] chars) {
        for(int i = pointer + 1; i < chars.length; i++) {
            if(chars[i] == ']') {
                pointer = i;
                break;
            } else {
                add(chars[i]);
            }
        }
        return get();
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
