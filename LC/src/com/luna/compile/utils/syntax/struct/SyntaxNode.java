package com.luna.compile.utils.syntax.struct;

import com.luna.compile.struct.Token;
import com.luna.compile.utils.syntax.constant.SyntaxRequirement;
import com.luna.compile.utils.syntax.constant.SyntaxNodeType;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.luna.compile.constant.TOKEN.*;
import static com.luna.compile.utils.syntax.constant.SyntaxRequirement.*;
import static com.luna.compile.utils.syntax.constant.SyntaxNodeType.*;

public final class SyntaxNode implements Serializable, Cloneable, Iterable<SyntaxNode> {

    private SyntaxNode() { }

    private SyntaxNode(SyntaxNodeType type) {
        this.type = type;
    }

    private SyntaxNodeType type = TERMINAL;

    private SyntaxRequirement requirement = REQUIRED;

    private String value;

    private String name;

    private SyntaxNode parent;

    public void setRequirement(SyntaxRequirement requirement) {
        this.requirement = requirement;
    }

    public SyntaxRequirement getRequirement() {
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

    public void setParent(SyntaxNode parent) {
        this.parent = parent;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    private final List<SyntaxNode> list = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addNode(SyntaxNode node) {
        node.setParent(this);
        this.list.add(node);
    }

    public List<SyntaxNode> getNodes() {
        List<SyntaxNode> cache = new ArrayList<>(list);
        list.clear();
        return cache;
    }

    public List<SyntaxNode> getNodes(int length) {
        int size = list.size() - length;
        List<SyntaxNode> nonLinkNodes = list.stream().skip(size).collect(Collectors.toList());
        for(int j = list.size() - 1; j >= size; j--) {
            list.remove(j);
        }
        return nonLinkNodes;
    }

    @Override
    public Iterator<SyntaxNode> iterator() {
        return list.iterator();
    }

    @Override
    public void forEach(Consumer<? super SyntaxNode> action) {
        list.forEach(action);
    }

    @Override
    public Spliterator<SyntaxNode> spliterator() {
        return list.spliterator();
    }

    public boolean match(Token token) {
        SyntaxNode pointer = this;
        SyntaxNode anchor = this;
        if(token.getType() == INTEGER || token.getType() == FLOAT || token.getType() == SYMBOL) {
            String value = token.getValue();
            char[] chars = value.toCharArray();
            for (char c : chars) {
                pointer = pointer.pick(c);
                if (pointer == null) return false;
                pointer = anchor;
            }
            return true;
        }
        if(token.getType() == STRING || token.getType() == OPERATOR) {
            pointer = pointer.pick(token.getValue());
            return pointer != null;
        }
        return false;
    }

    private boolean equals(char c) {
        return equals(String.valueOf(c));
    }

    private boolean equals(String value) {
        return this.value.equals(value);
    }

    private SyntaxNode pick(char c) {
        return pick(String.valueOf(c));
    }

    private SyntaxNode pick(String value) {
        for(SyntaxNode node : this) {
            if(node.getType() == OR) {
                return node.pick(value);
            }
            if(node.getType() == AND) {
                return node.pick(value);
            }
            if(node.getType() == TERMINAL) {
                for(SyntaxNode inside : node) {
                    SyntaxNode insideResult = inside.pick(value);
                    if(insideResult != null) return insideResult;
                }
            }
            if(node.getType() == CONST && node.equals(value)) return node;
        }
        return null;
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
        else {
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
    }

    public String toString(SyntaxNode root, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<length; i++) {
            stringBuilder.append("  ");
        }
        if(type == CONST) stringBuilder.append("|--<").append(type).append(requirement == OPTIONAL ? ":" + requirement : "").append(":").append(value).append(">").append('\n');
        else {
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
