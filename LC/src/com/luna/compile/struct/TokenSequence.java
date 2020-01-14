package com.luna.compile.struct;

import com.luna.base.kits.StringKit;

import java.util.ArrayList;
import java.util.List;
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
        return StringKit.join(list);
    }
}
