package y2021;

import common.ReadInputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * --- Day 3: Binary Diagnostic ---
 * The submarine has been making some odd creaking noises, so you ask it to produce a diagnostic report just in case.
 * <p>
 * The diagnostic report (your puzzle input) consists of a list of binary numbers which, when decoded properly, can tell you many useful things about the conditions of the submarine. The first parameter to check is the power consumption.
 * <p>
 * You need to use the binary numbers in the diagnostic report to generate two new binary numbers (called the gamma rate and the epsilon rate). The power consumption can then be found by multiplying the gamma rate by the epsilon rate.
 * <p>
 * Each bit in the gamma rate can be determined by finding the most common bit in the corresponding position of all numbers in the diagnostic report. For example, given the following diagnostic report:
 * <p>
 * 00100
 * 11110
 * 10110
 * 10111
 * 10101
 * 01111
 * 00111
 * 11100
 * 10000
 * 11001
 * 00010
 * 01010
 * Considering only the first bit of each number, there are five 0 bits and seven 1 bits. Since the most common bit is 1, the first bit of the gamma rate is 1.
 * <p>
 * The most common second bit of the numbers in the diagnostic report is 0, so the second bit of the gamma rate is 0.
 * <p>
 * The most common value of the third, fourth, and fifth bits are 1, 1, and 0, respectively, and so the final three bits of the gamma rate are 110.
 * <p>
 * So, the gamma rate is the binary number 10110, or 22 in decimal.
 * <p>
 * The epsilon rate is calculated in a similar way; rather than use the most common bit, the least common bit from each position is used. So, the epsilon rate is 01001, or 9 in decimal. Multiplying the gamma rate (22) by the epsilon rate (9) produces the power consumption, 198.
 * <p>
 * Use the binary numbers in your diagnostic report to calculate the gamma rate and epsilon rate, then multiply them together. What is the power consumption of the submarine? (Be sure to represent your answer in decimal, not binary.)
 * <p>
 * Your puzzle answer was 2972336.
 * <p>
 * --- Part Two ---
 * Next, you should verify the life support rating, which can be determined by multiplying the oxygen generator rating by the CO2 scrubber rating.
 * <p>
 * Both the oxygen generator rating and the CO2 scrubber rating are values that can be found in your diagnostic report - finding them is the tricky part. Both values are located using a similar process that involves filtering out values until only one remains. Before searching for either rating value, start with the full list of binary numbers from your diagnostic report and consider just the first bit of those numbers. Then:
 * <p>
 * Keep only numbers selected by the bit criteria for the type of rating value for which you are searching. Discard numbers which do not match the bit criteria.
 * If you only have one number left, stop; this is the rating value for which you are searching.
 * Otherwise, repeat the process, considering the next bit to the right.
 * The bit criteria depends on which type of rating value you want to find:
 * <p>
 * To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position, and keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 1 in the position being considered.
 * To find CO2 scrubber rating, determine the least common value (0 or 1) in the current bit position, and keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 0 in the position being considered.
 * For example, to determine the oxygen generator rating value using the same example diagnostic report from above:
 * <p>
 * Start with all 12 numbers and consider only the first bit of each number. There are more 1 bits (7) than 0 bits (5), so keep only the 7 numbers with a 1 in the first position: 11110, 10110, 10111, 10101, 11100, 10000, and 11001.
 * Then, consider the second bit of the 7 remaining numbers: there are more 0 bits (4) than 1 bits (3), so keep only the 4 numbers with a 0 in the second position: 10110, 10111, 10101, and 10000.
 * In the third position, three of the four numbers have a 1, so keep those three: 10110, 10111, and 10101.
 * In the fourth position, two of the three numbers have a 1, so keep those two: 10110 and 10111.
 * In the fifth position, there are an equal number of 0 bits and 1 bits (one each). So, to find the oxygen generator rating, keep the number with a 1 in that position: 10111.
 * As there is only one number left, stop; the oxygen generator rating is 10111, or 23 in decimal.
 * Then, to determine the CO2 scrubber rating value from the same example above:
 * <p>
 * Start again with all 12 numbers and consider only the first bit of each number. There are fewer 0 bits (5) than 1 bits (7), so keep only the 5 numbers with a 0 in the first position: 00100, 01111, 00111, 00010, and 01010.
 * Then, consider the second bit of the 5 remaining numbers: there are fewer 1 bits (2) than 0 bits (3), so keep only the 2 numbers with a 1 in the second position: 01111 and 01010.
 * In the third position, there are an equal number of 0 bits and 1 bits (one each). So, to find the CO2 scrubber rating, keep the number with a 0 in that position: 01010.
 * As there is only one number left, stop; the CO2 scrubber rating is 01010, or 10 in decimal.
 * Finally, to find the life support rating, multiply the oxygen generator rating (23) by the CO2 scrubber rating (10) to get 230.
 * <p>
 * Use the binary numbers in your diagnostic report to calculate the oxygen generator rating and CO2 scrubber rating, then multiply them together. What is the life support rating of the submarine? (Be sure to represent your answer in decimal, not binary.)
 * <p>
 * Your puzzle answer was 3368358.
 */
