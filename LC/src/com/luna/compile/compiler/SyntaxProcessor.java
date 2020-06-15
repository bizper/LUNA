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
                            //如果右边为静态表达式（不包含符号引用和非静态函数引用，一切信息在编译器已得知），将直接计算表达式结果
                            if(SyntaxParser.match("STATIC_EXPR", tokenSequences[1].toString())) {
                                TypeFinalizer.derive(tokenSequences[1].getList());
                                setNodeValue(ExpressionFinalizer.calculate(tokenSequences[1]));
                            } else {//如果不是就将表达式组成语法树
                                parseExpr();
                            }
                            break;
                        case "":
                            break;
                    }
                }
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
     * noticed that this method wont do any null-checking operations.
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
