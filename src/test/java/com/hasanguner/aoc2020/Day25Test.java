package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day25.part1(read(25)))
                .isEqualTo(16311885);
    }

}
