package com.luna.compile.struct;

import java.util.List;

public class Module {

    private List<TokenSequence> list;

    private String name;

    private Module() {}

    public Module setList(List<TokenSequence> list) {
        this.list = list;
        return this;
    }

    public Module setName(String name) {
        this.name = name;
        return this;
    }

    public List<TokenSequence> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public static Module get(String name, List<TokenSequence> list) {
        return new Module().setName(name).setList(list);
    }

    public static Module get() {
        return new Module();
    }

    public int length() {
        return list.size();
    }

    public void remove(int line) {
        list.stream().filter((e) -> e.getLine() == line).findFirst().ifPresent(ts -> list.remove(ts));
    }

    @Override
    public String toString() {
        return "<M'" +  name + '\'' +
                '>';
    }
}
