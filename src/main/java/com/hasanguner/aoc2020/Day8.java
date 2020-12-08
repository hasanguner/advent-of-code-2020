package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashSet;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.Arrays.stream;

public class Day8 {

    public static void main(String[] args) {
        var lines = read(8);
        out.println("Part 1 : " + part1(lines).accumulator);
        out.println("Part 2 : " + part2(lines));
    }

    static Result part1(String[] lines) {
        long accumulator = 0;
        var instructions = new HashSet<String>();
        var accHistory = new ArrayList<Long>();
        var visited = new ArrayList<Boolean>();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            var parts = line.split(" ");
            String op = parts[0];
            int arg = parseInt(parts[1]);
            if (op.equals("acc")) {
                accumulator += arg;
            } else if (op.equals("jmp")) {
                i += arg - 1;
            }
            visited.add(instructions.add(line));
            accHistory.add(accumulator);
            var index = visited.lastIndexOf(true);
            if (isRepeating(visited, index)) {
                return new Result(accHistory.get(index), true);
            }
        }
        return new Result(accumulator, false);
    }

    static long part2(String[] lines) {
        var length = lines.length;
        for (int k = 0; k < length; k++) {
            if (lines[k].startsWith("acc")) {
                continue;
            }
            var copyLines = stream(lines).toArray(String[]::new);
            if (copyLines[k].startsWith("nop")) {
                copyLines[k] = copyLines[k].replace("nop", "jmp");
            } else {
                copyLines[k] = copyLines[k].replace("jmp", "nop");
            }
            var result = part1(copyLines);
            if (!result.repeated) {
                return result.accumulator;
            }
        }
        return -1;
    }

    private static boolean isRepeating(ArrayList<Boolean> visited, int lastTrueIndex) {
        return visited.stream().filter(it -> !it).count() > lastTrueIndex;
    }

    static class Result {
        long accumulator;
        boolean repeated;

        public Result(long accumulator, boolean repeated) {
            this.accumulator = accumulator;
            this.repeated = repeated;
        }
    }
}
