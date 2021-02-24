package com.luna.compile.utils.syntax.struct;

import com.luna.compile.utils.syntax.constant.SyntaxNodeType;

public final class SyntaxNode {

    private SyntaxNode prev;

    private SyntaxNode next;

    private SyntaxNodeType type;

    private String value;

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
