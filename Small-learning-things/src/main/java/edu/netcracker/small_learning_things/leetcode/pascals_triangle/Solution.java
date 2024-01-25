package edu.netcracker.small_learning_things.leetcode.pascals_triangle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author svku0919
 * @version 24/01/2024-13:11
 */

public class Solution {
    public List<List<Integer>> generate(int numRows) {
        if (numRows == 1) {
            return List.of(List.of(1));
        }

        if (numRows == 2) {
            return List.of(List.of(1), List.of(1, 1));
        }

        if (numRows == 3) {
            return List.of(List.of(1), List.of(1, 1), List.of(1, 2, 1));
        }

        List<List<Integer>> result = new ArrayList<>(List.of(List.of(1), List.of(1, 1), List.of(1, 2, 1)));

        for (int i = 3; i < numRows; i++) {
            ArrayList<Integer> nextLine = new ArrayList<>(i + 1);
            result.add(nextLine);
            nextLine.add(1);
            List<Integer> previousLine = result.get(i - 1);
            for (int j = 1; j < i; j++) {
                nextLine.add(previousLine.get(j - 1) + previousLine.get(j));
            }
            nextLine.add(1);
        }

        return result;
    }
}
