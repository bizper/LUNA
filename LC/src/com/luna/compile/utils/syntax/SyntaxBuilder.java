package com.luna.compile.utils.syntax;

import com.luna.compile.utils.syntax.struct.SyntaxNode;

public class SyntaxBuilder {

    public SyntaxBuilder() {}

    public static SyntaxBuilder getBuilder() {
        return new SyntaxBuilder();
    }

    public SyntaxNode start() {
        return new SyntaxNode();
    }

}
