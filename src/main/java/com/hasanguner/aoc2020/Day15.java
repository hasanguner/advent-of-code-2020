package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.System.out;
import static java.util.Arrays.stream;

public class Day15 {

    public static void main(String[] args) {
        var input = read(15);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static int part1(String[] input) {
        return getLastSpoken(2020, getNumbers(input[0]));
    }

    static int part2(String[] input) {
        return getLastSpoken(30000000, getNumbers(input[0]));
    }

    private static int[] getNumbers(String s) {
        return stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private static int getLastSpoken(int limit, int[] numbers) {
        int lastSpoken = -1;
        var map = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < limit; i++) {
            var num = numbers[i % numbers.length];
            if (!map.containsKey(num)) {
                lastSpoken = num;
            } else {
                var list = map.get(lastSpoken);
                var size = list.size();
                lastSpoken = size == 1
                        ? 0
                        : list.get(size - 1) - list.get(size - 2);
            }
            map.putIfAbsent(lastSpoken, new ArrayList<>());
            map.get(lastSpoken).add(i);
        }
        return lastSpoken;
    }

}

