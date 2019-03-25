package expression.parser;

import expression.*;
import org.omg.CORBA.PRIVATE_MEMBER;

public class ExpressionParser implements Parser {
    private int index;
    private String expression;
    private int constant;
    private char variable;

    private enum State {number, plus, minus, asterisk, slash, lparen, rparen, variable, and, or, xor}

    private State current;

    private char getNextChar() {
        if (index < expression.length()) {
            char ret = expression.charAt(index);
            index++;
            return ret;
        } else {
            return '#';
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(getNextChar())) {
        }
        index--;
    }

    private void getNext() {
        skipWhitespace();
        char ch = getNextChar();
        if (Character.isDigit(ch)) {
            StringBuilder str = new StringBuilder();
            while (Character.isDigit(ch)) {
                str.append(ch);
                ch = getNextChar();
            }
            index--;
            constant = Integer.parseUnsignedInt(str.toString());
            current = State.number;
        } else {
            switch (ch) {
                case '+':
                    current = State.plus;
                    break;
                case '-':
                    current = State.minus;
                    break;
                case '*':
                    current = State.asterisk;
                    break;
                case '/':
                    current = State.slash;
                    break;
                case '&':
                    current = State.and;
                    break;
                case '|':
                    current = State.or;
                    break;
                case '^':
                    current = State.xor;
                    break;
                case '(':
                    current = State.lparen;
                    break;
                case ')':
                    current = State.rparen;
                    break;
                case 'x':
                case 'y':
                case 'z':
                    current = State.variable;
                    variable = ch;
                    break;
            }
        }
        skipWhitespace();
    }

    private CommonExpression atomic() {
        getNext();
        CommonExpression ret;
        switch (current) {
            case number:
                ret = new Const(constant);
                getNext();
                break;

            case variable:
                ret = new Variable(Character.toString(variable));
                getNext();
                break;

            case minus:
                ret = new Subtract(new Const(0), atomic());
                break;

            case lparen:
                ret = ors();
                if (current != State.rparen) {
                    System.out.println(") missing");
                    System.exit(0);
                }
                getNext();
                break;

            default:
                ret = null;
                System.out.println("Unrecognizable format");
                System.exit(0);
                break;
        }
        return ret;
    }

    private CommonExpression mulDiv() {
        CommonExpression left = atomic();
        while (true) {
            switch (current) {
                case asterisk:
                    left = new Multiply(left, atomic());
                    break;

                case slash:
                    left = new Divide(left, atomic());
                    break;

                default:
                    return left;
            }
        }
    }

    private CommonExpression addSubt() {
        CommonExpression left = mulDiv();
        while (true) {
            switch (current) {
                case minus:
                    left = new Subtract(left, mulDiv());
                    break;

                case plus:
                    left = new Add(left, mulDiv());
                    break;

                default:
                    return left;
            }
        }
    }

    private CommonExpression ands() {
        CommonExpression left = addSubt();
        while (true) {
            switch (current) {
                case and:
                    left = new And(left, addSubt());
                    break;

                default:
                    return left;
            }
        }
    }

    private CommonExpression xors() {
        CommonExpression left = ands();
        while (true) {
            switch (current) {
                case xor:
                    left = new Xor(left, ands());
                    break;

                default:
                    return left;
            }
        }
    }

    private CommonExpression ors() {
        CommonExpression left = xors();
        while (true) {
            switch (current) {
                case or:
                    left = new Or(left, xors());
                    break;

                default:
                    return left;
            }
        }
    }

    public CommonExpression parse(String expression) {
        index = 0;
        constant = 0;
        current = null;
        this.expression = expression;
        return ors();
    }

}
