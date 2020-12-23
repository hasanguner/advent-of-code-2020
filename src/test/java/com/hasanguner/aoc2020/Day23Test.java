package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day23.part1(read(23)))
                .isEqualTo(52864379);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day23.part2(read(23)))
                .isEqualTo(11591415792L);
    }
}
