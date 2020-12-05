package y2020;

import common.ReadInputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Allan Im
 */
public class Day5 {

    public static void main(String[] args) {
        String input = ReadInputData.read("./y2020/day5.txt");

        List<String> inputs = Arrays.stream(input.split("\n")).collect(Collectors.toList());

        List<Integer> seats = new ArrayList<>();
        for (String seat : inputs) {
            int rowS = 0;
            int rowE = 127;
            int rowCnt = 128;
            int colS = 0;
            int colE = 7;
            int colCnt = 8;
            for (char c : seat.toCharArray()) {
                if (c == 'F') {
                    rowCnt = rowCnt / 2;
                    rowE = rowE - rowCnt;
                } else if (c == 'B') {
                    rowCnt = rowCnt / 2;
                    rowS = rowS + rowCnt;
                } else if (c == 'L') {
                    colCnt = colCnt / 2;
                    colE = colE - colCnt;
                } else if (c == 'R') {
                    colCnt = colCnt / 2;
                    colS = colS + colCnt;
                }
            }
            int seatId = rowS * 8 + colS;
            seats.add(seatId);
            // System.out.println("row " + rowS + ", col " + colS + ", seat ID " + seatId);
        }

        System.out.println("Max Seat ID: " + seats.stream().max(Integer::compareTo).orElse(0));

        int check = 0;
        for (Integer id : seats.stream().sorted().collect(Collectors.toList())) {
            // System.out.println(id);
            if (check == 0 || check + 1 == id) {
                check = id;
            } else {
                System.out.println("My Seat ID: " + (check + 1));
                break;
            }
        }
    }
}
