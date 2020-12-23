package com.hasanguner.aoc2020;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Day23 {

    public static void main(String[] args) {
        var input = read(23);
        System.out.println("Part 1 : " + part1(input));
        System.out.println("Part 2 : " + part2(input));
    }

    public static long part2(String[] input) {
        var cups = stream(input[0].split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        var min = stream(cups).min().orElseThrow();
        var max = stream(cups).max().orElseThrow();
        var memo = new HashMap<Integer, Cup>();
        var lastCup = circleAndGetLastCup(cups, memo);
        var firstCup = lastCup.next;
        for (int k = max + 1; k <= 1_000_000; k++) {
            var node = new Cup(k);
            memo.put(k, node);
            lastCup.add(node);
            lastCup = node;
        }
        max = 1_000_000;

        var currentCup = firstCup;
        for (int j = 0; j < 10_000_000; j++) {
            currentCup = round(min, max, memo, currentCup);
        }
        return (long) memo.get(1).next.value * memo.get(1).next.next.value;
    }

    public static long part1(String[] input) {
        var cups = stream(input[0].split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        var min = stream(cups).min().orElseThrow();
        var max = stream(cups).max().orElseThrow();
        var memo = new HashMap<Integer, Cup>();
        var firstCup = circleAndGetLastCup(cups, memo).next;

        var currentCup = firstCup;
        for (int j = 0; j < 100; j++) {
//            print(currentCup, cups.length);
            currentCup = round(min, max, memo, currentCup);
        }
        return parseLong(toString(memo.get(1).next, cups.length - 1));
    }

    private static Cup round(int min, int max, HashMap<Integer, Cup> memo, Cup currentCup) {
        var lookFor = destination(min, max, currentCup);
        var pickedUpCups = List.of(
                currentCup.removeNext(),
                currentCup.removeNext(),
                currentCup.removeNext());
        var pickedUpCupValues = pickedUpCups.stream().map(it -> it.value).collect(toList());
        while (pickedUpCupValues.contains(lookFor)) {
            lookFor--;
            if (lookFor < min) {
                lookFor = max;
            }
        }
        memo.get(lookFor).addAll(pickedUpCups);
        return currentCup.next;
    }

    private static int destination(int min, int max, Cup currentCup) {
        return currentCup.value == min ? max : currentCup.value - 1;
    }

    private static Cup circleAndGetLastCup(int[] cups, HashMap<Integer, Cup> memo) {
        var firstCup = new Cup(cups[0]);
        memo.put(firstCup.value, firstCup);
        var lastCup = firstCup;
        for (int i = 1; i < cups.length; i++) {
            var node = new Cup(cups[i]);
            memo.put(node.value, node);
            lastCup.add(node);
            lastCup = node;
        }
        lastCup.add(firstCup);
        return lastCup;
    }

    private static void print(Cup cup, int len) {
        var printCup = cup;
        for (int i = 0; i < len; i++) {
            System.out.print(printCup.value + " ");
            printCup = printCup.next;
        }
        System.out.println();
    }

    private static String toString(Cup cup, int len) {
        var printCup = cup;
        var sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(printCup.value);
            printCup = printCup.next;
        }
        return sb.toString();
    }


    static class Cup {
        int value;
        Cup next;

        public Cup(int value) {
            this.value = value;
        }

        void add(Cup next) {
            var node = this.next;
            if (node != null) {
                next.next = node;
            }
            this.next = next;
        }

        void addAll(Collection<Cup> cups) {
            Cup prevCup = null;
            for (var cup : cups) {
                if (prevCup == null) {
                    add(cup);
                } else {
                    prevCup.add(cup);
                }
                prevCup = cup;
            }
        }

        Cup removeNext() {
            var cup = next;
            next = next.next;
            cup.next = null;
            return cup;
        }

    }

}
