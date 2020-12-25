package com.hasanguner.aoc2020;


import static com.hasanguner.aoc2020.Inputs.read;

public class Day25 {

    public static final int MOD = 20201227;

    public static void main(String[] args) {
        var input = read(25);
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: Look what reindeer is carrying ;)");
    }

    static long part1(String[] input) {
        int cardPublicKey = Integer.parseInt(input[0]);
        int doorPublicKey = Integer.parseInt(input[1]);
        var loopSize = determineLoopSize(cardPublicKey);
        return loop(doorPublicKey, loopSize);
    }

    private static int determineLoopSize(int publicKey) {
        var subjectNumber = 7;
        var i = 0;
        var value = 1;
        for (; value != publicKey; i++) {
            value = (value * subjectNumber) % MOD;
        }
        return i;
    }

    private static long loop(int subjectNumber, int loopSize) {
        var value = 1L;
        for (int i = 0; i < loopSize; i++) {
            value = (value * subjectNumber) % MOD;
        }
        return value;
    }
}
