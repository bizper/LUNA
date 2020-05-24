package com.luna.compile.utils.syntax.struct;

import com.luna.compile.utils.syntax.constant.SyntaxNodeType;

import java.util.ArrayList;
import java.util.List;

public final class SyntaxNode {

    private SyntaxNode() { }

    private String name;

    /**
     * 0 base node, described as range or a single character
     * 1 link node, show as a collection contains much other nodes
     */
    private SyntaxNodeType type = SyntaxNodeType.BASE;

    private String value;

    @Override
    public String toString() {
        return "<" + name + ", values=\"" + value + "\">";
    }

    public SyntaxNode setType(SyntaxNodeType type) {
        this.type = type;
        return this;
    }

    public SyntaxNode setValue(String values) {
        this.value = values;
        return this;
    }

    public SyntaxNodeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public SyntaxNode setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }


    public static SyntaxNode create(String name, String value) {
        return new SyntaxNode().setName(name).setValue(value);
    }

    public static SyntaxNode create(String name) {
        return new SyntaxNode().setName(name);
    }

}
