package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day10.part1(read(10)))
                .isEqualTo(2376);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day10.part2(read(10)))
                .isEqualTo(129586085429248L);
    }
}
