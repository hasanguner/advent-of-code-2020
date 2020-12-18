package com.hasanguner.aoc2020;

import java.util.ArrayDeque;
import java.util.function.ToLongFunction;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;

public class Day18 {

    public static void main(String[] args) {
        var input = Inputs.read(18);
        System.out.println("Part 1 : " + part1(input));
        System.out.println("Part 2 : " + part2(input));
    }

    static long part1(String[] input) {
        return stream(input)
                .mapToLong(exp -> evaluate(exp, Day18::calculateIgnoringPrecedence))
                .sum();
    }

    static long part2(String[] input) {
        return stream(input)
                .mapToLong(exp -> evaluate(exp, Day18::calculateWithReversePrecedence))
                .sum();
    }

    private static long evaluate(String expression, ToLongFunction<String> calculator) {
        var start = -1;
        boolean parenthesis = false;
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (parenthesis) {
                if (ch == '(') {
                    continue;
                }
                start = i;
                parenthesis = false;
            } else if (ch == '(') {
                parenthesis = true;
            } else if (ch == ')') {
                var newExpression = expression.substring(0, start - 1) +
                        calculator.applyAsLong(expression.substring(start, i)) +
                        expression.substring(i + 1);
                return evaluate(newExpression, calculator);
            }
        }
        return calculator.applyAsLong(expression);
    }

    private static long calculateIgnoringPrecedence(String expression) {
        var accumulator = 0L;
        for (var it = stream(expression.split(" ")).iterator(); it.hasNext(); ) {
            var part = it.next();
            if ("+".equals(part)) {
                accumulator += parseLong(it.next());
            } else if ("*".equals(part)) {
                accumulator *= parseLong(it.next());
            } else {
                accumulator = parseLong(part);
            }
        }
        return accumulator;
    }

    private static long calculateWithReversePrecedence(String expression) {
        var operands = new ArrayDeque<Long>();
        var operators = new ArrayDeque<String>();
        for (var part : expression.split(" ")) {
            if (!"+".equals(part) && !"*".equals(part)) {
                operands.push(parseLong(part));
            } else if (operands.size() == 1) {
                operators.push(part);
            } else if (part.equals("+")) {
                if (!operators.isEmpty() && operators.peek().equals("*")) {
                    operators.push(part);
                } else {
                    operands.push(operands.pop() + operands.pop());
                }
            } else {
                popAndCalculate(operands, operators);
                operators.push(part);
            }
        }

        while (!operators.isEmpty()) {
            popAndCalculate(operands, operators);
        }
        return operands.pop();
    }

    private static void popAndCalculate(ArrayDeque<Long> operands, ArrayDeque<String> operators) {
        var o1 = operands.pop();
        var o2 = operands.pop();
        var op = operators.pop();
        operands.push("+".equals(op) ? o1 + o2 : o1 * o2);
    }

}
