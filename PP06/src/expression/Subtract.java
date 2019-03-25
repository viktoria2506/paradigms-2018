package expression;

public class Subtract extends BinaryOperator implements CommonExpression {
    public Subtract(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a - b;
    }
}