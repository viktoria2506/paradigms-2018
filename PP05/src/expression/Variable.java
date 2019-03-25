package expression;

public class Variable implements CommonInterface {
	private String name;
    public Variable(String nam) {
        name = nam;
    }

    public int evaluate(int x) {
        return x;
    }

    public double evaluate(double x) {
        return x;
    }

}