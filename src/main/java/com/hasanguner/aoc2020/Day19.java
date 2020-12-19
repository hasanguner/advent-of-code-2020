package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Day19 {
    public static void main(String[] args) {
        var result = solve(read(19));
        System.out.println("Part 1 : " + result[0]);
        System.out.println("Part 2 : " + result[1]);
    }

    public static int[] solve(String[] input) {
        var rules = new HashMap<Integer, Rule>();
        var it = stream(input).iterator();
        while (it.hasNext()) {
            var line = it.next();
            if (line.isEmpty()) {
                break;
            }
            var parts = line.split(": ");
            var ruleNumber = parseInt(parts[0]);
            var ruleDescription = parts[1];
            rules.put(ruleNumber, RuleFactory.create(ruleDescription, rules));
        }
        var result = new int[]{0, 0};
        var expression = "^" + rules.get(0).expression() + "$";
        var loopingExpression = "^" + rules.get(0).expressionWithLooping() + "$";
        System.out.println("Expression : " + expression);
        System.out.println("Expression with looping : " + loopingExpression);
        while (it.hasNext()) {
            var line = it.next();
            var matchesP1 = line.matches(expression);
            var matchesP2 = line.matches(loopingExpression);
            if (matchesP1) {
                result[0]++;
            }
            if (matchesP2) {
                result[1]++;
            }
        }
        return result;
    }

    interface Rule {
        String expression();

        default String expressionWithLooping() {
            return expression();
        }
    }

    static class StaticRule implements Rule {
        private final String ch;

        public StaticRule(String ch) {
            this.ch = ch;
        }

        @Override
        public String expression() {
            return ch;
        }
    }

    static class CompositeRule implements Rule {

        private final List<Rule> rules;

        public CompositeRule(int[] indices, Map<Integer, Rule> lookup) {
            rules = stream(indices).mapToObj(i -> new ProxyRule(i, lookup)).collect(toList());
        }

        @Override
        public String expression() {
            return rules.stream().map(Rule::expression).collect(joining());
        }

        @Override
        public String expressionWithLooping() {
            return rules.stream().map(Rule::expressionWithLooping).collect(joining());
        }
    }

    static class SubRule implements Rule {
        private final Rule left;
        private final Rule right;

        public SubRule(Rule left, Rule right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String expression() {
            return format("(%s|%s)", left.expression(), right.expression());
        }

        @Override
        public String expressionWithLooping() {
            return format("(%s|%s)", left.expressionWithLooping(), right.expressionWithLooping());
        }
    }

    static class ProxyRule implements Rule {

        private final Integer index;
        private final Map<Integer, Rule> lookup;

        public ProxyRule(Integer index, Map<Integer, Rule> lookup) {
            this.index = index;
            this.lookup = lookup;
        }

        @Override
        public String expression() {
            return lookup.get(index).expression();
        }

        @Override
        public String expressionWithLooping() {
            if (index == 8) {
                return "(" + lookup.get(42).expression() + "+)";
            }
            if (index == 11) {
                var exprs = new ArrayList<String>();
                var exp42 = lookup.get(42).expression();
                var exp31 = lookup.get(31).expression();
                for (int i = 1; i < 20; i++) {
                    exprs.add("(" + range(0, i).mapToObj(g -> exp42).collect(joining()) +
                                      range(0, i).mapToObj(ignored -> exp31).collect(joining()) +
                                      ")");
                }
                return "(" + String.join("|", exprs) + ")";

            }
            return expression();
        }
    }

    interface RuleFactory {

        static Rule create(String line, Map<Integer, Rule> lookup) {
            if (line.startsWith("\"")) {
                return new StaticRule(line.replace("\"", ""));
            }
            if (!line.contains("|")) {
                var indices = stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                return new CompositeRule(indices, lookup);
            }
            var subRuleParts = line.split(" \\| ");
            var left = RuleFactory.create(subRuleParts[0], lookup);
            var right = RuleFactory.create(subRuleParts[1], lookup);
            return new SubRule(left, right);
        }
    }
}
