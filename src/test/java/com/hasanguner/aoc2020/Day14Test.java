package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day14.part1(read(14)))
                .isEqualTo(14722016054794L);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day14.part2(read(14)))
                .isEqualTo(3618217244644L);
    }
}
