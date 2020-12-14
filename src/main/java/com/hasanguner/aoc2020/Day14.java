package com.hasanguner.aoc2020;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.toBinaryString;
import static java.lang.System.out;
import static java.util.regex.Pattern.compile;

public class Day14 {

    public static final Pattern MEMORY_PATTERN = compile("mem\\[(.*)] = (.*)");

    public static void main(String[] args) {
        var input = read(14);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static BigInteger part1(String[] input) {
        String mask = "";
        var memory = new HashMap<String, String>();
        for (var instruction : input) {
            if (instruction.startsWith("mask")) {
                mask = instruction.substring(7);
            } else {
                var matcher = MEMORY_PATTERN.matcher(instruction);
                matcher.find();
                var address = padTo36Bit(toBinaryString(parseInt(matcher.group(1))));
                var value = padTo36Bit(toBinaryString(parseInt(matcher.group(2))));
                memory.put(address, applyMask(value, mask, 'X'));
            }
        }
        return sumOfMemoryValues(memory);
    }

    static BigInteger part2(String[] input) {
        String mask = "";
        var memory = new HashMap<String, String>();
        for (var instruction : input) {
            if (instruction.startsWith("mask")) {
                mask = instruction.substring(7);
            } else {
                var matcher = MEMORY_PATTERN.matcher(instruction);
                matcher.find();
                var address = padTo36Bit(toBinaryString(parseInt(matcher.group(1))));
                var value = padTo36Bit(toBinaryString(parseInt(matcher.group(2))));
                var memoryAddresses = new ArrayList<String>();
                var stack = new ArrayDeque<String>();
                stack.push(applyMask(address, mask, '0'));
                while (!stack.isEmpty()) {
                    var mem = stack.pop();
                    var ix = mem.indexOf('X');
                    if (ix == -1) {
                        memoryAddresses.add(mem);
                        continue;
                    }
                    var pos1 = new StringBuilder(mem);
                    var pos2 = new StringBuilder(mem);
                    pos1.setCharAt(ix, '0');
                    pos2.setCharAt(ix, '1');
                    stack.push(pos1.toString());
                    stack.push(pos2.toString());
                }
                memoryAddresses.forEach(it -> memory.put(it, value));
            }
        }
        return sumOfMemoryValues(memory);
    }

    private static BigInteger sumOfMemoryValues(HashMap<String, String> memory) {
        return memory.values()
                     .stream()
                     .map(v -> new BigInteger(v, 2))
                     .reduce(BigInteger::add)
                     .orElseThrow();
    }

    private static String padTo36Bit(String value) {
        return new StringBuilder("0".repeat(36))
                .replace(36 - value.length(), 36, value).toString();
    }

    private static String applyMask(String value, String mask, char skipBit) {
        var result = new StringBuilder(value);
        for (int i = 0; i < mask.length(); i++) {
            var maskBit = mask.charAt(i);
            if (skipBit != maskBit) {
                result.setCharAt(i, maskBit);
            }
        }
        return result.toString();
    }

}

