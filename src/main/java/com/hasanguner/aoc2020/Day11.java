package com.hasanguner.aoc2020;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.util.Arrays.stream;

public class Day11 {

    public static void main(String[] args) {
        var input = read(11);
        var part1 = part1(input);
        var part2 = part2(input);
        System.out.println("Part 1 : " + part1);
        System.out.println("Part 2 : " + part2);
    }

    static int part1(String[] input) {
        var layout = layout(input);
        var iLength = layout.length;
        var jLength = layout[0].length;
        while (true) {
            var stabilized = true;
            var copy = new char[iLength][jLength];
            for (int i = 0; i < iLength; i++) {
                for (int j = 0; j < jLength; j++) {
                    var spot = layout[i][j];
                    var occupied = occupiedSeatAdjacents(layout, i, j);
                    if (spot == 'L' && occupied == 0) {
                        stabilized = false;
                        spot = '#';
                    } else if (spot == '#' && occupied >= 4) {
                        stabilized = false;
                        spot = 'L';
                    }
                    copy[i][j] = spot;
                }
            }
            layout = copy;
            print(layout);
            if (stabilized) {
                break;
            }
        }
        return countOccupied(layout);
    }

    static int part2(String[] input) {
        var layout = layout(input);
        var iLength = layout.length;
        var jLength = layout[0].length;
        while (true) {
            var stabilized = true;
            var copy = new char[iLength][jLength];
            for (int i = 0; i < iLength; i++) {
                for (int j = 0; j < jLength; j++) {
                    var spot = layout[i][j];
                    var occupied = occupiedSeatsInAnyDirection(layout, i, j);
                    if (spot == 'L' && occupied == 0) {
                        stabilized = false;
                        spot = '#';
                    } else if (spot == '#' && occupied >= 5) {
                        stabilized = false;
                        spot = 'L';
                    }
                    copy[i][j] = spot;
                }
            }
            layout = copy;
            print(layout);
            if (stabilized) {
                break;
            }
        }
        return countOccupied(layout);
    }

    private static int countOccupied(char[][] layout) {
        var count = 0;
        for (var line : layout) {
            for (var spot : line) {
                if (spot == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private static char[][] layout(String[] input) {
        return stream(input)
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static int occupiedSeatsInAnyDirection(char[][] layout, int i, int j) {
        var iLength = layout.length;
        var jLength = layout[0].length;
        var counter = 0;
        for (var ii = 1; i - ii >= 0; ii++) {
            if (layout[i - ii][j] != '.') {
                counter += layout[i - ii][j] == '#' ? 1 : 0;
                break;
            }
        }
        for (var jj = 1; j - jj >= 0; jj++) {
            if (layout[i][j - jj] != '.') {
                counter += layout[i][j - jj] == '#' ? 1 : 0;
                break;
            }
        }
        for (var ii = 1; i + ii < iLength; ii++) {
            if (layout[i + ii][j] != '.') {
                counter += layout[i + ii][j] == '#' ? 1 : 0;
                break;
            }
        }
        for (var jj = 1; j + jj < jLength; jj++) {
            if (layout[i][j + jj] != '.') {
                counter += layout[i][j + jj] == '#' ? 1 : 0;
                break;
            }
        }
        for (int ii = 1, jj = 1; i - ii >= 0 && j - jj >= 0; ii++, jj++) {
            if (layout[i - ii][j - jj] != '.') {
                counter += layout[i - ii][j - jj] == '#' ? 1 : 0;
                break;
            }
        }
        for (int ii = 1, jj = 1; i + ii < iLength && j + jj < jLength; ii++, jj++) {
            if (layout[i + ii][j + jj] != '.') {
                counter += layout[i + ii][j + jj] == '#' ? 1 : 0;
                break;
            }
        }
        for (int ii = 1, jj = 1; i + ii < iLength && j - jj >= 0; ii++, jj++) {
            if (layout[i + ii][j - jj] != '.') {
                counter += layout[i + ii][j - jj] == '#' ? 1 : 0;
                break;
            }
        }
        for (int ii = 1, jj = 1; i - ii >= 0 && j + jj < jLength; ii++, jj++) {
            if (layout[i - ii][j + jj] != '.') {
                counter += layout[i - ii][j + jj] == '#' ? 1 : 0;
                break;
            }
        }
        return counter;
    }

    private static int occupiedSeatAdjacents(char[][] layout, int i, int j) {
        var iLength = layout.length;
        var jLength = layout[0].length;
        var counter = 0;
        for (int ii = -1; ii <= 1; ii++) {
            for (int jj = -1; jj <= 1; jj++) {
                if (!(ii == 0 && jj == 0) &&
                        ii + i >= 0 &&
                        ii + i < iLength &&
                        jj + j >= 0 &&
                        jj + j < jLength &&
                        layout[i + ii][j + jj] == '#'
                ) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static void print(char[][] layout) {
        for (var line : layout) {
            for (char spot : line) {
                System.out.print(spot);
            }
            System.out.println();
        }
        System.out.println();
        sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {
        }
    }
}
