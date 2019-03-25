package expression;

abstract public class BinaryOperator implements CommonExpression {
    protected CommonExpression fOp;
    protected CommonExpression sOp;

    BinaryOperator(CommonExpression first, CommonExpression second) {
        fOp = first;
        sOp = second;
    }

    abstract protected int apply(int a, int b) throws Exception;

    public int evaluate(int x, int y, int z) throws Exception {
        return apply(fOp.evaluate(x, y, z), sOp.evaluate(x, y, z));
    }
}