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


    @Override
    public String toString() {
        return "SyntaxNode [" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", values=" + Arrays.toString(values) +
                ']';
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public int getType() {
        return type;
    }

    public String[] getValues() {
        return values;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
