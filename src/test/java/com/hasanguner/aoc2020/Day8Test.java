package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day8.part1(read(8)).accumulator)
                .isEqualTo(1600);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day8.part2(read(8)))
                .isEqualTo(1543);
    }

}
