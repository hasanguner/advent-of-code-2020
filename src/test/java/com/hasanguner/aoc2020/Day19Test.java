package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day19Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day19.solve(read(19))[0])
                .isEqualTo(132);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day19.solve(read(19))[1])
                .isEqualTo(306);
    }
}
