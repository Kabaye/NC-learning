package edu.netcracker.small_learning_things.leetcode.pascals_triangle_ii;

import java.util.ArrayList;
import java.util.List;

/**
 * @author svku0919
 * @version 24/01/2024-13:11
 */

public class Solution {
    public List<Integer> getRow(int rowIndex) {
        if (rowIndex == 0) {
            return List.of(1);
        }

        if (rowIndex == 1) {
            return List.of(1, 1);
        }

        if (rowIndex == 2) {
            return List.of(1, 2, 1);
        }

        List<Integer> result = new ArrayList<>(rowIndex + 1);
        result.add(1);
        result.add(2);
        result.add(1);

        for (int i = 3; i <= rowIndex; i++) {
            result.add(1);
        }

        int tempNum;
        int sum;

        for (int i = 3; i <= rowIndex; i++) {
            tempNum = result.get(0);
            for (int j = 1; j < i; j++) {
                sum = tempNum + result.get(j);
                tempNum = result.get(j);
                result.set(j, sum);
            }
        }

        return result;
    }
}
