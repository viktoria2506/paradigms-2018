package expression;

public class Min extends BinaryOperator implements CommonExpression {
    public Min(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) throws Exception {
        return a <= b ? a : b;
    }
}