public class Day4 {

    public static void main(String[] args) {
        String input = ReadInputData.read("./y2021/day4.txt");

        List<String> inputs = Arrays.stream(input.split("\n\n")).toList();

        int[] bingoNums = Arrays.stream(inputs.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        List<int[][]> targetNums = new ArrayList<>();
        for (int i = 1; i < inputs.size(); i++) {
            int[][] target = new int[5][5];
            String[] rows = inputs.get(i).split("\n");
            for (int j = 0; j < rows.length; j++) {
                int intLength = 2;
                for (int k = 0; k < 5; k++) {
                    target[j][k] = Integer.parseInt(rows[j].substring((k * 3), (k * 3) + intLength).trim());
                }
            }
            targetNums.add(target);
        }

        // -- part 1

        // copy
        List<int[][]> haveNums = new ArrayList<>();
        for (int[][] targetNum : targetNums) {
            int[][] haveNum = new int[5][5];
            for (int i = 0; i < targetNum.length; i++) {
                for (int j = 0; j < targetNum[i].length; j++) {
                    haveNum[i][j] = targetNum[i][j];
                }
            }
            haveNums.add(haveNum);
        }

        // check
        int firstBingoNum = -1;
        int firstBingoSum = 0;
        int lastBingoNum = -1;
        int lastBingoSum = 0;
        List<Integer> ignoreIndex = new ArrayList<>();
        for (int bingoNum : bingoNums) {
            System.out.println("bingoNum: " + bingoNum);
            for (int i = 0; i < haveNums.size(); i++) {
                if (ignoreIndex.contains(i)) {
                    continue;
                }
                if (bingoCnt(mark(haveNums.get(i), bingoNum)) > 0) {
                    if (firstBingoNum < 0) {
                        for (int[] ints : haveNums.get(i)) {
                            firstBingoSum += Arrays.stream(ints).filter(value -> value > -1).sum();
                        }
                        firstBingoNum = bingoNum;
                    }
                    if (ignoreIndex.size() + 1 == haveNums.size()) {
                        for (int[] ints : haveNums.get(i)) {
                            lastBingoSum += Arrays.stream(ints).filter(value -> value > -1).sum();
                        }
                        lastBingoNum = bingoNum;
                    }
                    ignoreIndex.add(i);
                    System.out.println(bingoNum + "-" + i);
                }
            }
        }


        System.out.printf("first Bingo Num: %d", firstBingoNum);
        System.out.println();
        System.out.printf("first Bingo Sum: %d", firstBingoSum);
        System.out.println();
        System.out.printf("first Result %d * %d + %d", firstBingoNum, firstBingoSum, firstBingoNum * firstBingoSum);
        System.out.println();
        System.out.printf("last Bingo Num: %d", lastBingoNum);
        System.out.println();
        System.out.printf("last Bingo Sum: %d", lastBingoSum);
        System.out.println();
        System.out.printf("last Result %d * %d + %d", lastBingoNum, lastBingoSum, lastBingoNum * lastBingoSum);


    }

    private static int[][] mark(int[][] haveNums, int checkNum) {
        for (int i = 0; i < haveNums.length; i++) {
            for (int j = 0; j < haveNums[i].length; j++) {
                if (checkNum == haveNums[i][j]) {
                    haveNums[i][j] = -1;

                    for (int[] haveNum : haveNums) {
                        for (int i1 : haveNum) {
                            System.out.printf(i1 + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }
        }
        return haveNums;
    }

    private static int bingoCnt(final int[][] haveNums) {
        List<int[]> checkNums = bingoCheckData(haveNums);

        int count = 0;
        for (int[] checkNum : checkNums) {
            if (Arrays.stream(checkNum).sum() == -5) {
                count++;
            }
        }
        return count;
    }

    private static List<int[]> bingoCheckData(final int[][] haveNums) {
        List<int[]> checkNums = new ArrayList<>();

        // Horizontal
        Collections.addAll(checkNums, haveNums);

        // Vertical
        int[][] reverseNums = new int[5][5];
        for (int i = 0; i < haveNums.length; i++) {
            for (int j = 0; j < haveNums[i].length; j++) {
                reverseNums[j][i] = haveNums[i][j];
            }
        }
        Collections.addAll(checkNums, reverseNums);

        // Diagonal
        int[] x1 = new int[5];
        int[] x2 = new int[5];
        for (int i = 0; i < haveNums.length; i++) {
            for (int j = 0; j < haveNums[i].length; j++) {
                if (i == j) {
                    x1[i] = haveNums[i][j];
                }
                if (i + j == 4) {
                    x2[i] = haveNums[i][j];
                }
            }
        }
//        Collections.addAll(checkNums, x1, x2);

        return checkNums;
    }
}
