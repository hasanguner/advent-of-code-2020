package com.hasanguner.aoc2020;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;

public class Day13 {

    public static void main(String[] args) {
        var input = read(13);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static long part1(String[] input) {
        var timestamp = parseInt(input[0]);
        return stream(input[1].split(","))
                .filter(it -> !"x".equals(it))
                .map(Integer::parseInt)
                .map(id -> new int[]{id, id * (timestamp / id + 1)})
                .min(comparing(it -> it[1] - it[0]))
                .map(it -> it[0] * (it[1] - timestamp))
                .orElseThrow();
    }

    static long part2(String[] input) {
        // bus[i] * k + offset[i] = bus[i+1] * p - offset[i+1]
        // 17,x,13,19 is 3417.
        // 17 * k + 0 = 13 * p - 2 = 19 * s - 3 = M
        // M = ?
        var busses = input[1].split(",");
        var firstBus = parseInt(busses[0]);
        long result = firstBus;
        long multiplier = firstBus;
        for (int offset = 1; offset < busses.length; offset++) {
            var bus = busses[offset];
            if (bus.equals("x")) {
                continue;
            }
            var id = parseInt(bus);
            while ((result + offset) % id != 0) {
                result += multiplier;
            }
            multiplier *= id;
        }
        return result;
    }

}

