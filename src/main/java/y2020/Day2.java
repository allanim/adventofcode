package y2020;

import common.ReadInputData;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * --- Day 2: Password Philosophy ---
 * Your flight departs in a few days from the coastal airport; the easiest way down to the coast from here is via
 * toboggan.
 *
 * The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. "Something's wrong with our computers; we
 * can't log in!" You ask if you can take a look.
 *
 * Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed by the
 * Official Toboggan Corporate Policy that was in effect when they were chosen.
 *
 * To try to debug the problem, they have created a list (your puzzle input) of passwords (according to the corrupted
 * database) and the corporate policy when that password was set.
 *
 * For example, suppose you have the following list:
 *
 * 1-3 a: abcde
 * 1-3 b: cdefg
 * 2-9 c: ccccccccc
 * Each line gives the password policy and then the password. The password policy indicates the lowest and highest
 * number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password
 * must contain a at least 1 time and at most 3 times.
 *
 * In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but
 * needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of
 * their respective policies.
 *
 * How many passwords are valid according to their policies?
 *
 * Your puzzle answer was 424.
 *
 * --- Part Two ---
 * While it appears you validated the passwords correctly, they don't seem to be what the Official Toboggan Corporate
 * Authentication System is expecting.
 *
 * The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job at
 * the sled rental place down the street! The Official Toboggan Corporate Policy actually works a little differently.
 *
 * Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second
 * character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!) Exactly one of these
 * positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy
 * enforcement.
 *
 * Given the same example list from above:
 *
 * 1-3 a: abcde is valid: position 1 contains a and position 3 does not.
 * 1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
 * 2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
 * How many passwords are valid according to the new interpretation of the policies?
 *
 * Your puzzle answer was 747.
 */
public class Day2 {

    public static void main(String[] args) {
        String input = ReadInputData.read("./y2020/day2.txt");

        List<InputObject> inputs = Arrays.stream(input.split("\n")).map(s -> {
            String[] s1 = s.split(":");
            String[] s2 = s1[0].split(" ");
            String[] s3 = s2[0].split("-");
            return new InputObject(Integer.parseInt(s3[0]), Integer.parseInt(s3[1]), s2[1].trim(), s1[1].trim());
        }).collect(Collectors.toList());

        long part1 = inputs.stream().filter(InputObject::part1).count();
        System.out.println("Part1: " + part1);

        long part2 = inputs.stream().filter(InputObject::part2).count();
        System.out.println("Part2: " + part2);
    }

    public static class InputObject {
        private final int min;
        private final int max;
        private final char contain;
        private final String target;

        public InputObject(int min, int max, String contain, String target) {
            this.min = min;
            this.max = max;
            this.contain = contain.charAt(0);
            this.target = target;
        }

        public boolean part1() {
            int cnt = 0;
            for (int i = 0; i < target.length(); i++) {
                if (target.charAt(i) == contain) {
                    cnt++;
                }
            }
            return min <= cnt && max >= cnt;
        }

        public boolean part2() {
            boolean first = target.charAt(min - 1) == contain;
            boolean second = target.charAt(max - 1) == contain;
            return (first && !second) || (!first && second);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Day2.class.getSimpleName() + "[", "]")
                    .add("min=" + min)
                    .add("max=" + max)
                    .add("contain='" + contain + "'")
                    .add("target='" + target + "'")
                    .toString();
        }
    }
}
