package com.luna.compile.struct.syntax;

import java.util.Iterator;
import java.util.List;

public class ASTList implements ASTree {

    protected List<ASTree> children;

    public ASTList(List<ASTree> list) {
        children = list;
    }

    @Override
    public ASTree child(int i) {
        return children.get(i);
    }

    @Override
    public int count() {
        return children.size();
    }

    @Override
    public Iterator<ASTree> children() {
        return children.iterator();
    }

    @Override
    public String location() {
        for(ASTree t : children) {
            String loc = t.location();
            if(loc != null) return loc;
        }
        return null;
    }
}
