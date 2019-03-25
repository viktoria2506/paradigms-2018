package expression;

public class CheckedSubtract extends BinaryOperator implements CommonExpression {
    public CheckedSubtract(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected void check(int a, int b) throws Exception {
        if(b > 0 && a < Integer.MIN_VALUE + b) {
            throw new OverflowException();
        }
        if(b < 0 && a > Integer.MAX_VALUE + b) {
            throw new OverflowException();
        }
    }

    protected int apply(int a, int b) throws Exception {
        check(a, b);
        return a - b;
    }
}