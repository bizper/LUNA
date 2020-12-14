package com.luna.compile.compiler.syntax.struct;

public interface SyntaxNode {

    void addNode(SyntaxNode node);

    boolean accept(SyntaxNode node);

}
