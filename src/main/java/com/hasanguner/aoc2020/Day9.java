package com.hasanguner.aoc2020;

import java.util.ArrayList;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Long.parseLong;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class Day9 {

    public static void main(String[] args) {
        var lines = read(9);
        out.println("Part 1 : " + part1(lines));
        out.println("Part 2 : " + part2(lines));
    }

    static long part1(String[] lines) {
        var preambleOf = 25;
        for (int i = preambleOf; i < lines.length; i++) {
            var number = parseLong(lines[i]);
            var numbers = stream(lines)
                    .skip(i - preambleOf)
                    .limit(preambleOf)
                    .map(Long::parseLong)
                    .collect(toMap(it -> number - it, identity()));
            var invalid = numbers.values()
                                 .stream()
                                 .filter(numbers::containsKey)
                                 .findAny()
                                 .isEmpty();
            if (invalid) {
                return number;
            }
        }
        return -1;
    }


    static long part2(String[] lines) {
        var invalidNumber = part1(lines);
        for (int i = 2; i < lines.length; i++) {
            var numbers = new ArrayList<Long>();
            int sum = 0;
            for (int j = i - 1; j >= 0; j--) {
                var number = parseLong(lines[j]);
                sum += number;
                numbers.add(number);
                if (sum > invalidNumber) {
                    break;
                }
                if (sum == invalidNumber) {
                    var min = numbers.stream().mapToLong(it -> it).min().orElseThrow();
                    var max = numbers.stream().mapToLong(it -> it).max().orElseThrow();
                    return min + max;
                }
            }
        }
        return -1;
    }

}
