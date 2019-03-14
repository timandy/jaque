package com.trigersoft.jaque.expression;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class BlockExpression extends Expression {

    private final List<Expression> expressions;

    BlockExpression(Class<?> resultType, List<Expression> expressions) {
        super(ExpressionType.Block, resultType);

        this.expressions = expressions;
    }

    @Override
    protected <T> T visit(ExpressionVisitor<T> v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        int returnIndex = 0;
        for (Expression e : expressions) {
            b.append('\n');
            returnIndex = b.length();
            b.append(e);
        }

        b.append('\n');
        if (getResultType() != Void.TYPE)
            b.insert(returnIndex, "return ");
        return b.toString();
    }
}
