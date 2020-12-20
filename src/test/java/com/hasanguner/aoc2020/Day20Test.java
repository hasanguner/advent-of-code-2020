package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day20Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day20.solve(read(20))[0])
                .isEqualTo(18262194216271L);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day20.solve(read(20))[1])
                .isEqualTo(2023);
    }
}
