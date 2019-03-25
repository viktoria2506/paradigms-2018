package expression;

public class Or extends BinaryOperator implements CommonExpression {
    public Or(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a | b;
    }
}

