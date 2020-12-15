package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day15.part1(read(15)))
                .isEqualTo(866);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day15.part2(read(15)))
                .isEqualTo(1437692);
    }
}
