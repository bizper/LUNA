package com.luna.compile.struct;

import com.luna.base.io.OUT;
import com.luna.base.kits.StringKit;
import com.luna.base.result.Bean;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.struct.intf.StringElement;
import com.luna.compile.utils.BaseFinalizer;
import com.luna.compile.utils.TokenUtil;
import com.luna.compile.utils.TypeFinalizer;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TokenSequence extends BaseFinalizer implements StringElement, Iterable<Token> {

    @Override
    public Iterator<Token> iterator() {
        return list.iterator();
    }

    @Override
    public void forEach(Consumer<? super Token> action) {
        list.forEach(action);
    }

    @Override
    public Spliterator<Token> spliterator() {
        return list.spliterator();
    }

    private boolean checked = false;

    private List<Token> list = new ArrayList<>();

    private int line;

    public TokenSequence setLine(int line) {
        this.line = line;
        return this;
    }


    public int getLine() {
        return line;
    }

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

    public TokenSequence[] split(String str) {
        int key = 0;
        for(Token token : list) {
            if(token.getValue().equals(str)) break;
            key ++;
        }
        if(key == 0) return new TokenSequence[]{this};
        else return new TokenSequence[]{TokenSequence.getInstance(list, 0, key), TokenSequence.getInstance(list, key + 1, this.size())};
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

    public TokenSequence setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public Bean<TOKEN> getType() {
        Bean<TokenRepresent> bean = TypeFinalizer.derive(list);
        if(bean.isSuccess()) {
            return build(true, bean.getData().getType());
        }
        return build(false, bean.getMessage(), null);
    }

    public boolean isChecked() {
        return checked;
    }

    public int getCursor() {
        return cursor;
    }

    public int size() {
        return list.size();
    }

    private int cursor = 0;

    public void reset() {
        cursor = 0;
    }

    public boolean nextMatch(Token token) {
        return get(cursor++).check(token.getType(), token.getValue());
    }

    public boolean isTail() {
        return cursor == list.size();
    }

    /**
     * static method area
     */

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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TokenSequence)) {
            return false;
        }
        TokenSequence ts = (TokenSequence) obj;
        return this.toString().equals(ts.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
