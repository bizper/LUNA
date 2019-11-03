package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.compile.constant.NODE;
import com.luna.compile.constant.TOKEN;
import com.luna.compile.constant.Vars;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Node;
import com.luna.compile.struct.Token;

import java.util.List;

/**
 * 对于语法进行分析，语法的根基为根目录下的basic.sy文件
 */
public class SyntaxProcessor extends Component {

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        for(final List<Token> list : context.getList()) {
            //generate(list);
        }
        return this;
    }

    private void generate(List<Token> list) {
        Node root = Node.create();
        Node cache = root;
        for(Token token : list) {
            if(check(token, TOKEN.SYMBOL)) {
                root.setName(NODE.METHOD_CALL);
                root.setValue(token.getValue());
                root.addNode(Node.create());
                root = root.head();
            }
            if(check(token, TOKEN.STRING)) {
                if(root.getParent().getName() == NODE.METHOD_CALL) {
                    root.setValue(token.getValue());
                    root.setName(NODE.CONSTANT);
                    root = root.getParent();
                    Node node = Node.create();
                    root.addNode(node);
                    root = node;
                }
            }
            if(check(token, TOKEN.OPERATOR, "=")) {
                if(root.getParent().getName() == NODE.METHOD_CALL) {
                    root.getParent().setName(NODE.METHOD_DEFINE);
                    
                }
            }
        }
        OUT.debug(cache);
    }

    private boolean check(Token token, TOKEN type, String value) {
        return token != null && token.getType() == type && token.getValue().equals(value);
    }

    private boolean check(Token token, TOKEN type) {
        return token != null && token.getType() == type;
    }

}
