package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public class Day24 {

    /**
     * Hexagonal grids can be represented as cube coordinates
     * https://i.stack.imgur.com/e2Ipz.png
     */
    private static final Map<String, Coord3> DIRECTIONS = Map.of(
            "e", new Coord3(1, -1, 0),
            "w", new Coord3(-1, 1, 0),
            "ne", new Coord3(1, 0, -1),
            "nw", new Coord3(0, 1, -1),
            "se", new Coord3(0, -1, 1),
            "sw", new Coord3(-1, 0, 1)
    );

    public static void main(String[] args) {
        var input = read(24);
        System.out.println("Part 1 : " + part1(input));
        System.out.println("Part 2 : " + part2(input));
    }

    public static int part2(String[] input) {
        var blackTiles = blackTiles(input);

        for (int i = 0; i < 100; i++) {
            var newBlackTiles = new HashSet<Coord3>();
            var questionedTiles = new HashSet<Coord3>();
            for (var position : blackTiles) {
                questionedTiles.add(position);
                questionedTiles.addAll(position.adjacents());
            }
            for (var tile : questionedTiles) {
                var adjs = tile.adjacents();
                var count = blackTiles.stream().filter(adjs::contains).count();
                if (count == 2 || (count == 1 && blackTiles.contains(tile))) {
                    newBlackTiles.add(tile);
                }
            }
            blackTiles = newBlackTiles;
        }
        return blackTiles.size();
    }

    public static int part1(String[] input) {
        return blackTiles(input).size();
    }

    private static HashSet<Coord3> blackTiles(String[] input) {
        var blackTiles = new HashSet<Coord3>();
        stream(input)
                .map(Day24::moves)
                .map(moves -> concat(of(Coord3.origin()), moves.stream())
                        .reduce(Coord3::move)
                        .orElseThrow()
                )
                .filter(not(blackTiles::remove))
                .forEach(blackTiles::add);
        return blackTiles;
    }

    private static List<Coord3> moves(String line) {
        var directions = new ArrayList<Coord3>();
        for (int i = 0; i < line.length(); i++) {
            var ch = line.charAt(i);
            if (ch == 'e' || ch == 'w') {
                directions.add(DIRECTIONS.get(String.valueOf(ch)));
            } else {
                directions.add(DIRECTIONS.get(ch + "" + line.charAt(++i)));
            }
        }
        return directions;
    }


    private static class Coord3 {
        int x;
        int y;
        int z;

        public Coord3(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Coord3 coord3 = (Coord3) o;
            return x == coord3.x && y == coord3.y && z == coord3.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }

        public Coord3 move(Coord3 delta) {
            return new Coord3(
                    x + delta.x,
                    y + delta.y,
                    z + delta.z
            );
        }

        public List<Coord3> adjacents() {
            return DIRECTIONS.values()
                             .stream()
                             .map(this::move)
                             .collect(toUnmodifiableList());
        }

        public static Coord3 origin() {
            return new Coord3(0, 0, 0);
        }
    }

}











