package expression;

public class Xor extends BinaryOperator implements CommonExpression {
    public Xor(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a ^ b;
    }
}

