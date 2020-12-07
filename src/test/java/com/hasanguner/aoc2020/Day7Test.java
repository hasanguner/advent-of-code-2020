package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day7.part1(read(7)))
                .isEqualTo(265);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day7.part2(read(7)))
                .isEqualTo(14177);
    }

}
