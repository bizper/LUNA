package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.compile.constant.NODE;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Module;
import com.luna.compile.struct.Node;
import com.luna.compile.struct.TokenSequence;
import com.luna.compile.utils.TypeFinalizer;
import com.luna.compile.utils.syntax.SyntaxParser;
import com.luna.compile.utils.syntax.struct.SyntaxNode;

/**
 * 对于语法进行分析，语法的根基为根目录下的basic.sy文件
 */
public class SyntaxProcessor extends Component {

    private Node root;

    private SyntaxProcessor() {
        root = Node.create();
        SyntaxParser.init();
    }

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new SyntaxProcessor();
        return instance;
    }

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        for(final Module module : context.getModules()) {
            for(TokenSequence ts : module.getList()) {
                SyntaxNode sn;
                if((sn = SyntaxParser.match(ts.toString())) != null) {
                    switch(sn.getName()) {
                        case "VARIABLE":
                            TokenSequence[] tokenSequences = ts.split("=");
                            setNodeName(NODE.METHOD_DEFINE);
                            setNodeValue(tokenSequences[0].toString());
                            change();
                            setNodeName(NODE.METHOD_PARAM);
                            if(SyntaxParser.getMap().get("STATIC_EXPR").match(tokenSequences[1].toString())) {
                                TypeFinalizer.derive(tokenSequences[1].getList());
                                setNodeValue(tokenSequences[1].toString());
                            } else {
                                //TODO
                            }
                            break;
                    }
                }
            }
        }
        recall();
        OUT.info(root);
        return this;
    }

    private void recall() {
        while(root.getParent() != null) {
            root = root.getParent();
        }
    }

    private void change() {
        Node node = Node.create();
        root.addNode(node);
        root = node;
    }

    /**
     * noticed that this method wont do any null-checking operations.
     */
    private void back() {
        root = root.getParent();
    }

    private void setNodeName(NODE name) {
        root.setName(name);
    }

    private void setNodeValue(Object obj) {
        root.setValue(obj);
    }

    private void addChild(Node child) {
        root.addNode(child);
    }

}
