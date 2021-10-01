package com.luna.compile.struct.syntax;

import java.util.Iterator;

public interface ASTree extends Iterable<ASTree> {

    ASTree child(int i);

    int count();

    Iterator<ASTree> children();

    String location();

    default Iterator<ASTree> iterator() {
        return children();
    }

}
