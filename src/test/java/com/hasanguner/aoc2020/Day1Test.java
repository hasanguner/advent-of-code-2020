package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day1.part1(read(1)))
                .isEqualTo(545379);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day1.part2(read(1)))
                .isEqualTo(257778836);
    }

}
