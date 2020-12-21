package com.hasanguner.aoc2020;

import org.junit.jupiter.api.Test;

import static com.hasanguner.aoc2020.Inputs.read;
import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    @Test
    void test_with_real_input_part_1() {
        assertThat(Day21.solve(read(21)).getKey())
                .isEqualTo(2542);
    }

    @Test
    void test_with_real_input_part_2() {
        assertThat(Day21.solve(read(21)).getValue())
                .isEqualTo("hkflr,ctmcqjf,bfrq,srxphcm,snmxl,zvx,bd,mqvk");
    }
}
