package expression;

public class CheckedLog10 implements CommonExpression {
    private CommonExpression value;
    public CheckedLog10(CommonExpression val) {
        value = val;
    }

    protected void check(int a) throws Exception {
        if (a <= 0) {
            throw new Exception("Log by negative number.");
        }
    }

    public int evaluate(int x, int y, int z) throws Exception {
        int val = value.evaluate(x, y, z);
        check(val);
        int log = 0;
        int pow = 1;

        while (pow <= val / 10) {
            ++log;
            pow *= 10;
        }

        return log;
    }
}