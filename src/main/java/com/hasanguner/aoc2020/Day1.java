package com.hasanguner.aoc2020;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.util.Arrays.stream;
import static java.util.Map.entry;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class Day1 {

    public static void main(String[] args) {
        var input = read(1);
        System.out.println("Part 1 : " + part1(input));
        System.out.println("Part 2 : " + part2(input));
    }

    static int part1(String[] input) {
        var numbers = stream(input).map(Integer::valueOf);
        return findNumber(numbers, 2020)
                .map(num1 -> num1 * (2020 - num1))
                .orElseThrow();
    }

    static int part2(String[] input) {
        var memo = stream(input)
                .map(Integer::valueOf)
                .collect(toMap(i -> 2020 - i, identity()));
        return memo.keySet()
                   .stream()
                   .map(it -> entry(2020 - it, findNumber(memo.values().stream(), it)))
                   .filter(p -> p.getValue().isPresent())
                   .flatMapToInt(p -> IntStream.of(p.getKey(), p.getValue().get()))
                   .distinct()
                   .reduce(Math::multiplyExact)
                   .orElseThrow();
    }

    private static Optional<Integer> findNumber(Stream<Integer> numbers, int addUpTo) {
        var memo = numbers.collect(toMap(i -> addUpTo - i, identity()));
        return memo.values()
                   .stream()
                   .map(memo::get)
                   .filter(Objects::nonNull)
                   .findFirst();
    }
}
