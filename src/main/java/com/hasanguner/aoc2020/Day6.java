package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashSet;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.System.out;
import static java.util.Arrays.stream;

public class Day6 {

    public static void main(String[] args) {
        var input = read(6);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static int part1(String[] input) {
        var count = 0;
        var group = new HashSet<Integer>();
        for (var line : input) {
            if (line.isBlank()) {
                count += group.size();
                group = new HashSet<>();
                continue;
            }
            line.chars().forEach(group::add);
        }
        return count + group.size();
    }

    static long part2(String[] input) {
        var answers = new ArrayList<String>();
        var count = 0;
        for (String line : input) {
            if (line.isBlank()) {
                count += countYesToAllQuestions(answers);
                answers = new ArrayList<>();
                continue;
            }
            answers.add(line);
        }
        return count + countYesToAllQuestions(answers);
    }

    private static long countYesToAllQuestions(ArrayList<String> answers) {
        return answers.stream()
                      .flatMap(it -> stream(it.split("")))
                      .distinct()
                      .filter(question -> answers.stream().allMatch(a -> a.contains(question)))
                      .count();
    }
}
