package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day24Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day24.part1(read(24)))
                .isEqualTo(351);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day24.part2(read(24)))
                .isEqualTo(3869);
    }

}
