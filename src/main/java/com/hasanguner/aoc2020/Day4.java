package com.hasanguner.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hasanguner.aoc2020.Inputs.read;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.compile;

public class Day4 {
    public static void main(String[] args) {
        var input = read(4);
        out.println("Part 1 : " + part1(input));
        out.println("Part 2 : " + part2(input));
    }

    static long part1(String[] input) {
        return getPassports(input).stream().filter(Passport::hasRequiredFields).count();
    }

    static long part2(String[] input) {
        return getPassports(input).stream().filter(Passport::isStrictlyValid).count();
    }

    private static ArrayList<Passport> getPassports(String[] input) {
        var passports = new ArrayList<Passport>();
        var barcode = new Barcode();
        for (String line : input) {
            if (line.isBlank()) {
                passports.add(Passport.create(barcode));
                barcode = new Barcode();
                continue;
            }
            stream(line.split(" ")).forEach(barcode::addAttribute);
        }
        passports.add(Passport.create(barcode));
        return passports;
    }

    static class Barcode {
        static final String DELIMITER = ":";
        Map<String, String> attributes = new HashMap<>();

        void addAttribute(String part) {
            var attrAndVal = part.split(DELIMITER);
            attributes.put(attrAndVal[0], attrAndVal[1]);
        }
    }

    static class Passport {
        static final List<String> VALID_ECLS = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        static final String HCL_PATTERN = "^#[0-9a-f]+$";
        static final String PID_PATTERN = "^[0-9]{9}$";
        static final Pattern HGT_PATTERN = compile("^(?<value>\\d+)(?<unit>cm|in)$");
        String ecl;
        String pid;
        String eyr;
        String hcl;
        String byr;
        String iyr;
        String hgt;

        public static Passport create(Barcode barcode) {
            var passport = new Passport();
            passport.ecl = barcode.attributes.getOrDefault("ecl", null);
            passport.pid = barcode.attributes.getOrDefault("pid", null);
            passport.eyr = barcode.attributes.getOrDefault("eyr", null);
            passport.hcl = barcode.attributes.getOrDefault("hcl", null);
            passport.byr = barcode.attributes.getOrDefault("byr", null);
            passport.iyr = barcode.attributes.getOrDefault("iyr", null);
            passport.hgt = barcode.attributes.getOrDefault("hgt", null);
            return passport;
        }

        boolean hasRequiredFields() {
            return isNotEmpty(ecl) &&
                    isNotEmpty(pid) &&
                    isNotEmpty(eyr) &&
                    isNotEmpty(hcl) &&
                    isNotEmpty(byr) &&
                    isNotEmpty(iyr) &&
                    isNotEmpty(hgt);
        }

        boolean isStrictlyValid() {
            return validByr() &&
                    validIyr() &&
                    validEyr() &&
                    validHgt() &&
                    validHcl() &&
                    validEcl() &&
                    validPid();
        }

        private boolean validEyr() {
            return valid4Digit(eyr, 2020, 2030);
        }

        private boolean validIyr() {
            return valid4Digit(iyr, 2010, 2020);
        }

        private boolean validByr() {
            return valid4Digit(byr, 1920, 2002);
        }

        private static boolean valid4Digit(String str, int min, int max) {
            return ofNullable(str)
                    .filter(it -> it.length() == 4)
                    .map(Integer::parseInt)
                    .filter(it -> it >= min)
                    .filter(it -> it <= max)
                    .isPresent();
        }

        private boolean validHcl() {
            return ofNullable(hcl).filter(it -> it.matches(HCL_PATTERN)).isPresent();
        }

        private boolean validEcl() {
            return ofNullable(ecl).filter(VALID_ECLS::contains).isPresent();
        }

        private boolean validPid() {
            return ofNullable(pid).filter(it -> it.matches(PID_PATTERN)).isPresent();
        }

        private boolean validHgt() {
            return ofNullable(hgt)
                    .map(HGT_PATTERN::matcher)
                    .filter(Matcher::find)
                    .map(m -> {
                        var unit = m.group("unit");
                        var value = parseInt(m.group("value"));
                        var validCM = "cm".equals(unit) && value >= 150 && value <= 193;
                        var validIN = "in".equals(unit) && value >= 59 && value <= 76;
                        return validCM || validIN;
                    }).orElse(false);
        }
    }

    static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
