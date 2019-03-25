package expression;

public class Const implements CommonExpression {
	private int value;
    public Const(int val) {
        value = val;
    }
    public int evaluate(int x, int y, int z) {
        return value;
    }
}