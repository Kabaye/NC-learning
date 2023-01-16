package edu.netcracker.small_learning_things.leetcode.two_sum;

import java.util.HashMap;

/**
 * @author svku0919
 * @version 16.01.2023-14:45
 */

public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];

        HashMap<Integer, Integer> elemToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            Integer indexOfOtherElem = elemToIndex.get(target - nums[i]);
            if (indexOfOtherElem != null && indexOfOtherElem != i){
                result[0] = i;
                result[1] = indexOfOtherElem;
                return result;
            }
            elemToIndex.put(nums[i], i);
        }

        return result;
    }
}
