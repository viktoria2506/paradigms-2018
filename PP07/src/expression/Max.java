package expression;

public class Max extends BinaryOperator implements CommonExpression {
    public Max(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) throws Exception {
        return a <= b ? b : a;
    }
}