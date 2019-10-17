package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.compile.constant.Vars;
import com.luna.compile.struct.Context;

import java.util.List;

/**
 * 对于语法进行分析，语法的根基为根目录下的basic.sy文件
 */
public class SyntaxProcessor extends Component {

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        if(!check()) {
            OUT.err("");
            return this;
        }
        return this;
    }

    private void generate() {
        String syntaxPath = Vars.get("define_syntax_path");

    }

    private boolean check() {
        return true;
    }

    private void process() {

    }

    private class Node {

        private String value;
        private Node parent;
        private List<Node> list;

        @Override
        public String toString() {
            return "Node [" +
                    "value='" + value + '\'' + '\n' +
                    ", parent=" + parent + '\n' +
                    ", list=" + list + '\n' +
                    ']';
        }

        public Node setValue(String value) {
            this.value = value;
            return this;
        }

        public Node setParent(Node parent) {
            this.parent = parent;
            return this;
        }

        public Node setList(List<Node> list) {
            this.list = list;
            return this;
        }

        public String getValue() {
            return value;
        }

        public Node getParent() {
            return parent;
        }

        public List<Node> getList() {
            return list;
        }
    }

}
