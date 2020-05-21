package com.luna.compile.struct;

import com.luna.base.kits.StringKit;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.utils.TypeFinalizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TokenSequence implements StringElement {

    private List<Token> list = new ArrayList<>();

    @Override
    public String getElement() {
        return new StringJoiner(", ", TokenSequence.class.getSimpleName() + "[", "]")
                .add("list=" + StringKit.join(list))
                .toString();
    }

    public TokenSequence setList(List<Token> list) {
        this.list = list;
        return this;
    }

    

    public List<Token> getList() {
        return list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Token get(int i) {
        return list.get(i);
    }

    public Token head() {
        return list.get(0);
    }

    public Token tail() {
        return list.get(list.size() - 1);
    }

    public boolean headMatch(Token token) {
        if(head().check(token.getType(), token.getValue())) {
            cursor = 1;
            return true;
        }
        return false;
    }

    public void resetMetaInfo(int line, int fromColumn) {
        for(Token token : list) {
            token.setLine(line);
            token.setCol(fromColumn);
            fromColumn += token.getValue().length() + 1;
        }
    }

    public TOKEN getType() {
        return TypeFinalizer.derive(list).getData().getType();
    }

    public int getCursor() {
        return cursor;
    }

    public int size() {
        return list.size();
    }

    private int cursor = 0;

    public boolean nextMatch(Token token) {
        return get(cursor++).check(token.getType(), token.getValue());
    }

    public boolean isTail() {
        return cursor == list.size();
    }

    private TokenSequence() {}

    public static TokenSequence getInstance(List<Token> list) {
        return new TokenSequence().setList(list);
    }

    public static TokenSequence getInstance(List<Token> list, int fromIndex, int toIndex) {
        return new TokenSequence().setList(list.subList(fromIndex, toIndex));
    }

    public static TokenSequence getInstance(List<Token> list, int fromIndex, int toIndex, Predicate<? super Token> condition) {
        return new TokenSequence().setList(list.subList(fromIndex, toIndex).stream().filter(condition).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return StringKit.join(list, " ");
    }
}
