package com.hasanguner.aoc2020;

import java.util.function.IntBinaryOperator;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.System.out;
import static java.util.Arrays.stream;

public class Day5 {

    public static void main(String[] args) {
        var input = read(5);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static int part2(String[] input) {
        var seats = stream(input).mapToInt(Day5::seat).sorted().toArray();
        return getMissing(seats);
    }

    static int part1(String[] input) {
        return stream(input).mapToInt(Day5::seat).max().orElseThrow();
    }

    private static int getMissing(int[] seats) {
        var prev = seats[0];
        for (int i = 1; i < seats.length; i++) {
            var seat = seats[i];
            if (seat != prev + 1) {
                return seat - 1;
            } else {
                prev = seat;
            }
        }
        return -1;
    }

    private static int seat(String boardingPass) {
        IntBinaryOperator numberReducer = (l, r) -> (l << 1) + r;
        var row = boardingPass.chars()
                              .limit(7)
                              .map(it -> it == 'F' ? 0 : 1)
                              .reduce(numberReducer)
                              .orElseThrow();
        var column = boardingPass.chars()
                                 .skip(7)
                                 .map(it -> it == 'L' ? 0 : 1)
                                 .reduce(numberReducer)
                                 .orElseThrow();
        return row * 8 + column;
    }
}
