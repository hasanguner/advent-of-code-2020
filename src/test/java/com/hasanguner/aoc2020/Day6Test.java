package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Day6.part1;
import static com.hasanguner.aoc2020.Day6.part2;
import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {

    @Test
    void test_with_sample_input_for_part_1() {
        assertThat(part1(sampleInput()))
                .isEqualTo(11);
    }

    @Test
    void test_with_sample_input_for_part_2() {
        assertThat(part2(sampleInput()))
                .isEqualTo(6);
    }

    @Test
    void test_with_real_input_for_part1() {
        assertThat(part1(realInput()))
                .isEqualTo(6947);
    }

    @Test
    void test_with_real_input_for_part2() {
        assertThat(part2(realInput()))
                .isEqualTo(3398);
    }

    private String[] realInput() {
        return Inputs.read(6);
    }

    private String[] sampleInput() {
        return new String[]{
                "abc",
                "",
                "a",
                "b",
                "c",
                "",
                "ab",
                "ac",
                "",
                "a",
                "a",
                "a",
                "a",
                "",
                "b"
        };
    }

}
