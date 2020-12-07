package com.hasanguner.aoc2020;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

public class Day7 {

    public static final String SHINY_GOLD = "shiny gold";

    public static void main(String[] args) {
        var input = read(7);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static long part1(String[] input) {
        var bags = getBags(input);
        var nameAccumulator = new HashSet<String>();
        bags.entrySet()
            .stream()
            .filter(not(it -> it.getKey().equals(SHINY_GOLD)))
            .map(Map.Entry::getValue)
            .forEach(bag -> includesShinyBag(bag, bags, nameAccumulator));
        return nameAccumulator.size();
    }

    static long part2(String[] input) {
        var bags = getBags(input);
        var counter = new AtomicInteger(-1);
        countBagsInside(bags, SHINY_GOLD, counter);
        return counter.intValue();
    }

    private static void countBagsInside(Map<String, Bag> bags, String name, AtomicInteger counter) {
        counter.incrementAndGet();
        bags.get(name).inner.forEach(
                (innerBagName, count) -> range(0, count).forEach(
                        i -> countBagsInside(bags, innerBagName, counter)
                )
        );
    }

    private static boolean includesShinyBag(Bag bag, Map<String, Bag> bags, Set<String> nameAccumulator) {
        if (bag.inner.containsKey(SHINY_GOLD)) {
            nameAccumulator.add(bag.name);
            return true;
        }
        for (var name : bag.inner.keySet()) {
            if (includesShinyBag(bags.get(name), bags, nameAccumulator)) {
                nameAccumulator.add(bag.name);
                return true;
            }
        }
        return false;
    }

    private static Map<String, Bag> getBags(String[] input) {
        return stream(input).map(Bag::create)
                            .collect(toMap(it -> it.name, identity()));
    }

    static class Bag {
        static final Pattern pattern = compile("(?<target>.*) bags contain (?<inner>.*)");
        static final Pattern INNER_PATTERN = compile("(?<quantity>\\d+) (?<name>.*) bag.*");

        final String name;
        final Map<String, Integer> inner = new HashMap<>();

        public Bag(String name) {
            this.name = name;
        }

        static Bag create(String rawContent) {
            var matcher = pattern.matcher(rawContent);
            if (!matcher.find()) {
                return fail();
            }
            var bag = new Bag(matcher.group("target"));
            var inner = matcher.group("inner").trim();
            if (inner.equals("no other bags.")) {
                return bag;
            }
            for (var part : inner.split(",")) {
                var innerMatcher = INNER_PATTERN.matcher(part);
                if (!innerMatcher.find()) {
                    return fail();
                }
                bag.inner.put(
                        innerMatcher.group("name"),
                        parseInt(innerMatcher.group("quantity"))
                );
            }
            return bag;
        }

        private static Bag fail() {
            throw new IllegalArgumentException("Input is not valid");
        }
    }
}
