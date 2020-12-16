package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day16.solve(read(16))[0])
                .isEqualTo(21081);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day16.solve(read(16))[1])
                .isEqualTo(314360510573L);
    }
}
