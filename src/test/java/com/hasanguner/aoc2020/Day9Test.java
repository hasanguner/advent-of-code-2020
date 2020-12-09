package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day9.part1(read(9)))
                .isEqualTo(1398413738);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day9.part2(read(9)))
                .isEqualTo(169521051);
    }

}
