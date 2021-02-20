package com.luna.compile.utils.syntax;

import com.luna.compile.utils.syntax.struct.SyntaxNode;

public class SyntaxBuilder {

    public SyntaxBuilder() {}

    public static SyntaxBuilder getBuilder() {
        return new SyntaxBuilder();
    }

    public SyntaxNode start() {
        return null;
    }

    public SyntaxNode and(SyntaxNode... nodes) {
        return null;
    }

    public SyntaxNode or(SyntaxNode... nodes) {
        return null;
    }

    public SyntaxNode content(String content) {
        return null;
    }

    public SyntaxNode end() {
        return null;
    }

}
