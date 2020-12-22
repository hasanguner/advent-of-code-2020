package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day22Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day22.part1(read(22)))
                .isEqualTo(33772);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day22.part2(read(22)))
                .isEqualTo(35070);
    }
}
