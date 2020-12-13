package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Day13.part1;
import static com.hasanguner.aoc2020.Day13.part2;
import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    @Test
    void test_with_sample_input_for_part_1() {
        assertThat(part1(sampleInput()))
                .isEqualTo(295L);
    }

    @Test
    void test_with_sample_input_for_part_2() {
        assertThat(part2(sampleInput()))
                .isEqualTo(1068781L);
    }

    @Test
    void test_with_real_input_for_part1() {
        assertThat(part1(realInput()))
                .isEqualTo(8063L);
    }

    @Test
    void test_with_real_input_for_part2() {
        assertThat(part2(realInput()))
                .isEqualTo(775230782877242L);
    }

    private String[] realInput() {
        return Inputs.read(13);
    }

    private String[] sampleInput() {
        return new String[]{
                "939",
                "7,13,x,x,59,x,31,19"
        };
    }

}
