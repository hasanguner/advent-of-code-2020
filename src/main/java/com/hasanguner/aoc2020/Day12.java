package com.hasanguner.aoc2020;

import java.util.List;
import java.util.Map;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.System.out;

class Day12 {

    static List<Character> clockWiseDirections = List.of('N', 'E', 'S', 'W');
    static Map<Character, int[]> directionDeltas = Map.of(
            'E', new int[]{1, 0},
            'N', new int[]{0, 1},
            'S', new int[]{0, -1},
            'W', new int[]{-1, 0}
    );

    public static void main(String[] args) {
        var lines = read(12);
        out.println("Part 1 : " + part1(lines));
        out.println("Part 2 : " + part2(lines));
    }

    static int part2(String[] lines) {
        int east = 0;
        int north = 0;
        int wpEast = 10;
        int wpNorth = 1;
        for (String line : lines) {
            var action = line.charAt(0);
            var value = parseInt(line.substring(1));
            if (directionDeltas.containsKey(action)) {
                var deltas = directionDeltas.get(action);
                wpEast += deltas[0] * value;
                wpNorth += deltas[1] * value;
            } else if (action == 'F') {
                east += wpEast * value;
                north += wpNorth * value;
            } else {
                var rotateNorthSign = action == 'R' ? 1 : -1;
                var rotateEastSign = action == 'R' ? -1 : 1;
                var deltaIx = value / 90;
                for (int i = 0; i < deltaIx; i++) {
                    var prevWpEast = wpEast;
                    wpEast = rotateNorthSign * wpNorth;
                    wpNorth = rotateEastSign * prevWpEast;
                }
            }
        }
        return abs(east) + abs(north);
    }

    static int part1(String[] lines) {
        int east = 0;
        int north = 0;
        char facing = 'E';
        for (String line : lines) {
            var action = line.charAt(0);
            var value = parseInt(line.substring(1));
            if (directionDeltas.containsKey(action)) {
                var deltas = directionDeltas.get(action);
                east += deltas[0] * value;
                north += deltas[1] * value;
            } else if (action == 'F') {
                var deltas = directionDeltas.get(facing);
                east += deltas[0] * value;
                north += deltas[1] * value;
            } else {
                var facingIx = clockWiseDirections.indexOf(facing);
                var deltaIx = value / 90;
                var ix = action == 'R'
                        ? (facingIx + deltaIx) % 4
                        : (facingIx - deltaIx + 4) % 4;
                facing = clockWiseDirections.get(ix);
            }
        }
        return abs(east) + abs(north);
    }

}
