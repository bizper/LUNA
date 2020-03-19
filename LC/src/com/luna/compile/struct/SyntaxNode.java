package com.luna.compile.struct;

import java.util.Arrays;
import java.util.List;

public class SyntaxNode {

    private String name;

    /**
     * 0 base node, described as range or a single character
     * 1 link node, show as a collection contains much other nodes
     */
    private int type;

    private String[] values;

    private List<SyntaxNode> nodes;

    @Override
    public String toString() {
        return "|----<" + name + '>' +
                (type == 0 ? ", values=" + Arrays.toString(values) : ", nodes=" + nodes) +
                ']';
    }

    public SyntaxNode setType(int type) {
        this.type = type;
        return this;
    }

    public SyntaxNode setValues(String[] values) {
        this.values = values;
        return this;
    }

    public int getType() {
        return type;
    }

    public String[] getValues() {
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

    public static SyntaxNode create(String name, String[] values) {
        return new SyntaxNode().setName(name).setValues(values);
    }

    public static SyntaxNode create(String name, SyntaxNode node) {
        return new SyntaxNode().setName(name).addNode(node);
    }

}
