package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Math.sqrt;
import static java.util.Arrays.stream;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

public class Day20 {

    static final char[][] SEA_MONSTER = {
            "                  # ".toCharArray(),
            "#    ##    ##    ###".toCharArray(),
            " #  #  #  #  #  #   ".toCharArray()
    };

    public static void main(String[] args) {
        var result = solve(read(20));
        System.out.println("Part 1 : " + result[0]);
        System.out.println("Part 2 : " + result[1]);
    }

    static long[] solve(String[] input) {
        var tiles = getTiles(input);
        var uniqueEdges = new HashSet<String>();
        for (var tile : tiles) {
            for (var value : tile.edges) {
                if (!uniqueEdges.add(value)) {
                    uniqueEdges.remove(value);
                }
            }
        }
        var multiplicationOfCornerTileIds = tiles.stream()
                                                 .filter(tile -> tile.isCorner(uniqueEdges))
                                                 .mapToLong(t -> t.id)
                                                 .reduce(Math::multiplyExact)
                                                 .orElseThrow();
        var matrixLength = (int) sqrt(tiles.size());
        var topLeft = locateTopLeftCorner(tiles, uniqueEdges);
        tiles.remove(topLeft);
        var firstLine = new ArrayList<Tile>();
        firstLine.add(topLeft);
        var previousRight = topLeft.right();
        for (int i = 1; i < matrixLength; i++) {
            var lastTile = locateNextTile(tiles, previousRight);
            firstLine.add(lastTile);
            tiles.remove(lastTile);
            previousRight = lastTile.right();
        }

        var matrix = new ArrayList<List<Tile>>();
        matrix.add(firstLine);
        for (int i = 1; i < matrixLength; i++) {
            var line = new ArrayList<Tile>();
            matrix.add(line);
            var bottom = matrix.get(i - 1).get(0).bottom();
            var leftTile = locateTop(tiles, bottom);
            line.add(leftTile);
            tiles.remove(leftTile);
            previousRight = leftTile.right();
            for (int j = 1; j < matrixLength; j++) {
                Tile lastTile = locateNextTile(tiles, previousRight);
                line.add(lastTile);
                tiles.remove(lastTile);
                previousRight = lastTile.right();
            }
        }

        var located = locateSeaMonsters(captureImage(matrix, matrixLength * 8));
        var image = located.getKey();
        var monstersImage = located.getValue();
        print(image);
        print(monstersImage);
        return new long[]{multiplicationOfCornerTileIds, countMismatch(image, monstersImage)};
    }

    private static List<Tile> getTiles(String[] input) {
        var rawTiles = new HashMap<Integer, String[]>();
        var lastId = -1;
        var lastTileColumn = 0;

        for (String line : input) {
            if (line.isEmpty()) {
                lastTileColumn = 0;
            } else if (line.startsWith("Tile")) {
                var part = line.split(" ")[1];
                var id = Integer.parseInt(part.substring(0, part.length() - 1));
                lastId = id;
                rawTiles.put(id, new String[10]);
            } else {
                rawTiles.get(lastId)[lastTileColumn++] = line;
            }
        }
        return rawTiles.entrySet()
                       .stream()
                       .map(Tile::create)
                       .collect(toList());
    }

    private static Tile locateTopLeftCorner(List<Tile> tiles, Set<String> uniqueEdges) {
        var tile = tiles.stream()
                        .filter(t -> t.isCorner(uniqueEdges))
                        .findFirst()
                        .orElseThrow();
        while (!uniqueEdges.contains(tile.top())) {
            tile.rotate();
        }
        while (!uniqueEdges.contains(tile.left())) {
            tile.flip();
        }
        return tile;
    }

    private static Tile locateNextTile(List<Tile> tiles, String previousRight) {
        var tile = tiles.stream()
                        .filter(t -> stream(t.edges).anyMatch(previousRight::equals))
                        .findFirst()
                        .orElseThrow();

        for (int i = 0; i < 4; i++) {
            if (tile.left().equals(previousRight)) {
                return tile;
            }
            tile.rotate();
        }
        tile.flip();
        for (int i = 0; i < 4; i++) {
            if (tile.left().equals(previousRight)) {
                return tile;
            }
            tile.rotate();
        }
        throw fail();
    }

    private static Tile locateTop(List<Tile> tiles, String bottom) {
        var tile = tiles.stream()
                        .filter(t -> stream(t.edges).anyMatch(bottom::equals))
                        .findFirst()
                        .orElseThrow();
        for (int i = 0; i < 4; i++) {
            if (tile.top().equals(bottom)) {
                return tile;
            }
            tile.rotate();
        }
        tile.flip();
        for (int i = 0; i < 4; i++) {
            if (tile.top().equals(bottom)) {
                return tile;
            }
            tile.rotate();
        }
        throw fail();
    }

