package com.luna.compile.struct;

public class Expression {

    private String expression;

    @Override
    public String toString() {
        return "Expression [" +
                "expression='" + expression + '\'' + '\n' +
                ']';
    }

    public Expression setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public Expression setExpression(Token... list) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Token token : list) {
            stringBuilder.append(token.getValue());
        }
        this.expression = stringBuilder.toString();
        return this;
    }

    public String getExpression() {
        return expression;
    }
}
