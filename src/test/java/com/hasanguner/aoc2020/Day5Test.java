package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Day5.part1;
import static com.hasanguner.aoc2020.Day5.part2;
import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    @Test
    void test_with_real_input_for_part1() {
        assertThat(part1(realInput()))
                .isEqualTo(994);
    }

    @Test
    void test_with_real_input_for_part2() {
        assertThat(part2(realInput()))
                .isEqualTo(741);
    }

    private String[] realInput() {
        return Inputs.read(5);
    }

}
