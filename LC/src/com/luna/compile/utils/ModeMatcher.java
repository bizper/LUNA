package com.luna.compile.utils;

import com.luna.compile.struct.Mode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对输入的token流进行模式匹配
 * 除去TOKEN的名字为关键字外，可以使用如下的方式去匹配
 * SYMBOL *
 */
public class ModeMatcher {

    public static Atom compile(String mode) {
        String[] atoms = mode.split("\\s");
        Atom root = new Atom();
        Atom cache = root;
        for(String str : atoms) {
            root.setType(str);
            root.setNext(new Atom());
            root = root.getNext();
        }
        if(root.getType() == null) root.getParent().setNext(null);
        System.out.println(cache);
        return cache;
    }

    public static class Atom {

        private Atom next;
        private Atom parent;
        private String type;

        public String toString() {
            return next != null ? type + " -> " + next.toString() : type;
        }

        public Atom setNext(Atom next) {
            this.next = next;
            if(next != null) next.parent = this;
            return this;
        }

        public Atom getParent() {
            return parent;
        }

        public Atom setParent(Atom parent) {
            this.parent = parent;
            return this;
        }

        public Atom setType(String type) {
            this.type = type;
            return this;
        }

        public String getType() {
            return type;
        }

        public Atom getNext() {
            return next;
        }

        public <T extends Mode> boolean match(List<T> list) {
            if(list.isEmpty()) return false;
            if(!list.get(0).mode().equals(type) && !type.equals("*")) return false;
            Atom root = this;
            for(int i = 1; i<list.size(); i++) {
                T t = list.get(i);
                Atom cache = root.getNext(t);
                if(cache == null) return false;
                root = cache;
            }
            return true;
        }

        public <T extends Mode> Atom getNext(T t) {
            if(next != null && (next.type.equals(t.mode()) || next.type.equals("*"))) return next;
            return null;
        }
    }

}
