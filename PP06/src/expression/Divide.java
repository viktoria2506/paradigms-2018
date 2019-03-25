package expression;

public class Divide extends BinaryOperator implements CommonExpression {
    public Divide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a / b;
    }
}