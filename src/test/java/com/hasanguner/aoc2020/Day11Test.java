package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day11.part1(read(11)))
                .isEqualTo(2324);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day11.part2(read(11)))
                .isEqualTo(2068);
    }
}
