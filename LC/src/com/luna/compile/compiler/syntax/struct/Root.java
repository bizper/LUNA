package com.luna.compile.compiler.syntax.struct;

import com.luna.compile.struct.TokenSequence;

public class Root implements SyntaxNode {

    public Root() {

    }

    public Root(TokenSequence ts) {

    }

    @Override
    public void addNode(SyntaxNode node) {

    }

    @Override
    public boolean accept(SyntaxNode node) {
        return false;
    }
}