    private static char[][] captureImage(ArrayList<List<Tile>> matrix, int imageLength) {
        var image = new char[imageLength][imageLength];
        var i = 0;
        var j = 0;

        for (var line : matrix) {
            for (var t : line) {
                var tile = t.tile;
                for (int k = 1; k < tile.length - 1; k++) {
                    for (int p = 1; p < tile.length - 1; p++) {
                        image[i][j++] = tile[k][p];
                    }
                    i++;
                    j -= 8;
                }
                i -= 8;
                j += 8;
            }
            j = 0;
            i += 8;
        }
        return image;
    }

    static Map.Entry<char[][], char[][]> locateSeaMonsters(char[][] image) {
        for (int i = 0; i < 4; i++) {
            var maybeMonstersImage = locateMonsters(image);
            if (maybeMonstersImage.isPresent()) {
                return entry(image, maybeMonstersImage.get());
            }
            image = rotateTile(image);
        }
        image = flipTile(image);
        for (int i = 0; i < 4; i++) {
            var maybeMonstersImage = locateMonsters(image);
            if (maybeMonstersImage.isPresent()) {
                return entry(image, maybeMonstersImage.get());
            }
            image = rotateTile(image);
        }
        throw fail();
    }

    private static Optional<char[][]> locateMonsters(char[][] image) {
        var monstersImage = new char[image.length][image.length];
        var found = false;
        for (int i = 0; i < image.length - SEA_MONSTER.length + 1; i++) {
            outer:
            for (int j = 0; j < image.length - SEA_MONSTER[0].length + 1; j++) {
                for (int si = 0; si < SEA_MONSTER.length; si++) {
                    for (int sj = 0; sj < SEA_MONSTER[0].length; sj++) {
                        if (SEA_MONSTER[si][sj] == '#' && image[i + si][j + sj] != '#') {
                            continue outer;
                        }
                    }
                }
                found = true;
                for (int ii = 0; ii < SEA_MONSTER.length; ii++) {
                    System.arraycopy(SEA_MONSTER[ii], 0, monstersImage[ii + i], j, SEA_MONSTER[0].length);
                }
            }
        }
        return found ? Optional.of(monstersImage) : Optional.empty();
    }

    private static RuntimeException fail() {
        throw new IllegalArgumentException("Invalid tile");
    }

    private static int countMismatch(char[][] image, char[][] monstersImage) {
        var counter = 0;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                if (monstersImage[i][j] != '#' && image[i][j] == '#') {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static char[][] flipTile(char[][] tile) {
        var newTile = new char[tile.length][tile.length];
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile.length; j++) {
                newTile[i][tile.length - 1 - j] = tile[i][j];
            }
        }
        return newTile;
    }

    private static char[][] rotateTile(char[][] tile) {
        var newTile = new char[tile.length][tile.length];
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile.length; j++) {
                newTile[j][tile.length - 1 - i] = tile[i][j];
            }
        }
        return newTile;
    }

    static class Tile {
        final Integer id;
        char[][] tile;
        String[] edges;

        Tile(Integer id, char[][] tile, String[] edges) {
            this.id = id;
            this.tile = tile;
            this.edges = edges;
        }

        void rotate() {
            this.tile = rotateTile(tile);
            this.edges = getEdges(tile);
        }

        void flip() {
            this.tile = flipTile(tile);
            this.edges = getEdges(tile);
        }

        String top() {
            return edges[0];
        }

        String bottom() {
            return edges[2];
        }

        String left() {
            return edges[4];
        }

        String right() {
            return edges[6];
        }

        static Tile create(Map.Entry<Integer, String[]> idToTile) {
            var id = idToTile.getKey();
            var tile = stream(idToTile.getValue())
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
            return new Tile(id, tile, getEdges(tile));
        }

        private static String[] getEdges(char[][] tile) {
            var edges = new String[8];
            edges[0] = String.valueOf(tile[0]);
            edges[1] = new StringBuilder(edges[0]).reverse().toString();
            edges[2] = String.valueOf(tile[9]);
            edges[3] = new StringBuilder(edges[2]).reverse().toString();
            var left = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                left.append(tile[i][0]);
            }
            edges[4] = left.toString();
            edges[5] = left.reverse().toString();
            var right = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                right.append(tile[i][9]);
            }
            edges[6] = right.toString();
            edges[7] = right.reverse().toString();
            return edges;
        }

        public boolean isCorner(Set<String> uniqueEdges) {
            return stream(edges)
                    .filter(uniqueEdges::contains)
                    .count() > 2;
        }

    }

    public static void print(char[][] matrix) {
        for (char[] line : matrix) {
            for (char ch : line) {
                System.out.print(ch == '#' ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }


}
