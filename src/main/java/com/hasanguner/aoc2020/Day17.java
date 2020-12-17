package com.hasanguner.aoc2020;

import static com.hasanguner.aoc2020.Inputs.read;

public class Day17 {

    public static void main(String[] args) {
        var input = read(17);
        System.out.println("Part 1 : " + part1(input));
        System.out.println("Part 2 : " + part2(input));
    }

    static int part1(String[] input) {
        var n = input.length + 6 * 2;
        var grid = new char[n][n][n];
        var originI = n / 2;
        var offset = originI - input.length / 2;
        for (int j = 0; j < input.length; j++) {
            for (int k = 0; k < input.length; k++) {
                grid[originI][offset + j][offset + k] = input[j].charAt(k);
            }
        }
        for (var cycle = 0; cycle < 6; cycle++) {
            var newGrid = new char[n][n][n];
            for (var i = 0; i < n; i++) {
                for (var j = 0; j < n; j++) {
                    for (var k = 0; k < n; k++) {
                        var count = activeNeighbors(grid, i, j, k);
                        if (count == 3) {
                            newGrid[i][j][k] = '#';
                        } else if (grid[i][j][k] == '#' && count == 2) {
                            newGrid[i][j][k] = '#';
                        } else {
                            newGrid[i][j][k] = '.';
                        }
                    }
                }
            }
            grid = newGrid;
        }
        return countActiveCubes(grid);
    }

    static int part2(String[] input) {
        var n = input.length + 6 * 2;
        var grid = new char[n][n][n][n];
        var originIJ = n / 2;
        var offset = originIJ - input.length / 2;
        for (int k = 0; k < input.length; k++) {
            for (int t = 0; t < input.length; t++) {
                grid[originIJ][originIJ][offset + k][offset + t] = input[k].charAt(t);
            }
        }
        for (var cycle = 0; cycle < 6; cycle++) {
            var newGrid = new char[n][n][n][n];
            for (var i = 0; i < n; i++) {
                for (var j = 0; j < n; j++) {
                    for (var k = 0; k < n; k++) {
                        for (var t = 0; t < n; t++) {
                            var point = grid[i][j][k][t];
                            var count = activeNeighbors(grid, i, j, k, t);
                            if (count == 3) {
                                point = '#';
                            } else if (point == '#' && count == 2) {
                                point = '#';
                            } else {
                                point = '.';
                            }
                            newGrid[i][j][k][t] = point;
                        }
                    }
                }
            }
            grid = newGrid;
        }
        return countActiveCubes(grid);
    }

    private static int activeNeighbors(char[][][][] grid, int i, int j, int k, int t) {
        var n = grid.length;
        var counter = 0;
        for (int ii = -1; ii <= 1; ii++) {
            for (int jj = -1; jj <= 1; jj++) {
                for (int kk = -1; kk <= 1; kk++) {
                    for (int tt = -1; tt <= 1; tt++) {
                        if (!(ii == 0 && jj == 0 && kk == 0 && tt == 0) &&
                                ii + i >= 0 &&
                                ii + i < n &&
                                jj + j >= 0 &&
                                jj + j < n &&
                                kk + k >= 0 &&
                                kk + k < n &&
                                tt + t >= 0 &&
                                tt + t < n &&
                                grid[i + ii][j + jj][k + kk][t + tt] == '#'
                        ) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    private static int activeNeighbors(char[][][] grid, int i, int j, int k) {
        var n = grid.length;
        var counter = 0;
        for (int ii = -1; ii <= 1; ii++) {
            for (int jj = -1; jj <= 1; jj++) {
                for (int kk = -1; kk <= 1; kk++) {
                    if (!(ii == 0 && jj == 0 && kk == 0) &&
                            ii + i >= 0 &&
                            ii + i < n &&
                            jj + j >= 0 &&
                            jj + j < n &&
                            kk + k >= 0 &&
                            kk + k < n &&
                            grid[i + ii][j + jj][k + kk] == '#'
                    ) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private static int countActiveCubes(char[][][][] grid) {
        var counter = 0;
        for (char[][][] subGrid : grid) {
            counter += countActiveCubes(subGrid);
        }
        return counter;
    }

    private static int countActiveCubes(char[][][] grid) {
        var n = grid.length;
        var counter = 0;
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < n; j++) {
                for (var k = 0; k < n; k++) {
                    counter += grid[i][j][k] == '#' ? 1 : 0;
                }
            }
        }
        return counter;
    }

}
