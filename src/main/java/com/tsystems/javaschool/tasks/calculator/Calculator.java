package com.tsystems.javaschool.tasks.calculator;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {

        if (statement == null || statement.isEmpty()) {
            return null;
        }

        if (isContainsForbiddenSigns(statement)) {
            return null;
        }

        if (areParenthesesEquals(statement)) {
            return null;
        }

        if (areSymbolsTheSame(statement)) {
            return null;
        }

        String rpn = expressionToRPN(statement);
        String result = Double.toString(RPNtoAnswer(rpn));
        
        if (result.equals("Infinity")) {
            return null;
        }

        if (result.endsWith(".0")) {
            return result.substring(0, result.indexOf('.'));
        }


        return result;

    }

    private String expressionToRPN(String input) {
        String current = "";
        Stack<Character> stack = new Stack<>();
        int priority;


        for (int i = 0; i < input.length(); i++) {
            priority = getPriority(input.charAt(i));
            if (priority == 0) {
                current += input.charAt(i);
            }
            if (priority == 1) {
                stack.push(input.charAt(i));
            }
            if (priority > 1) {

                current += ' ';

                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) {
                        current += stack.pop();
                    } else break;
                }
                stack.push(input.charAt(i));
            }
            if (priority == -1) {
                current += ' ';
                while (getPriority(stack.peek()) != 1) {
                    current += stack.pop();
                }
                stack.pop();
            }

        }

        while (!stack.empty()) {
            current += stack.pop();
        }

        return current;

    }

    private double RPNtoAnswer(String rpn) {
        String operand = "";
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                }
                stack.push(Double.parseDouble(operand));
                operand = "";
            }

            if (getPriority(rpn.charAt(i)) > 1) {
                double a = stack.pop();
                double b = stack.pop();

                if (rpn.charAt(i) == '+') {
                    stack.push(b + a);
                }
                if (rpn.charAt(i) == '-') {
                    stack.push(b - a);
                }
                if (rpn.charAt(i) == '/') {
                    stack.push(b / a);
                }
                if (rpn.charAt(i) == '*') {
                    stack.push(b * a);
                }
            }

        }


        return stack.pop();
    }

    private int getPriority(char token) {

        switch (token) {
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            case '(':
                return 1;
            case ')':
                return -1;
            default:
                return 0;
        }
    }

    private boolean areParenthesesEquals(String statement) {


        String[] split = statement.split("");
        int open = 0;
        int close = 0;
        for (String s : split) {

            if (s.equals("(")) {
                open++;
            } else if (s.equals(")")) {
                close++;
            }
        }
        if (close == open) {
            return false;
        }
        return true;
    }

    public boolean areSymbolsTheSame(String statement) {
        String[] split = statement.split("");

        for (int i = 1; i < split.length; i++) {

            String current = split[i];
            String previous = split[i - 1];

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

    private boolean isContainsForbiddenSigns(String statement) {

        Character[] allowed = {'+', '-', '*', '/', '.', '(', ')', '1', '2', '3', '4',
                '5', '6', '7', '8', '9', '0'};

        List<Character> allowedChars = Arrays.asList(allowed);

        char[] chars = statement.toCharArray();

        for (char aChar : chars) {
            if (!allowedChars.contains(aChar)) {
                return true;
            }
        }

        return false;
    }


}
