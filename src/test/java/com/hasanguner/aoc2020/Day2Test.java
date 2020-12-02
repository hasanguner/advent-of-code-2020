package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Day2.part1;
import static com.hasanguner.aoc2020.Day2.part2;
import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    @Test
    void test_with_sample_input_for_part_1() {
        assertThat(part1(sampleInput()))
                .isEqualTo(2);
    }

    @Test
    void test_with_sample_input_for_part_2() {
        assertThat(part2(sampleInput()))
                .isEqualTo(1);
    }

    @Test
    void test_with_real_input_for_part1() {
        assertThat(part1(realInput()))
                .isEqualTo(465);
    }

    @Test
    void test_with_real_input_for_part2() {
        assertThat(part2(realInput()))
                .isEqualTo(294);
    }

    private String[] realInput() {
        return Inputs.read(2);
    }

    private String[] sampleInput() {
        return new String[]{
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
        };
    }

}
