package edu.netcracker.small_learning_things.leetcode.remove_duplicates_from_sorted_array;

/**
 * @author svku0919
 * @version 19.06.2023-15:25
 */

class Solution {
    public static int removeDuplicates(int[] nums) {
        int currIndex = 0;
        int lastElem = -101;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]!= lastElem){
                nums[currIndex++] = nums[i];
                lastElem = nums[i];
            }
        }

        return currIndex;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 2};
        removeDuplicates(nums);
        System.out.println(nums);
    }
}
