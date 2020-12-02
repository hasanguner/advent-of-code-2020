package com.hasanguner.aoc2020;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class Inputs {

    private static final String INPUT_PATH_TEMPLATE = "input/day<number>.txt";

    public static String[] read(int dayNumber) {
        var path = INPUT_PATH_TEMPLATE.replace("<number>", String.valueOf(dayNumber));
        try (var inputStream = Inputs.class.getClassLoader().getResourceAsStream(path)) {
            requireNonNull(inputStream, format("Input file under %s path not found!", path));
            return new String(inputStream.readAllBytes()).split("\n");
        } catch (IOException e) {
            throw new IllegalStateException(format("Failed to read input file under %s path", path), e);
        }
    }
}
