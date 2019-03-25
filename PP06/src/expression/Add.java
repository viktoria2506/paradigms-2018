package expression;

public class Add extends BinaryOperator implements CommonExpression {
    public Add(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a + b;
    }
}