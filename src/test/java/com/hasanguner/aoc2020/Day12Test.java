package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day12.part1(read(12)))
                .isEqualTo(1177);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day12.part2(read(12)))
                .isEqualTo(46530);
    }
}
