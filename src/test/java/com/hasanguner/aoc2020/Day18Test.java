package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day18.part1(read(18)))
                .isEqualTo(3885386961962L);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day18.part2(read(18)))
                .isEqualTo(112899558798666L);
    }

    @Test
    void test_with_sample_input_for_part_2() {
        var input = new String[]{"5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"};
        System.out.println(Day18.part2(input));
    }
}
