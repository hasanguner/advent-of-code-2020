package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day17.part1(read(17)))
                .isEqualTo(273);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day17.part2(read(17)))
                .isEqualTo(1504);
    }
}
