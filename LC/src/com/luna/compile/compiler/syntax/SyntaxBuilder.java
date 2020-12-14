package com.luna.compile.compiler.syntax;

import com.luna.compile.compiler.syntax.struct.Root;

public class SyntaxBuilder {

    private Root root;

    public SyntaxBuilder build() {
        return this;
    }

    public SyntaxBuilder and() {
        return this;
    }

    public SyntaxBuilder or() {
        return this;
    }

    public SyntaxBuilder string() {
        return this;
    }

    public SyntaxBuilder operator() {
        return this;
    }

    public SyntaxBuilder number() {
        return this;
    }

    public Root get() {
        return root;
    }

}
