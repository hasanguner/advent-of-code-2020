package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Day16 {

    public static void main(String[] args) {
        var input = read(16);
        var result = solve(input);
        out.println("Part 1 : " + result[0]);
        out.println("Part 2 : " + result[1]);
    }

    static long[] solve(String[] input) {
        var rules = new ArrayList<Rule>();
        var lineIterator = stream(input).iterator();
        while (lineIterator.hasNext()) {
            var line = lineIterator.next();
            if (line.isEmpty()) {
                lineIterator.next();
                break;
            }
            rules.add(new Rule(line));
        }
        var myTicket = splitToNumbers(lineIterator.next());

        lineIterator.next();
        lineIterator.next();

        var nearbyTickets = new ArrayList<List<Integer>>();
        while (lineIterator.hasNext()) {
            nearbyTickets.add(splitToNumbers(lineIterator.next()));
        }

        var validNearbyTickets = new ArrayList<List<Integer>>();
        validNearbyTickets.add(myTicket);

        int error = 0;
        for (var ticket : nearbyTickets) {
            var maybeInvalidField = findInvalidField(rules, ticket);
            if (maybeInvalidField.isEmpty()) {
                validNearbyTickets.add(ticket);
            }
            error += maybeInvalidField.orElse(0);
        }

        var cspRules = rules.stream().map(r -> new CSPRule(r, myTicket.size())).collect(toList());
        for (var rule : cspRules) {
            for (var ticket : validNearbyTickets) {
                for (var i = 0; i < ticket.size(); i++) {
                    rule.shrinkPossibilitiesByValidation(ticket.get(i), i);
                }
            }
        }

        var sortedRules = range(0, rules.size()).<Rule>mapToObj(i -> null).collect(toList());
        var satisfied = new ArrayList<Integer>();
        cspRules.stream()
                .sorted(comparing(it -> it.possibilities.size()))
                .forEach(r -> {
                    var index = r.possibilities.stream().filter(it -> !satisfied.contains(it)).findFirst().orElseThrow();
                    sortedRules.set(index, r.rule);
                    satisfied.add(index);
                });

        var result = 1L;
        for (int i = 0; i < sortedRules.size(); i++) {
            if (sortedRules.get(i).name.startsWith("departure")) {
                result *= myTicket.get(i);
            }

        }
        return new long[]{error, result};
    }

    private static Optional<Integer> findInvalidField(ArrayList<Rule> rules, List<Integer> ticket) {
        for (var n : ticket) {
            var invalid = rules.stream().noneMatch(r -> r.isValid(n));
            if (invalid) {
                return Optional.of(n);
            }
        }
        return Optional.empty();
    }

    private static List<Integer> splitToNumbers(String line) {
        return stream(line.split(","))
                .map(Integer::parseInt)
                .collect(toList());
    }

    static class Rule {
        private final String name;
        List<int[]> ranges = new ArrayList<>();

        Rule(String line) {
            var nameAndValues = line.split(":");
            name = nameAndValues[0];
            var nums = nameAndValues[1].trim();
            for (var range : nums.split(" or ")) {
                var parts = range.split("-");
                ranges.add(new int[]{parseInt(parts[0]), parseInt(parts[1])});
            }
        }

        boolean isValid(Integer it) {
            for (var range : ranges) {
                if (it >= range[0] && it <= range[1]) {
                    return true;
                }
            }
            return false;
        }
    }

    static class CSPRule {
        Rule rule;
        List<Integer> possibilities;

        CSPRule(Rule rule, int size) {
            this.rule = rule;
            this.possibilities = range(0, size).boxed().collect(toList());
        }

        void shrinkPossibilitiesByValidation(Integer fieldValue, Integer index) {
            if (!rule.isValid(fieldValue)) {
                possibilities.remove(index);
            }
        }
    }

}

