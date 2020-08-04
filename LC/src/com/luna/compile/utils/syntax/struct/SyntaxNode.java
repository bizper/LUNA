package com.luna.compile.utils.syntax.struct;

import com.luna.compile.utils.syntax.constant.Requirement;
import com.luna.compile.utils.syntax.constant.SyntaxNodeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.luna.compile.utils.syntax.constant.Requirement.*;
import static com.luna.compile.utils.syntax.constant.SyntaxNodeType.*;

public final class SyntaxNode implements Cloneable {

    private SyntaxNode() { }

    private SyntaxNode(SyntaxNodeType type) {
        this.type = type;
    }

    private SyntaxNodeType type = LINK;

    private Requirement requirement = REQUIRED;

    private String value;

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setType(SyntaxNodeType type) {
        this.type = type;
    }

    public SyntaxNodeType getType() {
        return type;
    }

    private String name;

    private final List<SyntaxNode> list = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addNode(SyntaxNode node) {
        this.list.add(node);
    }

    public List<SyntaxNode> getNodes() {
        return list;
    }

    public List<SyntaxNode> getNodes(int length) {
        int size = list.size() - length;
        List<SyntaxNode> nonLinkNodes = list.stream().skip(size).collect(Collectors.toList());
        for(int j = list.size() - 1; j >= size; j--) {
            list.remove(j);
        }
        return nonLinkNodes;
    }

    public void addAllNode(Collection<SyntaxNode> collection) {
        this.list.addAll(collection);
    }

    public static SyntaxNode get() {
        return new SyntaxNode();
    }

    public static SyntaxNode get(SyntaxNodeType type) {
        return new SyntaxNode(type);
    }
    //<BIN NUMBER>[<0><1>]
    //<DEC NUMBER>[[-]<BIN NUMBER>[<0><1>]<2>....]
    public String toString() {
        if(type == CONST) return "<" + type + (requirement == OPTIONAL ? ":" + requirement : "") + ":" + value + ">" ;
        if(type == LINK) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("|--<").append(type).append(requirement == OPTIONAL ? ":" + requirement : "").append(":").append(name).append(">").append('\n');
            int i = 0;
            for(SyntaxNode node : list) {
                if(node == this) {
                    stringBuilder.append("  ");
                    stringBuilder.append("|--<").append(node.getName()).append(":").append(node.getType()).append(">").append('\n');
                } else {
                    stringBuilder.append(node.toString(this, i + 1));
                }

            }
            return stringBuilder.toString();
        }
        return "";
    }

    public String toString(SyntaxNode root, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<length; i++) {
            stringBuilder.append("  ");
        }
        if(type == CONST) stringBuilder.append("|--<").append(type).append(requirement == OPTIONAL ? ":" + requirement : "").append(":").append(value).append(">").append('\n');
        if(type == LINK) {
            stringBuilder.append("|--<").append(type).append(requirement == OPTIONAL ? ":" + requirement : "").append(":").append(name).append(">").append('\n');
            for(SyntaxNode node : list) {
                if(node == root) {
                    for(int i = 0; i<length + 1; i++) {
                        stringBuilder.append("  ");
                    }
                    stringBuilder.append("|--<").append(node.getName()).append(":").append(node.getType()).append(">").append('\n');
                } else {
                    stringBuilder.append(node.toString(this, length + 1));
                }

            }

        }
        return stringBuilder.toString();
    }

    public SyntaxNode clone() {
        try {
            return (SyntaxNode) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
