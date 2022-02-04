package com.tsystems.javaschool.tasks.calculator;
import java.util.EmptyStackException;
import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    //The following part is a wrapping part where I use public methods, check the input, and shape the output
    public String evaluate (String input) {
        if (input == null || input.isEmpty()) return null;            // it's obvious;
        if (!input.matches("[0-9(.)/*+-]+")) return null;       // quite obvious;
        if (doubleTypedSymbols(input)) return null;                   // the method checks if there's anything as ".."
        if (!areBracketsEven(input)) return null;                     // the number of "(" and ")" must be equal

        String preparedForRPNline = reshapeZerosAndBrackets(input);

        Double value = rpnToAnswer(calculate(preparedForRPNline));

        String result = String.format("%.4f", value);
        
        if(result == "Infinity") return null;
        else return result;
    }


//Here's the private part where all the operations are being made. The main piece of the solution.

    public String calculate (String input) {          // Reverse Polish Notation is used here.
        String current = "";
        Stack<Character> stack = new Stack<Character>();

        int priority;                                      // What we need to do first
        for (int i = 0; i < input.length(); i++) {         // is to put operations in the right order.
            priority = getPriority(input.charAt(i));       // We use priorities here, and using the rules of RPN
            if (priority == 0) current += input.charAt(i); // we firstly separate numbers from actions.
            if (priority == 1) stack.push(input.charAt(i));// Stack library is perfect for this purpose.
                                                           // The priorities are:
            if (priority > 1) {                            // * or / <- 3    It's important to notice that
                current += " ";                            // + or - <- 2    brackets naturally don't have priority
                                                           //   (    <- 1    those are not actions, but they change
                                                           // digits <- 0    the priority of operations they wrap
                while (!stack.isEmpty()) {                 //    )   <- -1
                    if (getPriority(stack.peek()) >= priority) current += stack.pop();
                    else break;
                }
                stack.push(input.charAt(i));
            }

            if (priority == -1) {
                current += " ";
                while (getPriority(stack.peek()) != 1) current += stack.pop();
                stack.pop();
            }
        }
        while (!stack.isEmpty()) current += stack.pop(); //at the end we get a line such as "1 2 3 4 *+-"
        return current;
    }


    private Double rpnToAnswer(String rpn) {                   // We have a rpn line
        if (rpn == null) return null;                          // so now we can execute operations
        String operand = new String();                         // basically we go through the line and once we meet
        Stack<Double> stack = new Stack<Double>();             // an operator we perform the action over two previous
        try {                                                  // variables we had in the line.
            for (int i = 0; i < rpn.length(); i++) {           // After we complete the action, we delete the operator,
                                                               // and replace the two numbers with the result we got.
                if (rpn.charAt(i) == ' ') continue;
                if (getPriority(rpn.charAt(i)) == 0) {
                    while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                        operand += rpn.charAt(i++);
                        if (i == rpn.length()) break;
                    }
                    stack.push(Double.parseDouble(operand));
                    operand = new String();
                }
                if (getPriority(rpn.charAt(i)) > 1) {

                    double a = stack.pop();
                    double b = stack.pop();

                    if (rpn.charAt(i) == '*') stack.push(b * a);
                    if (rpn.charAt(i) == '/') {
                        if (a == 0) {
                            System.out.println("Division by zero");
                            stack.push(b / a);
                        } else stack.push(b / a);
                    }
                    if (rpn.charAt(i) == '+') stack.push(b + a);
                    if (rpn.charAt(i) == '-') stack.push(b - a);

                }
            }
        } catch (EmptyStackException e) {
            return null;
        }

        return stack.pop();

    }

    private int getPriority(char symbol) {
        if (symbol == '*' || symbol == '/') return 3;
        else if (symbol == '+' || symbol == '-') return 2;
        else if (symbol == '(') return 1;
        else if (symbol == ')') return -1;
        else return 0;
    }

    private String reshapeZerosAndBrackets(String expression) {
        String preparedExpression = new String();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (symbol == '-') {
                if (i == 0) preparedExpression += 0;
                else if (expression.charAt(i - 1) == '(') preparedExpression += 0;
            }
            preparedExpression += expression.charAt(i);
        }
        return preparedExpression;
    }

    private boolean areBracketsEven(String input) {


        String[] split = input.split("");
        int opening = 0;
        int closing = 0;
        for (String s : split) {

            if (s.equals("(")) {
                opening++;
            } else if (s.equals(")")) {
                closing++;
            }
        }
        if (opening == closing) {
            return true;
        }
        return false;
    }

    private boolean doubleTypedSymbols(String input) {
        String[] inputArray = input.split("");

        for (int i = 1; i < inputArray.length; i++) {

            String current = inputArray[i];
            String previous = inputArray[i - 1];

            if (current.equals("+") && current.equals(previous)) {
                return true;
            } else if (current.equals("-") && current.equals(previous)) {
                return true;
            } else if (current.equals("/") && current.equals(previous)) {
                return true;
            } else if (current.equals("*") && current.equals(previous)) {
                return true;
            } else if (current.equals(".") && current.equals(previous)) {
                return true;
            }
        }

        return false;
    }
}
