package y2020;

import common.ReadInputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Allan Im
 */
public class Day4 {

    public static void main(String[] args) {
        String input = ReadInputData.read("./y2020/day4.txt");

        List<String> inputs = Arrays.stream(input.split("\n\n")).collect(Collectors.toList());

        List<Passport> passportList = new ArrayList<>();
        for (String info : inputs) {
            Passport passport = new Passport();
            for (String row : info.split("\n")) {
                for (String item : row.split(" ")) {
                    String[] i = item.split(":");
                    if (!i[1].equals(""))
                        passport.put(i[0], i[1]);
                }
            }
            passportList.add(passport);
        }

        long count = passportList.stream().filter(Passport::valid).count();
        System.out.println("Part1 valid count: " + count);

        long count2 = passportList.stream().filter(Passport::valid2).count();
        System.out.println("Part2 valid count: " + count2);
    }


    private static class Passport extends HashMap<String, String> {
        private static Set<String> icolor = new HashSet<>();

        static {
            icolor.add("amb");
            icolor.add("blu");
            icolor.add("brn");
            icolor.add("gry");
            icolor.add("grn");
            icolor.add("hzl");
            icolor.add("oth");
        }

        public boolean valid() {
            return containsKey("byr")
                    && containsKey("iyr")
                    && containsKey("eyr")
                    && containsKey("hgt")
                    && containsKey("hcl")
                    && containsKey("ecl")
                    && containsKey("pid");
        }

        public boolean valid2() {
            return yearCheck("byr", 4, 1920, 2002)
                    && yearCheck("iyr", 4, 2010, 2020)
                    && yearCheck("eyr", 4, 2020, 2030)
                    && heightCheck("hgt")
                    && colorCheck("hcl")
                    && icolor("ecl")
                    && pid("pid");
        }

        private boolean yearCheck(String key, int digits, int min, int max) {
            if (containsKey(key)) {
                String value = this.get(key);
                int year = Integer.parseInt(value);
                return value.length() == digits && year >= min && year <= max;
            } else {
                return false;
            }
        }

        private boolean heightCheck(String key) {
            if (containsKey(key)) {
                String value = this.get(key);
                if (value.contains("cm")) {
                    int height = Integer.parseInt(value.replace("cm", ""));
                    return height >= 150 && height <= 193;
                } else if (value.contains("in")) {
                    int height = Integer.parseInt(value.replace("in", ""));
                    return height >= 59 && height <= 76;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        private boolean colorCheck(String key) {
            if (containsKey(key)) {
                String value = this.get(key);
                return value.length() == 7 && value.matches("^#[a-f0-9]{6}");
            } else {
                return false;
            }
        }

        private boolean icolor(String key) {
            if (containsKey(key)) {
                String value = this.get(key);
                return icolor.contains(value);
            } else {
                return false;
            }
        }

        private boolean pid(String key) {
            if (containsKey(key)) {
                String value = this.get(key);
                return value.length() == 9 && value.matches("^[0-9]{9}");
            } else {
                return false;
            }
        }
    }
}
