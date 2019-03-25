package expression;

public class CheckedNegate implements CommonExpression {
    private CommonExpression value;
    public CheckedNegate(CommonExpression val) {
        value = val;
    }

    private void check(int a) throws Exception {
        if (a <= Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public int evaluate(int x, int y, int z) throws Exception {
        int a = value.evaluate(x, y, z);
        check(a);
        return -a;
    }
}