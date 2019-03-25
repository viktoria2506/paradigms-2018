package expression.parser;
import expression.*;
import expression.ParsingException;

public class ExpressionParser implements expression.exceptions.Parser {
    private int index;
    private String expression;
    private int constant;
    private char variable;
    private enum State {number, plus, minus, asterisk, slash, lparen, rparen, variable, log10, pow10}
    private State current;

    private char getNextChar() {
        if (index < expression.length()) {
            char ret =  expression.charAt(index);
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

    private void checkSymbol(int pos) throws Exception {
        if(pos == expression.length()) {
            throw new ParsingException("No parameter of function", expression, generatePos(pos));
        }
        if(Character.isDigit(expression.charAt(pos)) || Character.isAlphabetic(expression.charAt(pos))) {
            throw new ParsingException("unexpected function", expression, generatePos(pos));
        }
    }

    private static String generatePos(int pos) {
        StringBuilder t = new StringBuilder();
        for(int i = 0 ; i < pos ; ++i) t.append(' ');
        t.append('^');
        return t.toString();
    }

    private void getNext() throws Exception {
        skipWhitespace();
        char ch = getNextChar();
        if (Character.isDigit(ch)) {
            StringBuilder str = new StringBuilder();
            while (Character.isDigit(ch)) {
                str.append(ch);
                ch = getNextChar();
            }
            index--;
            try {
                constant = Integer.parseInt(str.toString());
            } catch (NumberFormatException e) {
                throw new OverflowException();
            }
            current = State.number;
        } else if (ch == '+') {
            current = State.plus;
        } else if (ch == '-') {
            if (expression.length() >= index + 10 && expression.substring(index, index + 10).equals("2147483648")) {
                constant = Integer.parseInt(expression.substring(index - 1, index + 10));
                index += 10;
                current = State.number;
            } else {
                current = State.minus;
            }
        } else if (ch == '*') {
            current = State.asterisk;
        } else if (ch == '/') {
            current = State.slash;
        } else if (ch == '(') {
            current = State.lparen;
        } else if (ch == ')') {
            current = State.rparen;
        } else if (ch == 'x' || ch == 'y' || ch == 'z') {
            current = State.variable;
            variable = ch;
        } else {
            if (expression.length() >= index + 4 && expression.substring(index - 1, index + 4).equals("log10")) {
                checkSymbol(index + 4);
                index += 4;
                current = State.log10;
            } else
            if (expression.length() >= index + 4 && expression.substring(index - 1, index + 4).equals("pow10")) {
                checkSymbol(index + 4);
                index += 4;
                current = State.pow10;
            }
            else if (!Character.isWhitespace(ch)) {
                throw new ParsingException("unexpected char: \"" + ch + "\" at index: " + (index - 1), expression, generatePos(index - 1));
            }
        }
        skipWhitespace();
    }

    private CommonExpression atomic() throws Exception {
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
                ret = new CheckedNegate(atomic());
                break;

            case log10:
                ret = new CheckedLog10(atomic());
                break;

            case pow10:
                ret = new CheckedPow10(atomic());
                break;

            case lparen:
                ret = addSubt();
                if (current != State.rparen) {
                    System.out.println(") missing");
                    System.exit(0);
                }
                getNext();
                break;

            default:
                throw new ParsingException("unrecognizable symbol at position: " + index, expression, generatePos(index - 2));
        }
        return ret;
    }

    private CommonExpression mulDiv() throws Exception {
        CommonExpression left = atomic();
        while(true) {
            switch(current) {
                case asterisk:
                    left = new CheckedMultiply(left, atomic());
                    break;

                case slash:
                    left = new CheckedDivide(left, atomic());
                    break;

                default:
                    return left;
            }
        }
    }

    private CommonExpression addSubt() throws Exception {
        CommonExpression left = mulDiv();
        while (true) {
            switch(current) {
                case minus:
                    left = new CheckedSubtract(left, mulDiv());
                    break;

                case plus:
                    left = new CheckedAdd(left, mulDiv());
                    break;

                default:
                    return left;
            }
        }
    }

    public CommonExpression parse(String expr) throws Exception {
        index = 0;
        constant = 0;
        current = null;
        expression = expr;
        int bb = 0;
        for (int i = 0; i < expression.length() ; i++) {
            if (expression.charAt(i) == '(') {
                bb++;
            } else if(expression.charAt(i) == ')') {
                bb--;
            }
            if (bb < 0) {
                throw new ParsingException("unexpected ) at position: " + (i + 1), expression, generatePos(i));
            }
        }
        if (bb != 0) {
            throw new ParsingException("expected ) at end");
        }

        return addSubt();
    }
}