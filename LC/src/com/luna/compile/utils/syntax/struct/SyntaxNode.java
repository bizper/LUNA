package com.luna.compile.utils.syntax.struct;

import com.luna.compile.utils.syntax.constant.SyntaxNodeType;

import java.util.ArrayList;
import java.util.List;

public final class SyntaxNode {

    private SyntaxNode() {
        values = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    private String name;

    /**
     * 0 base node, described as range or a single character
     * 1 link node, show as a collection contains much other nodes
     */
    private SyntaxNodeType type;

    private List<String> values;

    private List<SyntaxNode> nodes;

    @Override
    public String toString() {
        return "<" + name +
                (type == SyntaxNodeType.BASE ? ", values=" + values : ", nodes=" + nodes) + '>';
    }

    public SyntaxNode setType(SyntaxNodeType type) {
        this.type = type;
        return this;
    }

    public SyntaxNode setValues(List<String> values) {
        this.values = values;
        return this;
    }

    public SyntaxNode addValue(String value) {
        this.values.add(value);
        return this;
    }

    public SyntaxNode addValue(List<String> value) {
        this.values.addAll(value);
        return this;
    }

    public SyntaxNodeType getType() {
        return type;
    }

    public List<String> getValues() {
        return values;
    }

    public SyntaxNode setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setNodes(List<SyntaxNode> nodes) {
        this.nodes = nodes;
    }

    public SyntaxNode addNode(SyntaxNode node) {
        nodes.add(node);
        return this;
    }

    public List<SyntaxNode> getNodes() {
        return nodes;
    }

    public static SyntaxNode create(String name, List<String> values) {
        return new SyntaxNode().setName(name).setValues(values);
    }

    public static SyntaxNode create(String name, SyntaxNode node) {
        return new SyntaxNode().setName(name).addNode(node);
    }

    public static SyntaxNode create(String name, String value) {
        return new SyntaxNode().setName(name).addValue(value);
    }

    public static SyntaxNode create(String name) {
        return new SyntaxNode().setName(name);
    }

}
