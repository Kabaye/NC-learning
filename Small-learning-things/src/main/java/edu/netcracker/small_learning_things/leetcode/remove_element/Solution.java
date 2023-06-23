package edu.netcracker.small_learning_things.leetcode.remove_element;

/**
 * @author svku0919
 * @version 23.06.2023-15:51
 */

class Solution {
    public static int removeElement(int[] nums, int val) {
        int temp;
        int indexLast = nums.length - 1;
        for (int i = 0; i <= indexLast; i++) {
            if (nums[i] == val) {
                temp = nums[indexLast];
                nums[indexLast] = nums[i];
                nums[i] = temp;
                indexLast--;
            }
            if (nums[i] == val) {
                i--;
            }
        }
        return indexLast + 1;
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int i = removeElement(nums, 2);
        System.out.println(i);

    }
}
