package expression;

public class And extends BinaryOperator implements CommonExpression {
    public And(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a & b;
    }
}

