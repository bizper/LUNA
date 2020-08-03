package com.luna.compile.utils.syntax.struct;

import com.luna.compile.utils.syntax.constant.SyntaxNodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.luna.compile.utils.syntax.constant.SyntaxNodeType.BASE;

public final class SyntaxNode {

    private static final double     BASE_FACTOR = 0.6;
    private static final    int     BASE_CAPACITY = 2 ^ 3;

    private SyntaxNode() { }

    private String name;

    /**
     * 0 base node, described as range or a single character
     * 1 link node, show as a collection contains much other nodes
     */
    private SyntaxNodeType type = BASE;

    private String[] value = new String[BASE_CAPACITY];

    private List<SyntaxNode> list = new ArrayList<>();

    private int cursor = 0;

    public void addValue(String str) {
        checkCapacity();
        value[cursor++] = str;
    }

    public SyntaxNode setType(SyntaxNodeType type) {
        this.type = type;
        return this;
    }

    public SyntaxNode setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    private void checkCapacity() {
        if(cursor >= value.length * BASE_FACTOR) {
            String[] newValue = new String[value.length << 1];
            System.arraycopy(value, 0, newValue, 0, value.length);
            value = newValue;
        }
    }

    public int size() {
        return cursor;
    }

    public void clear() {
        cursor = 0;
    }

    public String[] getValue() {
        return value;
    }

    public String getIndexValue(int index) {
        if(index >= cursor) return null;
        return value[index];
    }

    public void addNode(SyntaxNode node) {
        this.list.add(node);
    }

    public List<SyntaxNode> getNodes() {
        return list;
    }

    public static SyntaxNode get() {
        return new SyntaxNode();
    }

    public String toString() {
        return "<" + type + ":" + name + ">--" + (type == BASE ? Arrays.toString(value) : list);
    }

}
