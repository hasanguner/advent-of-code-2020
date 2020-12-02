package com.hasanguner.aoc2020;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.regex.Pattern.compile;

public class Day2 {

    public static void main(String[] args) {
        var input = Inputs.read(2);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static long part1(final String[] input) {
        return stream(input)
                .map(Input::parse)
                .filter(Input::validateByFrequency)
                .count();
    }

    static long part2(final String[] input) {
        return stream(input)
                .map(Input::parse)
                .filter(Input::validateByPosition)
                .count();
    }

    static class Input {
        static final Pattern PATTERN = compile("(?<min>\\d+)-(?<max>\\d+) (?<ch>\\w): (?<password>\\w+)");

        int min;
        int max;
        char ch;
        String password;

        Input(int min, int max, char ch, String password) {
            this.min = min;
            this.max = max;
            this.ch = ch;
            this.password = password;
        }

        static Input parse(String line) {
            var matcher = PATTERN.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Input is not valid!");
            }
            return new Input(
                    parseInt(matcher.group("min")),
                    parseInt(matcher.group("max")),
                    matcher.group("ch").charAt(0),
                    matcher.group("password"));
        }

        boolean validateByFrequency() {
            var frequency = password.chars().filter(it -> it == ch).count();
            return frequency >= min && frequency <= max;
        }

        boolean validateByPosition() {
            var minValid = ch == password.charAt(min - 1);
            var maxValid = ch == password.charAt(max - 1);
            return minValid ^ maxValid;
        }
    }
}
