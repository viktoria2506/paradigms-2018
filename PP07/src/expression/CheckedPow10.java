package expression;

public class CheckedPow10 implements CommonExpression {
    private CommonExpression value;
    public CheckedPow10(CommonExpression val) {
        value = val;
    }

    private void check(int a) throws Exception {
        if (a < 0 || a > 9) {
            throw new OverflowException();
        }
    }

    public int evaluate(int x, int y, int z) throws Exception {
        int a = value.evaluate(x, y, z);
        check(a);
        int pow = 1;
        int k = 10;
        while(a > 0) {
            if(a % 2 == 1) {
                pow *= k;
            }
            k *= k;
            a /= 2;
        }
        return pow;
    }
}