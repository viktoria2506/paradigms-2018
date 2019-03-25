package expression;

public class Multiply extends BinaryOperator implements CommonExpression {
    public Multiply(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a * b;
    }
}