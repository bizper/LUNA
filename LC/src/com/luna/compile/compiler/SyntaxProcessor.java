package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.compile.constant.NODE;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Module;
import com.luna.compile.struct.Node;
import com.luna.compile.struct.TokenSequence;
import com.luna.compile.utils.ExpressionFinalizer;
import com.luna.compile.utils.TypeFinalizer;
import com.luna.compile.utils.syntax.SyntaxMatcher;
import com.luna.compile.utils.syntax.SyntaxParser;
import com.luna.compile.utils.syntax.struct.SyntaxNode;

/**
 * 对于词法进行分析，词法的根基为根目录下的basic.sy文件
 */
public class SyntaxProcessor extends Component {

    private Node root;

    private SyntaxProcessor() {
        root = Node.create();
        SyntaxMatcher.init();
    }

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new SyntaxProcessor();
        return instance;
    }

    @Override
    public Component run(Context context, Config config) {
        super.run(context, config);
        for(final Module module : context.getModules()) {
            for(TokenSequence ts : module.getList()) {

            }
        }
        recall();
        OUT.info(root);
        return this;
    }

    private void parseExpr() {

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
     * noticed that this method wont do any null-check operations.
     */
    private void back() {
        root = root.getParent();
    }

    private void safetyBack() {
        if(root.getParent() != null) root = root.getParent();
    }

    public boolean isNull() {
        return root == null;
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
