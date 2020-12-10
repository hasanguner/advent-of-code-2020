package com.hasanguner.aoc2020;

import java.util.HashMap;
import java.util.Map;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public class Day10 {

    public static void main(String[] args) {
        var lines = read(10);
        out.println("Part 1 : " + part1(lines));
        out.println("Part 2 : " + part2(lines));
    }

    static int part1(String[] lines) {
        var numbers = stream(lines)
                .map(Integer::parseInt)
                .sorted()
                .collect(toList());
        var prev = 0;
        var differences = new HashMap<Integer, Integer>();
        differences.put(3, 1);
        for (var number : numbers) {
            differences.merge(number - prev, 1, Integer::sum);
            prev = number;
        }
        return differences.values()
                          .stream()
                          .reduce(Math::multiplyExact)
                          .orElseThrow();
    }

    static long part2(String[] lines) {
        var numbers = concat(
                of(0),
                stream(lines)
                        .map(Integer::parseInt)
                        .sorted()
        ).collect(toUnmodifiableList());
        var numberToAdjacents = new HashMap<Integer, int[]>();
        for (int i = 0; i < numbers.size(); i++) {
            var number = numbers.get(i);
            var limit = number + 3;
            var adjacents = numbers.stream()
                                   .skip(i + 1)
                                   .takeWhile(it -> it <= limit)
                                   .mapToInt(it -> it)
                                   .toArray();
            numberToAdjacents.put(number, adjacents);
        }
        return count(0, numberToAdjacents, new HashMap<>());
    }

    private static long count(int number, Map<Integer, int[]> numberToAdjacents, Map<Integer, Long> cache) {
        var hit = cache.get(number);
        if (hit != null) {
            return hit;
        }
        var adjacents = numberToAdjacents.get(number);
        if (adjacents.length == 0) {
            return 1L;
        }
        var count = stream(adjacents)
                .mapToLong(i -> count(i, numberToAdjacents, cache))
                .sum();
        cache.put(number, count);
        return count;
    }
}
