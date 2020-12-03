package com.hasanguner.aoc2020;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.System.out;

public class Day3 {

    public static void main(String[] args) {
        var input = read(3);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static long part1(String[] input) {
        return countTrees(input, 1, 3);
    }

    static long part2(String[] input) {
        return countTrees(input, 1, 1) *
                countTrees(input, 1, 3) *
                countTrees(input, 1, 5) *
                countTrees(input, 1, 7) *
                countTrees(input, 2, 1);
    }

    static long countTrees(String[] matrix, int down, int right) {
        var trees = 0;
        var rLen = matrix.length;
        var cLen = matrix[0].length();
        for (int i = 0, j = 0; i < rLen; i = i + down, j = j + right) {
            trees += matrix[i].charAt(j % cLen) == '#' ? 1 : 0;
        }
        return trees;
    }
}
