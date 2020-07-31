package com.luna.compile.struct;

import com.luna.compile.constant.NODE;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private NODE name;

    private Object value;

    private Node parent;

    private List<Node> list;

    private Node() {
        list = new ArrayList<>();
    }

    public void addNode(Node node) {
        list.add(node);
        node.setParent(this);
    }

    public Node head() {
        return list.get(0);
    }

    public Node tail() {
        return list.get(Math.max(list.size() - 1, 0));
    }

    public Node setName(NODE name) {
        this.name = name;
        return this;
    }

    public Node setParent(Node parent) {
        this.parent = parent;
        return this;
    }

    public Node getParent() {
        return parent;
    }

    public Node setValue(Object value) {
        this.value = value;
        return this;
    }

    public Node setList(List<Node> list) {
        this.list = list;
        return this;
    }

    public NODE getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public List<Node> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("|--<" + name + ":" + value + ">\n");
        for(Node node : list) {
            stringBuilder.append("  ").append(node);
        }
        return stringBuilder.toString();
    }

    public static Node create() {
        return new Node();
    }
}
