package com.luna.compile.struct;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String name;

    private Object value;

    private List<Node> list;

    private Node() {
        list = new ArrayList<>();
    }

    public void addNode(Node node) {
        list.add(node);
    }

    public Node head() {
        return list.get(0);
    }

    public Node tail() {
        return list.get(list.size() - 1 < 0 ? 0 : list.size() - 1);
    }

    public Node setName(String name) {
        this.name = name;
        return this;
    }

    public Node setValue(Object value) {
        this.value = value;
        return this;
    }

    public Node setList(List<Node> list) {
        this.list = list;
        return this;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public List<Node> getList() {
        return list;
    }
}
