import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class SolveTheExpression {

    static final int MASK = 511; // 9 bits

    static int[] digitMask = new int[10];

    static Map<Integer, Character> symbols = new HashMap<>();
    static Map<Integer, Character> resultDigits = new HashMap<>();

    static String expression;
    static int pos;

    // Convert a 3x3 segment into its 9-bit value
    static int getPattern(String[] rows, int index) {
        int bits = 0;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                int col = index * 3 + c;

                char ch = col < rows[r].length()
                        ? rows[r].charAt(col)
                        : ' ';

                bits <<= 1;

                if (ch != ' ' && ch != '\t' &&
                        ch != '\u00A0') {
                    bits |= 1;
                }
            }
        }

        return bits;
    }

    // Stores binary value and its bit length
    static class Value {
        BigInteger bits;
        int width;

        Value(BigInteger bits, int width) {
            this.bits = bits;
            this.width = width;
        }
    }

    // Lowest precedence: AND
    static Value parseAnd() {
        Value left = parseOr();

        while (pos < expression.length() &&
                expression.charAt(pos) == '&') {

            pos++;
            Value right = parseOr();

            left = new Value(
                    left.bits.and(right.bits),
                    Math.max(left.width, right.width));
        }

        return left;
    }

    // OR has higher precedence than AND
    static Value parseOr() {
        Value left = parseNot();

        while (pos < expression.length() &&
                expression.charAt(pos) == '|') {

            pos++;
            Value right = parseNot();

            left = new Value(
                    left.bits.or(right.bits),
                    Math.max(left.width, right.width));
        }

        return left;
    }

    // Highest operator precedence: NOT
    static Value parseNot() {

        if (expression.charAt(pos) == '~') {
            pos++;

            Value val = parseNot();

            // Flip only the bits belonging to the operand
            BigInteger mask = BigInteger.ONE
                    .shiftLeft(val.width)
                    .subtract(BigInteger.ONE);

            return new Value(
                    val.bits.xor(mask),
                    val.width);
        }

        // Handle brackets
        if (expression.charAt(pos) == '(') {
            pos++;

            Value val = parseAnd();

            pos++; // Skip ')'
            return val;
        }

        // Read a complete multi-digit number
        BigInteger num = BigInteger.ZERO;
        int width = 0;

        while (pos < expression.length() &&
                Character.isDigit(expression.charAt(pos))) {

            int digit = expression.charAt(pos++) - '0';

            // Append its 9-bit representation
            num = num.shiftLeft(9).or(
                    BigInteger.valueOf(digitMask[digit]));

            width += 9;
        }

        return new Value(num, width);
    }

    static void solve(BufferedReader br) throws Exception {

        String[] numberRows = new String[3];
        String[] operatorRows = new String[3];
        String[] expressionRows = new String[3];

        // Read digit patterns
        for (int i = 0; i < 3; i++) {
            numberRows[i] = br.readLine();
        }

        // Read operator patterns
        for (int i = 0; i < 3; i++) {
            operatorRows[i] = br.readLine();
        }

        // Read expression patterns
        for (int i = 0; i < 3; i++) {
            expressionRows[i] = br.readLine();
        }

        // Map patterns to digits
        for (int d = 0; d < 10; d++) {

            int bits = getPattern(numberRows, d);

            digitMask[d] = bits;

            symbols.put(bits, (char) ('0' + d));
            resultDigits.put(bits, (char) ('0' + d));
        }

        // Operators in the specified input order
        char[] operators = { '|', '&', '~', '(', ')' };

        for (int i = 0; i < 5; i++) {

            int bits = getPattern(operatorRows, i);

            symbols.put(bits, operators[i]);
        }

        // Determine the number of encoded characters
        int maxWidth = 0;

        for (String row : expressionRows) {
            maxWidth = Math.max(maxWidth, row.length());
        }

        int count = (maxWidth + 2) / 3;

        // Decode the expression
        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < count; i++) {
            int bits = getPattern(expressionRows, i);

            Character ch = symbols.get(bits);

            if (ch != null) {
                decoded.append(ch);
            }
        }

        expression = decoded.toString();
        pos = 0;

        // Evaluate according to custom precedence
        Value result = parseAnd();

        // Convert result into 9-bit groups
        StringBuilder answer = new StringBuilder();

        int groups = result.width / 9;

        for (int i = groups - 1; i >= 0; i--) {

            int bits = result.bits
                    .shiftRight(i * 9)
                    .and(BigInteger.valueOf(MASK))
                    .intValue();

            answer.append(resultDigits.get(bits));
        }

        // Print numeric value without leading zeroes
        System.out.println(
                new BigInteger(answer.toString()));
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        solve(br);
    }
}
